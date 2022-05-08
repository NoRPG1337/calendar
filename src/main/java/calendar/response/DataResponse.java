package calendar.response;

public class DataResponse<T> extends BaseResponse{

    private T data;

    public DataResponse(T data, String message) {
        super(message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
