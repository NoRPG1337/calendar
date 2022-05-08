package calendar.controller;

import calendar.request.EventRequest;
import calendar.response.BaseResponse;
import calendar.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EvenController {

    private EventService eventService;

    @Autowired
    public void autowire(EventService eventService) {
        this.eventService = eventService;
    }

//    @PostMapping("")
//    public ResponseEntity<BaseResponse> create(
//            @RequestBody EventRequest request
//    ) {
//        this.eventService.validateRequest(request);
//        this.eventService.createEvent(request);
//        return new ResponseEntity<>(new BaseResponse(""), HttpStatus.CREATED);
//    }
}
