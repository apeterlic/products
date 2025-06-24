package dev.beenary.common.utility;

/**
 * Utility class providing common defense operations.<br> Each of the provided operations throws a
 * {@link IllegalArgumentException} in terms of defense.<br>
 */
public final class Defense {

    public static final String PARAMETER_NULL = "Parameter '%s' cannot be null.";

    /**
     * Class contains only static members thus cannot be instantiated.
     */
    private Defense() {
        throw new UnsupportedOperationException();
    }

    public static <T> T notNull(final T parametar, final String naziv) {
        if (parametar == null) {
            throwException(String.format(PARAMETER_NULL, naziv));
        }
        return parametar;
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