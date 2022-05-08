package calendar.response;

public class BaseResponse implements Response {

    protected String message;

    public BaseResponse(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
