package calendar.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

    private static final long serialVersionUID = -4027826489681581897L;

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
