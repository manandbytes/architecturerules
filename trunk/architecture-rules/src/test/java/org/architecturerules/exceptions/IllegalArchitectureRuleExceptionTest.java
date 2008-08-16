/**
 * Copyright 2007, 2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * For more information visit
 *         http://72miles.com/ and
 *         http://architecturerules.googlecode.com/
 */

package org.architecturerules.exceptions;


import junit.framework.TestCase;
import org.architecturerules.domain.Rule;


/**
 * <code>IllegalArchitectureRuleException Tester.</code>
 *
 * @author mikenereson
 */
public class IllegalArchitectureRuleExceptionTest extends TestCase {

    public IllegalArchitectureRuleExceptionTest(final String name) {
        super(name);
    }

    public void testInheritance() {

        assertTrue(ArchitectureException.class.isAssignableFrom(IllegalArchitectureRuleException.class));
    }


    @SuppressWarnings({"ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown"
    })
    public void testInterestingConstructors() {

        IllegalArchitectureRuleException exception;
        String message;
        Throwable cause;

        final Rule rule = new Rule();
        rule.setId("dao");
        rule.setComment("dao layer");
        assertTrue(rule.addPackage("com.seventytwomiles.dao"));

        exception = new IllegalArchitectureRuleException(rule.getId(), rule.describePackages());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("rule 'dao' contains an invalid violation that refers to itself; remove violation 'com.seventytwomiles.dao' or change package", message);
        assertEquals(null, cause);

        exception = new IllegalArchitectureRuleException(rule.getId(), rule.describePackages(), new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("rule 'dao' contains an invalid violation that refers to itself; remove violation 'com.seventytwomiles.dao' or change package", message);
        assertTrue(cause instanceof IllegalArgumentException);
    }


    @SuppressWarnings({"ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown"
    })
    public void testTypicalConstructors() {

        IllegalArchitectureRuleException exception;
        String message;
        Throwable cause;

        exception = new IllegalArchitectureRuleException();
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("illegal architecture rule", message);
        assertEquals(null, cause);

        exception = new IllegalArchitectureRuleException("oops, you broke an architecture rule");
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("oops, you broke an architecture rule", message);
        assertEquals(null, cause);

        exception = new IllegalArchitectureRuleException(new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("illegal architecture rule", message);
        assertTrue(cause instanceof IllegalArgumentException);

        exception = new IllegalArchitectureRuleException("oops, you broke an architecture rule", new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("oops, you broke an architecture rule", message);
        assertTrue(cause instanceof IllegalArgumentException);
    }
}
