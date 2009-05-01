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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * <code>CyclicRedundancyException Tester.</code>
 *
 * @author mikenereson
 */
@SuppressWarnings({"ThrowableInstanceNeverThrown"
})
public class CyclicRedundancyExceptionTest extends TestCase {

    public CyclicRedundancyExceptionTest(final String name) {
        super(name);
    }

    public void testInheritance() {

        assertTrue(ArchitectureException.class.isAssignableFrom(CyclicRedundancyException.class));
    }


    public void testInterestingConstructors() {

        final CyclicRedundancyException exception;
        final String message;
        final Throwable cause;

        exception = new CyclicRedundancyException("com.seventytwomiles.dao", "com.seventytwomiles.dao.hibernate");

        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("'com.seventytwomiles.dao' is involved in an cyclically redundant " + "dependency with 'com.seventytwomiles.dao.hibernate'", message);

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

        exception = new CyclicRedundancyException(new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("cyclic redundancy", message);
        assertTrue(cause instanceof IllegalArgumentException);

        exception = new CyclicRedundancyException("cyclic redundancy found", new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("cyclic redundancy found", message);
        assertTrue(cause instanceof IllegalArgumentException);
    }


    public void testCyclesMap() {

        Map<String, Set<String>> cycles = new HashMap<String, Set<String>>();
        Set<String> packages = new HashSet<String>();
        String springPackage = "test.com.seventytwomiles.web.spring";
        String daoPackage = "test.com.seventytwomiles.dao";
        String hibernatePackage = "test.com.seventytwomiles.dao.hibernate";

        packages.add(hibernatePackage);
        packages.add(daoPackage);
        cycles.put(springPackage, packages);

        CyclicRedundancyException exception = new CyclicRedundancyException();
        exception.getCycles().putAll(cycles);

        assertTrue(exception.getCycles().containsKey(springPackage));

        Set<String> cylePackages = exception.getCycles().get(springPackage);

        assertTrue(cylePackages.contains(daoPackage));
        assertTrue(cylePackages.contains(hibernatePackage));
    }
}
