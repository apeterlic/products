package dev.beenary;

import lombok.Data;

import java.util.UUID;

@Data
public class IdResponse {

    protected UUID id;

    public IdResponse(final UUID id) {
        this.id = id;
    }
}
