package dev.beenary;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static <T> T toJson(final Class<T> clazz, final String fileName) throws IOException {
        try (InputStream inputStream = DataGenerator.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException(String.format("File not found: %s", fileName));
            }

            return objectMapper.readValue(inputStream, clazz);
        }
    }
}
