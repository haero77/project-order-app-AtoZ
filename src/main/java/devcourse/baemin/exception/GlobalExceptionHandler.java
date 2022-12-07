package devcourse.baemin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("IllegalArgumentException Error!!! exceptionMessage: '{}'", exception.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResult(exception.getMessage()));
    }
}
