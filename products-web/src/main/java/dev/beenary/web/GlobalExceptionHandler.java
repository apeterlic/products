package dev.beenary.web;

import dev.beenary.api.ApiErrorResponse;
import dev.beenary.api.ErrorMessage;
import dev.beenary.common.exception.BusinessException;
import dev.beenary.common.exception.EntityNotFoundException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_ERROR_MESSAGE = "There was an error processing the " +
            "request. Please try again later.";

    /**
     * Handles any uncaught exception that can occur during application execution.
     *
     * @param exception [{@link Exception}] :: a runtime exception.
     * @return response [{@link ApiErrorResponse}] :: response with error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleRuntimeException(@NonNull final Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ApiErrorResponse.of(Collections.singletonList(new ErrorMessage("", DEFAULT_ERROR_MESSAGE))),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles any {@link BusinessException} that can occur during application execution.
     *
     * @param exception [{@link BusinessException}] :: a business exception.
     * @return response [{@link ApiErrorResponse}] :: response with error message.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(@NonNull final BusinessException exception) {
        return new ResponseEntity<>(ApiErrorResponse.of(Collections.singletonList(new ErrorMessage("", exception.getMessage()))),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any {@link EntityNotFoundException} that can occur during application execution.
     *
     * @param exception [{@link EntityNotFoundException}] :: indicates that requested entity
     *                  doesn't exist in database.
     * @return response [{@link ApiErrorResponse}] :: response with error message.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(@NonNull final EntityNotFoundException exception) {
        return new ResponseEntity<>(ApiErrorResponse.of(Collections.singletonList(new ErrorMessage(exception.getReference(), exception.getMessage()))),
                HttpStatus.NOT_FOUND);
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
