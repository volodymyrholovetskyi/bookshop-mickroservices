package ua.vholovetskyi.bookshop.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-20
 */
@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgument(MethodArgumentNotValidException e) {
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(HttpStatus.BAD_REQUEST, getAllErrors(e)));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest().body(buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFound e) {
        return new ResponseEntity<>(buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExists(ResourceAlreadyExists e) {
        return new ResponseEntity<>(buildErrorResponse(HttpStatus.CONFLICT, e.getMessage()), HttpStatus.CONFLICT);
    }

    private ErrorResponse buildErrorResponse(HttpStatus status, String message) {
        return new ErrorResponse(
                status.name(),
                status.value(),
                message,
                Instant.now());
    }

    private String getAllErrors(MethodArgumentNotValidException e) {
        List<String> messages = new ArrayList<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            messages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        return String.join(", ", messages);
    }
}
