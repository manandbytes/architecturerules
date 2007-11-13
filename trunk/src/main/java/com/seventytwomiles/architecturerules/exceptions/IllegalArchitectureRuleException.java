package com.seventytwomiles.architecturerules.exceptions;


/**
 * <p>Exception to be thrown when a Rule is illegal constructed. That is, an
 * illegal violation is created.</p>
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see RuntimeException
 */
public class IllegalArchitectureRuleException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public IllegalArchitectureRuleException() {
        super("illegal architecture rule");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public IllegalArchitectureRuleException(String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public IllegalArchitectureRuleException(Throwable cause) {
        super("illegal architecture rule", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public IllegalArchitectureRuleException(String message, Throwable cause) {
        super(message, cause);
    }


    public IllegalArchitectureRuleException(final String ruleId, final String rulePackages) {

        this(ruleId, rulePackages, null);
    }


    public IllegalArchitectureRuleException(final String ruleId, final String rulePackages, final Throwable cause) {

        super("rule '{id}' contains an invalid violation that referes to itself; remove violation '{violation}' or change package"
                .replace("{id}", ruleId)
                .replace("{violation}", rulePackages)
                .replace("[", "")
                .replace("]", ""),
              cause);
    }
}
