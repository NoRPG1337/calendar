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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public List<Long> getAttendeesIds() {
        return attendeesIds;
    }

    public void setAttendeesIds(List<Long> attendeesIds) {
        this.attendeesIds = attendeesIds;
    }
}
