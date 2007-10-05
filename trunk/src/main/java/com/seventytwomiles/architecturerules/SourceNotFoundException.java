package com.seventytwomiles.architecturerules;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see Exception
 */
public class SourceNotFoundException extends Exception {


    public SourceNotFoundException() {
        super();
    }


    public SourceNotFoundException(String message) {
        super(message);
    }


    public SourceNotFoundException(Throwable cause) {
        super(cause);
    }


    public SourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
