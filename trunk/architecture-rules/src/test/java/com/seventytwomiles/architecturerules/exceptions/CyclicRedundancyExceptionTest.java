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


import junit.framework.TestCase;


/**
 * <code>CyclicRedundancyException Tester.</code>
 *
 * @author mikenereson
 */
@SuppressWarnings({"ThrowableInstanceNeverThrown"})
public class CyclicRedundancyExceptionTest extends TestCase {


    public CyclicRedundancyExceptionTest(final String name) {
        super(name);
    }


    public void testInterestingConstructors() {

        final CyclicRedundancyException exception;
        final String message;
        final Throwable cause;


        exception = new CyclicRedundancyException("com.seventytwomiles.dao",
                "com.seventytwomiles.dao.hibernate");

        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals(
                "'com.seventytwomiles.dao' is involved in an cyclically redundant " +
                        "dependency with 'com.seventytwomiles.dao.hibernate'",
                message);

        assertEquals(null, cause);
    }


    public void testTypicalConstructors() {

        CyclicRedundancyException exception;
        String message;
        Throwable cause;

        exception = new CyclicRedundancyException();
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("cyclic redundancy", message);
        assertEquals(null, cause);

        exception = new CyclicRedundancyException("cyclic redundancy found");
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("cyclic redundancy found", message);
        assertEquals(null, cause);


        exception = new CyclicRedundancyException(
                new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("cyclic redundancy", message);
        assertTrue(cause instanceof IllegalArgumentException);


        exception = new CyclicRedundancyException("cyclic redundancy found",
                new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("cyclic redundancy found", message);
        assertTrue(cause instanceof IllegalArgumentException);
    }
}
