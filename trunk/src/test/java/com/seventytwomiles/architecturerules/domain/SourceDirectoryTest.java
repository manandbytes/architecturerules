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
        //TODO: Test goes here...
    }


    /**
     * <p>Test for {@link SourceDirectory#setShouldThrowExceptionWhenNotFound(boolean)}
     * </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testSetShouldThrowExceptionWhenNotFound() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * <p>Test for {@link SourceDirectory#setPath(String)} and {@link
     * SourceDirectory#getPath()} </p>
     *
     * @throws Exception when <code>SourceDirectory</code> throws and unexpected
     * <code>Exception</code>
     */
    public void testSetGetPath() throws Exception {
        //TODO: Test goes here...
    }


}
