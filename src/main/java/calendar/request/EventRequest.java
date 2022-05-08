package calendar.request;

import java.sql.Timestamp;
import java.util.List;

public class EventRequest {

    private String title;

    private String description;

    private Timestamp startTime;

    private Timestamp endTime;

    private List<Long> attendeesIds;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public List<Long> getAttendeesIds() {
        return attendeesIds;
    }
}
