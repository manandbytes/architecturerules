package com.seventytwomiles.architecturerules;


/**
 * <p>Exception to be thrown when a configured source is not found and
 * <samp>&lt;source not-found="exception"></samp></p>
 *
 * @author mnereson
 * @noinspection JavaDoc
 * @see Exception
 */
public class SourceNotFoundException extends Exception {


    /**
     * @see Exception#Exception()
     */
    public SourceNotFoundException() {
        super();
    }


    /**
     * @see Exception#Exception(String)
     */
    public SourceNotFoundException(final String message) {
        super(message);
    }


    /**
     * @see Exception#Exception(Throwable)
     */
    public SourceNotFoundException(final Throwable cause) {
        super(cause);
    }


    /**
     * @see Exception#Exception(String,Throwable)
     */
    public SourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
