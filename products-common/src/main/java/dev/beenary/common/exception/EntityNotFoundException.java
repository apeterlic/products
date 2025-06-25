package dev.beenary.common.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final String reference;

    public EntityNotFoundException(final String message, final String reference) {
        super(message);
        this.reference = reference;
    }
}
