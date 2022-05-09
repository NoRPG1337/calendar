package calendar.service;

import calendar.entity.Event;
import calendar.entity.User;
import calendar.exception.BadRequestException;
import calendar.helper.Message;
import calendar.repository.EventRepository;
import calendar.request.EventRequest;
import calendar.response.projection.EventProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    private static final SimpleDateFormat date = new SimpleDateFormat("EEE, dd.MM.yyyy");
    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

    private EventRepository eventRepository;
    private UserService userService;

    @Value("day.start")
    private String start;

    @Value("day.end")
    private String end;

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

    public void validateRequest(EventRequest request) throws BadRequestException {
        Timestamp startTime = request.getStartTime();
        Timestamp endTime = request.getEndTime();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (endTime.compareTo(startTime) <= 0 || currentTime.compareTo(startTime) > 0) {
            throw new BadRequestException(Message.EVENT_INVALID_TIME);
        }

        if (this.isLessThan(startTime, start) || !this.isLessThan(endTime, end)) {
            throw new BadRequestException(Message.EVENT_INVALID_TIME);
        }

        if (this.isSunday(startTime)) {
            throw new BadRequestException(Message.EVENT_INVALID_TIME);
        }

        if (this.eventRepository.findAnyBetween(startTime, endTime)) {
            throw new BadRequestException(Message.EVENT_OVERLAP);
        }
    }

    public void create(EventRequest eventRequest) throws BadRequestException {
        List<User> attendees = new ArrayList<>();
        for (Long id : eventRequest.getAttendeesIds()) {
            User user = this.userService.findById(id);
            if (user == null) {
                throw new BadRequestException(Message.userNotFound(id));
            } else {
                attendees.add(user);
            }
        }

        Event event = new Event(
                eventRequest.getTitle(),
                eventRequest.getDescription(),
                attendees,
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

    public boolean isSunday(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public boolean isLessThan(Timestamp timestamp, String actual) {
        String toCompare = time.format(timestamp);

        String[] units = actual.split(":");
        Calendar toCompareCalendar = Calendar.getInstance();
        toCompareCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(units[0]));
        toCompareCalendar.set(Calendar.MINUTE, Integer.parseInt(units[1]));
        toCompareCalendar.set(Calendar.SECOND, Integer.parseInt(units[2]));

        units = toCompare.split(":");
        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(units[0]));
        actualCalendar.set(Calendar.MINUTE, Integer.parseInt(units[1]));
        actualCalendar.set(Calendar.SECOND, Integer.parseInt(units[2]));

        return toCompareCalendar.before(actualCalendar);
    }
}
