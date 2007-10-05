package com.seventytwomiles.architecturerules;


/**
 * <p>Thrown to indicate that a cyclic redendency was found.</p>
 *
 * TODO: report which packages were cyclical
 *
 * @author mnereson
 * @see RuntimeException
 */
public class CyclicRedundencyException extends Exception {


    /**
     * @see Exception#Exception()
     */
    public CyclicRedundencyException() {
        super();
    }


    /**
     * @see Exception#Exception(String)
     */
    public CyclicRedundencyException(String message) {
        super(message);
    }


    /**
     * @see Exception#Exception(Throwable)
     */
    public CyclicRedundencyException(Throwable cause) {
        super(cause);
    }


    /**
     * @see Exception#Exception(String,Throwable)
     */
    public CyclicRedundencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
