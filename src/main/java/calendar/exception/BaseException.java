package calendar.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception {

    protected final HttpStatus status;

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
