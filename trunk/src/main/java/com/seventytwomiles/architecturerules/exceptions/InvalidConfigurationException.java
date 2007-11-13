package com.seventytwomiles.architecturerules.exceptions;


/**
 * <p>todo: javadocs</p>
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see Exception
 */
public class InvalidConfigurationException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public InvalidConfigurationException() {
        super();
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public InvalidConfigurationException(String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public InvalidConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public InvalidConfigurationException(Throwable cause) {
        super(cause);
    }
}
