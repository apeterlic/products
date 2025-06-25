package dev.beenary.api;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
public class ApiIdEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
}
