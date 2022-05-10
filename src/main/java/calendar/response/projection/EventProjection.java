package calendar.response.projection;

import calendar.entity.Event;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(description = "Event projection")
public class EventProjection {

    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm");

    @ApiModelProperty(value = "Event ID")
    private Long id;

    @ApiModelProperty(value = "Event title")
    private String title;

    @ApiModelProperty(value = "Event description")
    private String description;

    @ApiModelProperty(value = "Event start time", example = "10:00")
    private String startTime;

    @ApiModelProperty(value = "Event end time", example = "12:00")
    private String endTime;

    @ApiModelProperty(value = "Event attendees")
    private List<UserProjection> attendees;

    public EventProjection(Event event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.startTime = time.format(event.getStartTime());
        this.endTime = time.format(event.getEndTime());
        this.attendees = event.getAttendees().stream().map(UserProjection::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<UserProjection> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<UserProjection> attendees) {
        this.attendees = attendees;
    }
}
