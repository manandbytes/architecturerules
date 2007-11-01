package com.seventytwomiles.architecturerules.domain;


import junit.framework.TestCase;


/**
 * <p><code>SourceDirectory</code> Tester.</p>
 *
 * @author mnereson
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
     * <p>Test for {@link SourceDirectory#SourceDirectory(String)} and {@link
     * SourceDirectory#SourceDirectory(String,boolean)} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testInterestingConstructors() throws Exception {


        sourceDirectory = new SourceDirectory("core/target/classes");
        assertEquals("core/target/classes", sourceDirectory.getPath());


        sourceDirectory = new SourceDirectory("core/target/classes", true);
        assertEquals("core/target/classes", sourceDirectory.getPath());
        assertTrue(sourceDirectory.shouldThrowExceptionWhenNotFound());

        sourceDirectory = new SourceDirectory("core/target/classes", false);
        assertEquals("core/target/classes", sourceDirectory.getPath());
        assertFalse(sourceDirectory.shouldThrowExceptionWhenNotFound());
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
     * <p>Test for {@link SourceDirectory#setPath(String)} and {@link
     * SourceDirectory#getPath()} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testSetGetPath() throws Exception {

        sourceDirectory.setPath("core/target/classes");
        assertEquals("core/target/classes", sourceDirectory.getPath());
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
        assertEquals("core/target/classes", sourceDirectory.getPath());
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
}




