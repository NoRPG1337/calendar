package calendar.controller;

import calendar.entity.Event;
import calendar.exception.BadRequestException;
import calendar.exception.NotFoundException;
import calendar.helper.Message;
import calendar.request.EventRequest;
import calendar.response.BaseResponse;
import calendar.response.DataResponse;
import calendar.response.projection.EventProjection;
import calendar.response.projection.UserOptionProjection;
import calendar.service.EventService;
import calendar.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;
    private UserService userService;

    @Autowired
    public void autowire(
            EventService eventService,
            UserService userService
    ) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/create-options")
    @ApiOperation(value = "Event creating options for users")
    public ResponseEntity<BaseResponse> options() {
        List<UserOptionProjection> users = this.userService.findAllExceptCurrent().stream().map(UserOptionProjection::new).collect(Collectors.toList());
        return new ResponseEntity<>(new DataResponse<>(users, Message.SUCCESS), HttpStatus.OK);
     }

    @PostMapping("")
    @ApiOperation(value = "Event creation")
    public ResponseEntity<BaseResponse> create(
            @ApiParam(value = "Event request") @Validated @RequestBody EventRequest request
    ) throws BadRequestException {
        this.eventService.validateRequest(request);
        this.eventService.create(request);
        return new ResponseEntity<>(new BaseResponse(Message.EVENT_CREATED), HttpStatus.CREATED);
    }

    @DeleteMapping("")
    @ApiOperation(value = "Event deletion")
    public ResponseEntity<BaseResponse> delete(
            @ApiParam(value = "Event ID") @RequestParam Long id
    ) throws NotFoundException {
        Event event = this.eventService.findById(id);
        if (event == null) {
            throw new NotFoundException(Message.eventNotFound(id));
        }
        this.eventService.delete(event);
        return new ResponseEntity<>(new BaseResponse(Message.eventDeleted(id)), HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation(value = "Events' matrix creation")
    public ResponseEntity<BaseResponse> getEventMatrix(
            @ApiParam(value = "Week's start time", example = "2022-05-02 00:00:01") @RequestParam Timestamp startTime
    ) throws BadRequestException {
        Map<String, List<EventProjection>> map = this.eventService.findAllForWeek(startTime);
        if (!this.eventService.isThisDayOfWeek(startTime, Calendar.MONDAY)) {
            throw new BadRequestException(Message.EVENT_MATRIX_INVALID_TIME);
        }
        return new ResponseEntity<>(new DataResponse<>(map, Message.SUCCESS), HttpStatus.OK);
    }
}
