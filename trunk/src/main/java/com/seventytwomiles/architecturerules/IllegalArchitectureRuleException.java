package com.seventytwomiles.architecturerules;


/**
 * <p>Exception to be thrown when a Rule is illegal constructed. That is, an
 * illegal violation is created.</p>
 *
 * @author mnereson
 * @noinspection JavaDoc
 * @see RuntimeException
 */
public class IllegalArchitectureRuleException extends RuntimeException {


    /**
     * @see Exception#Exception()
     */
    public IllegalArchitectureRuleException() {
        super();
    }


    /**
     * @see Exception#Exception(String)
     */
    public IllegalArchitectureRuleException(String message) {
        super(message);
    }


    /**
     * @see Exception#Exception(Throwable)
     */
    public IllegalArchitectureRuleException(Throwable cause) {
        super(cause);
    }


    /**
     * @see Exception#Exception(String,Throwable)
     */
    public IllegalArchitectureRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
