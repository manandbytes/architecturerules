package com.seventytwomiles.architecturerules.exceptions;


/**
 * <p>Exception to be thrown when any <code>Rule</code> fails, that is to say,
 * the rule is violoated</p>
 *
 * @author mikenereson
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
     * <p>Reports which <code>Rule</code> was broken, by its <tt>id</tt>, and
     * what packages that <code>Rule</code> governs.</p>
     *
     * @param ruleId
     * @param packages
     * @param cause
     */
    public DependencyConstraintException(final String ruleId, final String packages, final Throwable cause) {

        this("dependency constraint failed in '{id}' rule which constrains packages '{efferent}'"
                .replace("{id}", ruleId)
                .replace("{efferent}", packages)
                .replace("[", "")
                .replace("]", ""), cause);
    }
}
