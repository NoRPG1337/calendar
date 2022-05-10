package calendar.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

@ApiModel(description = "Event creation request")
public class EventRequest {

    @ApiModelProperty(value = "Event title")
    private String title;

    @ApiModelProperty(value = "Event description")
    private String description;

    @ApiModelProperty(value = "Event start time", example = "2022-05-05T10:00:00.000Z")
    private Timestamp startTime;

    @ApiModelProperty(value = "Event end time", example = "2022-05-05T12:00:00.000Z")
    private Timestamp endTime;

    @ApiModelProperty(value = "Event attendees' IDs")
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
