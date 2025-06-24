package dev.beenary;

import lombok.Data;

@Data
public class PayloadResponse<T> {

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
