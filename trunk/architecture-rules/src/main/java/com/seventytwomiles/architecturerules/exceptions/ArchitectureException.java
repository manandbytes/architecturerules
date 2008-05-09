package com.seventytwomiles.architecturerules.exceptions;

/**
 * <p>Top-level Architecture Rules Exception. All domain Exceptions should
 * extend this class.</p>
 *
 * @author mikenereson
 * @see RuntimeException
 */
@SuppressWarnings({"JavaDoc"})
public class ArchitectureException extends RuntimeException {


    /**
     * @see RuntimeException#Exception()
     */
    public ArchitectureException() {
        super();
    }

    /**
     * @see RuntimeException#Exception(String)
     */
    public ArchitectureException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException#Exception(String, Throwable)
     */
    public ArchitectureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see RuntimeException#Exception(Throwable)
     */
    public ArchitectureException(Throwable cause) {
        super(cause);
    }
}
