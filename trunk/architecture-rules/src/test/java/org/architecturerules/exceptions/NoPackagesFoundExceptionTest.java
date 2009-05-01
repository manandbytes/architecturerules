/**
 * Copyright 2007, 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com/ and
 *         http://architecturerules.googlecode.com/
 */
package org.architecturerules.exceptions;


import junit.framework.TestCase;

import org.architecturerules.domain.SourceDirectory;


/**
 * <code>NoPackagesFoundException Tester.</code>
 *
 * @author mikenereson
 */
public class NoPackagesFoundExceptionTest extends TestCase {

    public NoPackagesFoundExceptionTest(final String name) {
        super(name);
    }

    public void testInheritance() {

        assertTrue(ArchitectureException.class.isAssignableFrom(NoPackagesFoundException.class));
    }


    @SuppressWarnings({"ThrowableInstanceNeverThrown"
    })
    public void testInterestingConstructors() {

        final NoPackagesFoundException exception;
        final String message;
        final Throwable cause;

        final SourceDirectory sourceDirectory = new SourceDirectory();
        sourceDirectory.setPath("core/target/classes");

        exception = new NoPackagesFoundException(sourceDirectory.getPath());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("source directory 'core/target/classes' does not exist or can not be found", message);

        assertEquals(null, cause);
    }


    @SuppressWarnings({"ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown",
        "ThrowableInstanceNeverThrown"
    })
    public void testTypicalConstructors() {

        NoPackagesFoundException exception;
        String message;
        Throwable cause;

        exception = new NoPackagesFoundException();
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("no packages found"));
        assertEquals(null, cause);

        exception = new NoPackagesFoundException("/core/target/classes");
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("source directory '/core/target/classes' does not exist or can not be found", message);

        assertEquals(null, cause);

        exception = new NoPackagesFoundException(new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("no packages found"));
        assertTrue(cause instanceof IllegalArgumentException);

        exception = new NoPackagesFoundException("/core/target/classes directory not found", new IllegalArgumentException());

        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("/core/target/classes directory not found"));
        assertTrue(cause instanceof IllegalArgumentException);
    }
}
