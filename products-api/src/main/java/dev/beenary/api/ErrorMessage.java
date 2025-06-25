package dev.beenary.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String reference;

    private String message;

}
