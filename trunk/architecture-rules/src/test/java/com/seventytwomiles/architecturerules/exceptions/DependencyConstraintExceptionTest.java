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


import com.seventytwomiles.architecturerules.domain.Rule;
import junit.framework.TestCase;


/**
 * <code>DependencyConstraintException Tester.</code>
 *
 * @author mikenereson
 */
public class DependencyConstraintExceptionTest extends TestCase {


    public DependencyConstraintExceptionTest(final String name) {
        super(name);
    }

    public void testInheritance() {
        assertTrue(ArchitectureException.class.isAssignableFrom(DependencyConstraintException.class));
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown", "ThrowableInstanceNeverThrown", "ThrowableInstanceNeverThrown"})
    public void testInterestingConstructors() {

        DependencyConstraintException exception;
        String message;
        Throwable cause;

        final Rule rule = new Rule();
        rule.setId("dao");
        rule.setComment("dao layer");
        assertTrue(rule.addPackage("com.seventytwomiles.dao"));
        rule.addViolation("com.seventytwomiles.web.controllers");

        exception = new DependencyConstraintException(rule.getId(),
                rule.describePackages(), null);
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals(
                "dependency constraint failed in 'dao' rule which constrains packages 'com.seventytwomiles.dao'",
                message);
        assertEquals(null, cause);


        exception = new DependencyConstraintException(rule.getId(),
                rule.describePackages(), new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals(
                "dependency constraint failed in 'dao' rule which constrains packages 'com.seventytwomiles.dao'",
                message);
        assertTrue(cause instanceof IllegalArgumentException);
    }


    @SuppressWarnings({"ThrowableInstanceNeverThrown", "ThrowableInstanceNeverThrown", "ThrowableInstanceNeverThrown", "ThrowableInstanceNeverThrown", "ThrowableInstanceNeverThrown", "ThrowableInstanceNeverThrown"})
    public void testTypicalConstructors() {
        DependencyConstraintException exception;
        String message;
        Throwable cause;

        final Rule rule = new Rule();
        rule.setId("dao");
        rule.setComment("dao layer");
        assertTrue(rule.addPackage("com.seventytwomiles.dao"));
        rule.addViolation("com.seventytwomiles.web.controllers");

        exception = new DependencyConstraintException();
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("dependency constraint", message);
        assertEquals(null, cause);

        exception = new DependencyConstraintException(
                "dependency constraint violated");
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("dependency constraint violated", message);
        assertEquals(null, cause);


        exception = new DependencyConstraintException(
                new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("dependency constraint", message);
        assertTrue(cause instanceof IllegalArgumentException);


        exception = new DependencyConstraintException(
                "dependency constraint violated",
                new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("dependency constraint violated", message);
        assertTrue(cause instanceof IllegalArgumentException);
    }
}
