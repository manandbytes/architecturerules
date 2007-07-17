package com.nereson.architecturerules;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see Exception
 */
public class NoPackagesFoundException extends Exception {


    public NoPackagesFoundException() {
        super();
    }


    public NoPackagesFoundException(String message) {
        super(message);
    }


    public NoPackagesFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public NoPackagesFoundException(Throwable cause) {
        super(cause);
    }
}
