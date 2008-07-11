/**
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com/svn/docs/index.html
 */

package com.seventytwomiles.architecturerules.exceptions;

/**
 * <p>Exception to be thrown when any <code>Rule</code> fails, that is to say,
 * the rule is violoated.</p>
 *
 * @author mikenereson
 * @see ArchitectureException
 */
public class DependencyConstraintException extends ArchitectureException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public DependencyConstraintException() {
        super("dependency constraint");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public DependencyConstraintException(final String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public DependencyConstraintException(final Throwable cause) {
        super("dependency constraint", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public DependencyConstraintException(final String message,
                                         final Throwable cause) {
        super(message, cause);
    }


    /**
     * <p>Reports which <code>Rule</code> was broken, by its <tt>id</tt>, and
     * what packages that <code>Rule</code> governs.</p>
     *
     * @param ruleId   String id of the <code>Rule</code> that was violated.
     * @param packages String listing each package constrained by the violated
     *                 <code>Rule</code>
     * @param cause    Throwable any exception that was thrown
     */
    public DependencyConstraintException(final String ruleId,
                                         final String packages,
                                         final Throwable cause) {

        this("dependency constraint failed in '{id}' rule which constrains packages '{efferent}'"
                .replaceAll("\\{id}", ruleId)
                .replaceAll("\\{efferent}", packages.trim())
                .replaceAll("\\[", "")
                .replaceAll("\\]", ""), cause);
    }
}
