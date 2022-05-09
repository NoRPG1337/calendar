package calendar.config;

import calendar.exception.BaseException;
import calendar.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Custom exception handler
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Custom exception handler
     * @param exception Custom exception
     * @return ResponseEntity with exception's message and status
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse> baseException(BaseException exception) {
        return new ResponseEntity<>(new BaseResponse(exception.getMessage()), exception.getStatus());
    }

    /**
     * Other exception handler
     * @param exception
     * @return ResponseEntity with exception's stack trace and Internal Server Error status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> serverErrorException(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));

        return new ResponseEntity<>(new BaseResponse(stringWriter.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
