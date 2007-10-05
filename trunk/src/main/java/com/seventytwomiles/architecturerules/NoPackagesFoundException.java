package com.seventytwomiles.architecturerules;


/**
 * <p>Exception to be thrown when no packages are found in the given source path
 * if <samp>&lt;sources no-packages="exception"> </samp></p>
 *
 * @author mnereson
 * @noinspection JavaDoc
 * @see Exception
 */
public class NoPackagesFoundException extends Exception {


    /**
     * @see Exception#Exception()
     */
    public NoPackagesFoundException() {
        super();
    }


    /**
     * @see Exception#Exception(String)
     */
    public NoPackagesFoundException(final String message) {
        super(message);
    }


    /**
     * @see Exception#Exception(String,Throwable)
     */
    public NoPackagesFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }


    /**
     * @see Exception#Exception(Throwable)
     */
    public NoPackagesFoundException(final Throwable cause) {
        super(cause);
    }
}
