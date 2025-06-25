package dev.beenary.api;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
public class IdResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected UUID id;

    public IdResponse(final UUID id) {
        this.id = id;
    }
}
