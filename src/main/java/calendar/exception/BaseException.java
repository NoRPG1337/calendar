package calendar.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception {

    private static final long serialVersionUID = 7813918823132054286L;

    protected final HttpStatus status;

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
