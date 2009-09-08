/**
 * Copyright 2007 - 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://wiki.architecturerules.org/ and
 *         http://blog.architecturerules.org/
 */
package org.architecturerules.exceptions;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


/**
 * <code>DependencyConstraintException Tester.</code>
 *
 * @author mikenereson
 */
@RunWith(Parameterized.class)
public class DependencyConstraintExceptionTest {

    public DependencyConstraintExceptionTest(Throwable expectedRootCause) {

        this.expectedRootCause = expectedRootCause;
    }

    private static final LinkedList<String> AFFECTED_CLASSES = new LinkedList<String>(Arrays.asList("com.seventytwomiles.dao"));
    private static final String SOME_RULE_ID = "dao";

    @Test
    public void testInheritance() {

        assertTrue(ArchitectureException.class.isAssignableFrom(DependencyConstraintException.class));
    }

    private Throwable expectedRootCause;

    @Parameters
    public static Collection<Object[]> buildParameters() {

        return Arrays.asList(new Object[][] {
                                 {
                                     new IllegalArgumentException()
                                 },
                                 {
                                     null
                                 }
                             });
    }


    @Test
    public void testInterestingConstructors() {

        DependencyConstraintException exception = new DependencyConstraintException(SOME_RULE_ID, "", AFFECTED_CLASSES, expectedRootCause);

        String message = exception.getMessage();
        assertEquals("dependency constraint failed in 'dao' rule: package '' should not be imported by [com.seventytwomiles.dao]", message);

        Throwable cause = exception.getCause();
        assertEquals(expectedRootCause, cause);
    }
}
