package dev.beenary.common.exception;


public class EntityNotFoundException extends RuntimeException {

    private final String reference;

    public EntityNotFoundException(final String message, final String reference) {
        super(message);
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }
}
