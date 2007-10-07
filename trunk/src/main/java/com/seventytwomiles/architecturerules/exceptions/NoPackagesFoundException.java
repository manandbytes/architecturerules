package com.seventytwomiles.architecturerules.exceptions;


import com.seventytwomiles.architecturerules.domain.SourceDirectory;


/**
 * <p>Exception to be thrown when no packages are found in the given source path
 * if <samp>&lt;sources no-packages="exception"> </samp></p>
 *
 * @author mnereson
 * @noinspection JavaDoc
 * @see Exception
 */
public class NoPackagesFoundException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public NoPackagesFoundException() {
        super("no packages found");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public NoPackagesFoundException(final String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public NoPackagesFoundException(final Throwable cause) {
        super("no packages found", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public NoPackagesFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public NoPackagesFoundException(final SourceDirectory sourceDirectory) {

        super("source directory '{0}' does not exist or can not be found"
                .replace("{0}", sourceDirectory.getPath()));
    }
}