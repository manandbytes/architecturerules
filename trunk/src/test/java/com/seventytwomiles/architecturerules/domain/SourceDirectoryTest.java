package com.seventytwomiles.architecturerules.domain;


import junit.framework.AssertionFailedError;
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

        try {

            sourceDirectory.setPath(null);
            fail("excpected AssertionFailedError");

        } catch (AssertionFailedError e) {

        }

        try {

            sourceDirectory.setPath("");
            fail("excpected AssertionFailedError");

        } catch (AssertionFailedError e) {

        }
    }


    /**
     * <p>Test for {@link SourceDirectory#equals(Object)} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testEquals() {

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


