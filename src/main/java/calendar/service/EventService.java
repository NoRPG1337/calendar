package calendar.service;

import calendar.entity.Event;
import calendar.exception.BadRequestException;
import calendar.repository.EventRepository;
import calendar.request.EventRequest;
import calendar.response.projection.EventProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventService {

    private static final SimpleDateFormat date = new SimpleDateFormat("EEE, dd.MM.yyyy");

    private EventRepository eventRepository;
    private UserService userService;

    @Autowired
    public void autowire(
            EventRepository eventRepository,
            UserService userService
    ) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public void save(Event event) {
        this.eventRepository.save(event);
    }

    public void delete(Event event) {
        this.eventRepository.delete(event);
    }

    public Event findById(Long id) {
        return this.eventRepository.findById(id).orElse(null);
    }

    public void validateRequest(EventRequest eventRequest) throws BadRequestException {
        if (!date.format(eventRequest.getStartTime()).equals(date.format(eventRequest.getEndTime()))) {
            throw new BadRequestException("");
        }
        if (this.eventRepository.findAnyBetween(eventRequest.getStartTime(), eventRequest.getEndTime())) {
            throw new BadRequestException("");
        }
    }

    public void create(EventRequest eventRequest) {
        Event event = new Event(
                eventRequest.getTitle(),
                eventRequest.getDescription(),
                this.userService.findAllByIdIn(eventRequest.getAttendeesIds()),
                eventRequest.getStartTime(),
                eventRequest.getEndTime()
        );
        this.save(event);
    }

    public List<Event> findAllBetween(Timestamp startTime, Timestamp endTime) {
        return this.eventRepository.findAllBetween(startTime, endTime);
    }

    public Map<String, List<EventProjection>> findAllForWeek(Timestamp startTime) {
        Map<String, List<EventProjection>> eventMap = new LinkedHashMap<>();
        while (eventMap.size() != 6) {
            Timestamp endTime = this.incrementTimestamp(startTime);
            List<Event> currentEvents = this.findAllBetween(startTime, endTime);
            eventMap.put(date.format(startTime), currentEvents.stream().map(EventProjection::new).collect(Collectors.toList()));
            startTime = endTime;
        }
        return eventMap;
    }

    public Timestamp incrementTimestamp(Timestamp timestamp) {
        return Timestamp.valueOf(timestamp.toLocalDateTime().plusDays(1));
    }
}
