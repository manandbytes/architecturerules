package com.seventytwomiles.architecturerules.exceptions;


import com.seventytwomiles.architecturerules.domain.Rule;


/**
 * <p>Exception to be thrown when any <code>Rule</code> fails, that is to say,
 * the rule is violoated</p>
 *
 * @author mnereson
 * @noinspection JavaDoc
 * @see RuntimeException
 */
public class DependencyConstraintException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public DependencyConstraintException() {
        super("dependency constraint");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public DependencyConstraintException(String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public DependencyConstraintException(Throwable cause) {
        super("dependency constraint", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
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

        this("dependency constraint failed in '{id}' rule which constrains package '{efferent}'"
                .replace("{id}", rule.getId())
                .replace("{efferent}", rule.getPackageName()),
                cause);
    }
}
