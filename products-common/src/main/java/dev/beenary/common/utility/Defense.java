package dev.beenary.common.utility;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

/**
 * Utility class providing common defense operations.
 * Each of the provided operations throws a {@link IllegalArgumentException} in terms of defense.
 */
public final class Defense {

    public static final String PARAMETER_NULL = "Parameter '%s' cannot be null.";

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private Defense() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }

    public static <T> T notNull(final T parameter, final String fieldName) {
        if (parameter == null) {
            throwException(String.format(PARAMETER_NULL, fieldName));
        }
        return parameter;
    }

    /**
     * Throws a validation exception by the given message.<br>
     *
     * @param message [{@link String}] :: the error message
     * @throws IllegalArgumentException in case a defense criteria was not met
     */
    private static void throwException(final String message) {
        throw new IllegalArgumentException(message);
    }

}