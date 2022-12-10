package devcourse.baemin.global.exception;

public class ErrorResult {

    private final String errorMessage;

    public ErrorResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
