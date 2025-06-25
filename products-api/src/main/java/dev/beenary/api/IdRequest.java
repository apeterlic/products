package dev.beenary.api;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
public class IdRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected UUID id;

    /**
     * Creates a new {@code IdRequest} instance with the given ID.
     *
     * @param id [{@link UUID}] :: the entity ID
     * @return request [{@link IdRequest}] :: the newly created ID-based request
     */
    public static IdRequest from(final UUID id) {
        final IdRequest idRequest = new IdRequest();
        idRequest.setId(id);
        return idRequest;
    }

}
