package com.seventytwomiles.architecturerules;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see RuntimeException
 */
public class DependencyConstraintException extends Exception {


    public DependencyConstraintException() {
        super();
    }


    public DependencyConstraintException(String message) {
        super(message);
    }


    public DependencyConstraintException(Throwable cause) {
        super(cause);
    }


    public DependencyConstraintException(String message, Throwable cause) {
        super(message, cause);
    }


    public DependencyConstraintException(Rule rule) {
        this(rule, null);
    }


    public DependencyConstraintException(Rule rule, Throwable cause) {

        this("Dependency Constraint failed in " + rule.getId()
                + " rule which constrains package " + rule.getPackageName(), cause);
    }
}
