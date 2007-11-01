package com.seventytwomiles.architecturerules.exceptions;


import java.util.Arrays;
import java.util.Collection;


/**
 * <p>Exception to be thrown when a configured source is not found and
 * <samp>&lt;source not-found="exception"></samp></p>
 *
 * @author mnereson
 * @noinspection JavaDoc
 * @see RuntimeException
 */
public class SourceNotFoundException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public SourceNotFoundException() {
        super("sources not found");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public SourceNotFoundException(final String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public SourceNotFoundException(final Throwable cause) {
        super("sources not found", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public SourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public SourceNotFoundException(final Collection sources) {


        super("unable to find any source files in given source directories {0}"
                .replace("{0}", Arrays.toString(sources.toArray())));
    }
}
