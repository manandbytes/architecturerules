package com.seventytwomiles.architecturerules;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see RuntimeException
 */
public class IllegalArchitectureRuleException extends RuntimeException {


    public IllegalArchitectureRuleException() {
        super();
    }


    public IllegalArchitectureRuleException(String message) {
        super(message);
    }


    public IllegalArchitectureRuleException(Throwable cause) {
        super(cause);
    }


    public IllegalArchitectureRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
