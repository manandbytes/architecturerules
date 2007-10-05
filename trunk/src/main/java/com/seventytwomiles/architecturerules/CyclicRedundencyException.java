package com.seventytwomiles.architecturerules;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see RuntimeException
 */
public class CyclicRedundencyException extends Exception {


    public CyclicRedundencyException() {
        super();
    }


    public CyclicRedundencyException(String message) {
        super(message);
    }


    public CyclicRedundencyException(Throwable cause) {
        super(cause);
    }


    public CyclicRedundencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
