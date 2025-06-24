package dev.beenary;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public final class ApiErrorResponse {

    private boolean success;

    private Instant timestamp;

    private List<ErrorMessage> errors;

    private ApiErrorResponse(final Instant timestamp, final List<ErrorMessage> errors) {
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public static ApiErrorResponse of(final List<ErrorMessage> errors) {
        return new ApiErrorResponse(Instant.now(), errors);
    }
}
