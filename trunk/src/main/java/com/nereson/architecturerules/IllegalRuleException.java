package com.nereson.architecturerules;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see RuntimeException
 */
public class IllegalRuleException extends RuntimeException {


    public IllegalRuleException() {
        super();
    }


    public IllegalRuleException(String message) {
        super(message);
    }


    public IllegalRuleException(Throwable cause) {
        super(cause);
    }


    public IllegalRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
