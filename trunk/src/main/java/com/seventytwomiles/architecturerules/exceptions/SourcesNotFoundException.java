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
public class SourcesNotFoundException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public SourcesNotFoundException() {
        super("sources not found");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public SourcesNotFoundException(final String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public SourcesNotFoundException(final Throwable cause) {
        super("sources not found", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public SourcesNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public SourcesNotFoundException(final Collection sources) {


        super("unable to find any source files in given source directories {0}"
                .replace("{0}", Arrays.toString(sources.toArray())));
    }
}
