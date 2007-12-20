package com.seventytwomiles.architecturerules.domain;

/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more information visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


import junit.framework.TestCase;

import java.io.File;



/**
 * <p><code>SourceDirectory</code> Tester.</p>
 *
 * @author mikenereson
 */
public class SourceDirectoryTest extends TestCase {


    /**
     * <p>Instance of a sourceDirectory to test against.</p>
     */
    private SourceDirectory sourceDirectory;


    public SourceDirectoryTest(String name) {
        super(name);
    }


    /**
     * @see TestCase#setName(String)
     */
    public void setUp() throws Exception {
        super.setUp();

        sourceDirectory = new SourceDirectory();
    }


    /**
     * @see TestCase#tearDown()
     */
    public void tearDown() throws Exception {
        sourceDirectory = null;

        super.tearDown();
    }


    /**
     * <p>Test for {@link SourceDirectory#equals(Object)} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testEquals() throws Exception {
        SourceDirectory that;

        sourceDirectory.setPath("core/target/classes");
        that = new SourceDirectory("core/target/classes");
        assertTrue(sourceDirectory.equals(that));
        assertTrue(sourceDirectory.hashCode() == that.hashCode());

        that = new SourceDirectory("web/target/classes");
        assertFalse(sourceDirectory.equals(that));
        assertFalse(sourceDirectory.hashCode() == that.hashCode());
    }


    /**
     * <p>Test for {@link SourceDirectory#SourceDirectory(String)} and {@link
     * SourceDirectory#SourceDirectory(String,boolean)} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testInterestingConstructors() throws Exception {
        /**
         * Test SourceDirectory#SourceDirectory(String)
         */
        sourceDirectory = new SourceDirectory("core/target/classes");
        assertEquals("core" + File.separator + "target" + File.separator + "classes"
                , sourceDirectory.getPath());

        /**
         * Test SourceDirectory#SourceDirectory(String, boolean)
         */
        sourceDirectory = new SourceDirectory("core/target/classes", true);
        assertEquals("core" + File.separator + "target" + File.separator + "classes", sourceDirectory.getPath());
        assertTrue(sourceDirectory.shouldThrowExceptionWhenNotFound());

        sourceDirectory = new SourceDirectory("core/target/classes", false);
        assertEquals("core" + File.separator + "target" + File.separator + "classes", sourceDirectory.getPath());
        assertFalse(sourceDirectory.shouldThrowExceptionWhenNotFound());

        /**
         * Test SourceDirectory#SourceDirectory(String, String)
         */
        sourceDirectory = new SourceDirectory("core/target/classes", "ignore");
        assertEquals("core" + File.separator + "target" + File.separator + "classes", sourceDirectory.getPath());
        assertFalse(sourceDirectory.shouldThrowExceptionWhenNotFound());

        sourceDirectory = new SourceDirectory("core/target/classes", "exception");
        assertEquals("core" + File.separator + "target" + File.separator + "classes", sourceDirectory.getPath());
        assertTrue(sourceDirectory.shouldThrowExceptionWhenNotFound());
    }


    /**
     * <p>Test for {@link SourceDirectory#SourceDirectory(String)} and {@link
     * SourceDirectory#SourceDirectory(String,boolean)} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testInterestingConstructors_illegalArguments() throws Exception {
        try {
            sourceDirectory = new SourceDirectory("");
            fail("expected IllegalArgumentException");
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            sourceDirectory = new SourceDirectory(null);
            fail("expected IllegalArgumentException");
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }


        try {
            sourceDirectory = new SourceDirectory("", true);
            fail("expected IllegalArgumentException");
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            sourceDirectory = new SourceDirectory(null, true);
            fail("expected IllegalArgumentException");
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            sourceDirectory = new SourceDirectory("core/target/classes", "monkey");
            fail("expected IllegalArgumentException");
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }


        try {
            sourceDirectory = new SourceDirectory("core/target/classes", "");
            fail("expected IllegalArgumentException");
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            sourceDirectory = new SourceDirectory("core/target/classes", null);
            fail("expected IllegalArgumentException");
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }


    /**
     * <p>Test for {@link SourceDirectory#setPath(String)} and {@link
     * SourceDirectory#getPath()} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testSetGetPath_windows() throws Exception {

        if (File.separator.equals("\\")) {

            sourceDirectory.setPath("core/target/classes");
            assertEquals("core\\target\\classes", sourceDirectory.getPath());

            sourceDirectory.setPath("core\\target\\classes");
            assertEquals("core\\target\\classes", sourceDirectory.getPath());
        }
    }


    /**
     * <p>Test for {@link SourceDirectory#setPath(String)} and {@link
     * SourceDirectory#getPath()} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testSetGetPath_unix() throws Exception {

        if (File.separator.equals("/")) {

            sourceDirectory.setPath("core/target/classes");
            assertEquals("core/target/classes", sourceDirectory.getPath());

            sourceDirectory.setPath("core\\target\\classes");
            assertEquals("core/target/classes", sourceDirectory.getPath());
        }
    }


    /**
     * <p>Test for {@link SourceDirectory#setPath(String)} and {@link
     * SourceDirectory#getPath()} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testSetGetPath_illegalArguments() throws Exception {
        sourceDirectory.setPath("core/target/classes");
        assertEquals("core" + File.separator + "target" + File.separator + "classes", sourceDirectory.getPath());
    }


    /**
     * <p>Test for {@link SourceDirectory#setNotFound(String)} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testSetNotFound() throws Exception {
        /* check the initial state*/
        assertFalse(sourceDirectory.shouldThrowExceptionWhenNotFound());

        /* change value */
        sourceDirectory.setNotFound("exception");

        /* check ending state */
        assertTrue(sourceDirectory.shouldThrowExceptionWhenNotFound());

        /* check the initial state*/
        assertTrue(sourceDirectory.shouldThrowExceptionWhenNotFound());

        /* change value */
        sourceDirectory.setNotFound("ignore");

        /* check ending state */
        assertFalse(sourceDirectory.shouldThrowExceptionWhenNotFound());
    }


    /**
     * <p>Test for {@link SourceDirectory#setNotFound(String)} when illegal
     * arguments are passed</p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testSetNotFound_illegalArguments() throws Exception {
        try {
            sourceDirectory.setNotFound("");
            fail("expected IllegalArgumentException");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            sourceDirectory.setNotFound(null);
            fail("expected IllegalArgumentException");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            sourceDirectory.setNotFound("http://www.72miles.com");
            fail("expected IllegalArgumentException");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }


    /**
     * <p>Test for {@link SourceDirectory#setShouldThrowExceptionWhenNotFound(boolean)}
     * </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testSetShouldThrowExceptionWhenNotFound() throws Exception {
        sourceDirectory.setShouldThrowExceptionWhenNotFound(true);
        assertTrue(sourceDirectory.shouldThrowExceptionWhenNotFound());

        sourceDirectory.setShouldThrowExceptionWhenNotFound(false);
        assertFalse(sourceDirectory.shouldThrowExceptionWhenNotFound());
    }


    /**
     * <p>Test for {@link SourceDirectory#replaceBackslashForOS(String)} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testFixSlashForOS_windows() throws Exception {

        if (File.separator.equals("\\")) {

            assertEquals("src\\main\\resources", sourceDirectory.replaceBackslashForOS("src\\main\\resources"));
            assertEquals("src\\main\\resources", sourceDirectory.replaceBackslashForOS("src/main/resources"));
        }
    }


    /**
     * <p>Test for {@link SourceDirectory#replaceBackslashForOS(String)} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testFixSlashForOS_unix() throws Exception {

        if (File.separator.equals("/")) {

            assertEquals("src/main/resources", sourceDirectory.replaceBackslashForOS("src\\main\\resources"));
            assertEquals("src/main/resources", sourceDirectory.replaceBackslashForOS("src/main/resources"));
        }
    }

}
