package dev.beenary.common.utility;

/**
 * Common class that contains constants used through application.
 */
public final class Constant {

    public static final String ERROR_CLASS_INSTANTIATION = "Utility class should not be " +
            "instantiated.";

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private Constant() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }
}
