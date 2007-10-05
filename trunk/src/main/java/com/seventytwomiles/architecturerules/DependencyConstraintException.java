package com.seventytwomiles.architecturerules;


/**
 * <p>Exception to be thrown when any <code>Rule</code> fails, that is to say,
 * the rule is violoated</p>
 *
 * @author mnereson
 * @see RuntimeException
 */
public class DependencyConstraintException extends Exception {


    /**
     * @see Exception#Exception()
     */
    public DependencyConstraintException() {
        super();
    }


    /**
     * @see Exception#Exception(String)
     */
    public DependencyConstraintException(String message) {
        super(message);
    }


    /**
     * @see Exception#Exception(Throwable)
     */
    public DependencyConstraintException(Throwable cause) {
        super(cause);
    }


    /**
     * @see Exception#Exception(String,Throwable)
     */
    public DependencyConstraintException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * <p></p>
     *
     * @param rule Rule that failed
     */
    public DependencyConstraintException(final Rule rule) {
        this(rule, null);
    }


    /**
     * @param rule Rule that failed
     * @param cause Throwable
     */
    public DependencyConstraintException(final Rule rule, final Throwable cause) {

        this("Dependency Constraint failed in " + rule.getId()
                + " rule which constrains package " + rule.getPackageName(), cause);
    }
}
