package dev.beenary.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;

public class DataGenerator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(final Class<T> clazz, final String fileName) throws IOException {
        try (InputStream inputStream = DataGenerator.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException(String.format("File not found: %s", fileName));
            }

            return objectMapper.readValue(inputStream, clazz);
        }
    }

    /**
     * Pretvara objekt u JSON reprezentaciju u obliku String tipa.
     *
     * @param request :: objekt kojeg je potrebno pretvoriti.
     * @param <T>     :: tip objekta kojeg je potrebno pretvoriti.
     * @return rezultat :: JSON reprezentacija objekta.
     * @throws JsonProcessingException :: baca iznimku ako nešto pođe po krivu.
     */
    public static <T> String toJson(final T request) throws JsonProcessingException {
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectMapper.registerModule(new JavaTimeModule());
        final ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(request);
    }
}
