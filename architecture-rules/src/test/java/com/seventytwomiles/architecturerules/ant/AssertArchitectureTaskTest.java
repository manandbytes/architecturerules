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

package com.seventytwomiles.architecturerules.ant;


import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;
import junit.framework.TestCase;



/**
 * <p><code>AssertArchitectureTask</code> Tester.</p>
 *
 * @author mikenereson
 */
public class AssertArchitectureTaskTest extends TestCase {


    private AssertArchitectureTask task;


    public AssertArchitectureTaskTest(final String name) {
        super(name);
    }


    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        task = new AssertArchitectureTask();
    }


    public void testExecute() throws Exception {

        try {

            task.setConfigurationFileName("architecture-rules.xml");
            task.execute();

        } catch (Exception e) {

            assertTrue(e instanceof CyclicRedundancyException);
        }
    }


    public void testExecute_invalidArguments() throws Exception {

        try {

            task.execute();
            fail("expected IllegalStateException");

        } catch (Exception e) {

            assertTrue(e instanceof IllegalStateException);
        }


        try {

            task.setConfigurationFileName(null);
            task.execute();
            fail("expected IllegalStateException");

        } catch (Exception e) {

            assertTrue(e instanceof IllegalStateException);
        }


        try {

            task.setConfigurationFileName("");
            task.execute();
            fail("expected IllegalStateException");

        } catch (Exception e) {

            assertTrue(e instanceof IllegalStateException);
        }
    }
}