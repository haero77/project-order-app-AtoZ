package devcourse.baemin.api.response;

public class CommonResponse<T> {

    private final String responseMessage;
    private final T data;

    public CommonResponse(String responseMessage, T data) {
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public T getData() {
        return this.data;
    }
}
