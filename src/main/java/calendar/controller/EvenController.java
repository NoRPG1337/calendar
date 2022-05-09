package calendar.controller;

import calendar.entity.Event;
import calendar.exception.BadRequestException;
import calendar.exception.NotFoundException;
import calendar.request.EventRequest;
import calendar.response.BaseResponse;
import calendar.response.DataResponse;
import calendar.response.Response;
import calendar.response.projection.EventProjection;
import calendar.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/event")
public class EvenController {

    private EventService eventService;

    @Autowired
    public void autowire(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> create(
            @RequestBody EventRequest request
    ) throws BadRequestException {
        this.eventService.validateRequest(request);
        this.eventService.create(request);
        return new ResponseEntity<>(new BaseResponse(""), HttpStatus.CREATED);
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResponse> delete(
            @RequestParam Long id
    ) throws NotFoundException {
        Event event = this.eventService.findById(id);
        if (event == null) {
            throw new NotFoundException("");
        }
        this.eventService.delete(event);
        return new ResponseEntity<>(new BaseResponse(""), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse> get(
            @RequestParam Timestamp startTime
    ) {
        Map<String, List<EventProjection>> map = this.eventService.findAllForWeek(startTime);
        return new ResponseEntity<>(new DataResponse<>(map, ""), HttpStatus.OK);
    }
}
