package dev.beenary.api;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PayloadResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected T payload;

    public PayloadResponse(final T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(final T payload) {
        this.payload = payload;
    }
}
