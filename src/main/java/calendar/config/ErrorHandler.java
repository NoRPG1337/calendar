package calendar.config;

import calendar.exception.BaseException;
import calendar.response.BaseResponse;
import calendar.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerErrorException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse> baseException(BaseException e) {
        return new ResponseEntity<>(new BaseResponse(""), e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> serverErrorException(ServerErrorException e) {
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
