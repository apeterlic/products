package dev.beenary.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;

public class TestDataHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Reads JSON file and converts it to requested object.
     *
     * @param clazz    [{@link Class}] :: object class.
     * @param fileName [{@link String}] :: name of the file from the '/resources' directory.
     * @param <T>      :: type of the object.
     * @return T [{@link T}] :: new instance of the created object.
     * @throws IOException :: if reading from a file is not possible.
     */
    public static <T> T fromJsonFile(final Class<T> clazz, final String fileName) throws IOException {
        try (InputStream inputStream = TestDataHelper.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException(String.format("File not found: %s", fileName));
            }

            return objectMapper.readValue(inputStream, clazz);
        }
    }

    /**
     * Converts given object to the JSON String representation.
     *
     * @param request :: request to be converted.
     * @param <T>     :: type of the request.
     * @return result [{@link String}] :: String representation of an object.
     * @throws JsonProcessingException :: if conversion is not possible.
     */
    public static <T> String toJson(final T request) throws JsonProcessingException {
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectMapper.registerModule(new JavaTimeModule());
        final ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(request);
    }
}
