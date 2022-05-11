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
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    private static final SimpleDateFormat date = new SimpleDateFormat("EEE, dd.MM.yyyy");
    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

    private EventRepository eventRepository;
    private UserService userService;

    @Value("${day.start}")
    private String start;

    @Value("${day.end}")
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
        Timestamp startTime = this.setAsLocal(request.getStartTime());
        Timestamp endTime = this.setAsLocal(request.getEndTime());
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (endTime.compareTo(startTime) <= 0 || currentTime.compareTo(startTime) > 0) {
            throw new BadRequestException(Message.EVENT_INVALID_TIME);
        }

        if (!this.isMoreThan(startTime, start) || this.isMoreThan(startTime, end) ||
                this.isMoreThan(endTime, end) || !this.isMoreThan(endTime, start)) {
            throw new BadRequestException(Message.EVENT_INVALID_TIME);
        }

        if (this.isThisDayOfWeek(startTime, Calendar.SUNDAY)) {
            throw new BadRequestException(Message.EVENT_INVALID_TIME);
        }

        if (this.eventRepository.findAnyBetween(startTime, endTime)) {
            throw new BadRequestException(Message.EVENT_OVERLAP);
        }
    }

    public void create(EventRequest request) throws BadRequestException {
        List<User> attendees = new ArrayList<>();
        for (Long id : request.getAttendeesIds()) {
            User user = this.userService.findById(id);
            if (user == null) {
                throw new BadRequestException(Message.userNotFound(id));
            } else {
                attendees.add(user);
            }
        }
        attendees.add(this.userService.getCurrentAuthUser());

        Event event = new Event(
                request.getTitle(),
                request.getDescription(),
                attendees,
                this.setAsLocal(request.getStartTime()),
                this.setAsLocal(request.getEndTime())
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

    public boolean isThisDayOfWeek(Timestamp timestamp, int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        return calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
    }

    public boolean isMoreThan(Timestamp timestamp, String actual) {
        String toCompare = time.format(timestamp);

        String[] units = actual.split(":");
        this.trimStringForTimeUnit(units);
        Calendar toCompareCalendar = Calendar.getInstance();
        toCompareCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(units[0]));
        toCompareCalendar.set(Calendar.MINUTE, Integer.parseInt(units[1]));
        toCompareCalendar.set(Calendar.SECOND, Integer.parseInt(units[2]));

        units = toCompare.split(":");
        this.trimStringForTimeUnit(units);
        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(units[0]));
        actualCalendar.set(Calendar.MINUTE, Integer.parseInt(units[1]));
        actualCalendar.set(Calendar.SECOND, Integer.parseInt(units[2]));

        return toCompareCalendar.before(actualCalendar);
    }

    public void trimStringForTimeUnit(String[] units) {
        for (int i = 0; i < units.length; i++) {
            if (units[i].charAt(0) == '0') {
                units[i] = units[i].substring(1);
            }
        }
    }

    public Timestamp setAsLocal(Timestamp timestamp) {
        Instant instant = timestamp.toInstant();
        Duration d = Duration.ofHours(4);
        Instant hourPrior = instant.minus( d );
        return Timestamp.from(hourPrior);
    }
}
