package dev.beenary;

import dev.beenary.common.exception.BusinessException;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that represents global exception handler.
 *
 * @author anapeterlic
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_ERROR_MESSAGE = "There was an error processing the " +
            "request. Please try again later.";

    /**
     * Handles any {@link RuntimeException} that can occur during application execution.
     *
     * @param exception [{@link RuntimeException}] :: a runtime exception.
     * @return response [{@link ApiErrorResponse}] :: response with error message.
     */
    public ResponseEntity<Object> handleRuntimeException(@NonNull final RuntimeException exception) {
        return new ResponseEntity<>(ApiErrorResponse.of(Collections.singletonList(new ErrorMessage("", DEFAULT_ERROR_MESSAGE))),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles any {@link BusinessException} that can occur during application execution.
     *
     * @param exception [{@link BusinessException}] :: a business exception.
     * @return response [{@link ApiErrorResponse}] :: response with error message.
     */
    public ResponseEntity<Object> handleBusinessException(@NonNull final BusinessException exception) {
        return new ResponseEntity<>(ApiErrorResponse.of(Collections.singletonList(new ErrorMessage("", exception.getMessage()))),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Custom implementation for handling any {@link MethodArgumentNotValidException} that can
     * occur during data validation.
     *
     * @param exception [{@link MethodArgumentNotValidException}] :: a validation exception.
     * @param headers   [{@link HttpHeaders}] :: list of HTTP headers
     * @param status    [{@link HttpStatusCode}] :: HTTP status code
     * @param request   [{@link WebRequest}] :: HTTP request
     * @return response [{@link ApiErrorResponse}] :: response with list of error messages.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull final MethodArgumentNotValidException exception, @NonNull final HttpHeaders headers,
                                                                  @NonNull final HttpStatusCode status, @NonNull final WebRequest request) {
        final List<ErrorMessage> errors = new ArrayList<>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    final FieldError fieldError = (FieldError) error;
                    errors.add(new ErrorMessage(fieldError.getField(), error.getDefaultMessage()));
                });

        return new ResponseEntity<>(ApiErrorResponse.of(errors), HttpStatus.BAD_REQUEST);
    }

}
