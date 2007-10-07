package com.seventytwomiles.architecturerules.domain;


import junit.framework.TestCase;


/**
 * <p><code>Rule</code> Tester.</p>
 *
 * @author mnereson
 */
public class RuleTest extends TestCase {


    /**
     * <p>Instance of a <code>Rule</code> to test against.</p>
     *
     * @parameter rule Rule
     */
    private Rule rule;


    /**
     * <p>Contructs a new test with the given <tt>name</tt></p>
     *
     * @param name String name of the test
     */
    public RuleTest(final String name) {
        super(name);
    }


    /**
     * @see TestCase#setUp()
     */
    public void setUp() throws Exception {

        super.setUp();

        rule = new Rule();
    }


    /**
     * @see TestCase#tearDown()
     */
    public void tearDown() throws Exception {

        rule = null;

        super.tearDown();
    }


    /**
     * <p>Tests for {@link Rule#setId(String)} and {@link Rule#getId()}</p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testSetGetId() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * <p>Tests for {@link Rule#setComment(String)}  and {@link
     * Rule#getComment()} </p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testSetGetComment() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * <p>Tests for {@link Rule#getPackageName()}  and {@link
     * Rule#setPackageName(String)} </p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testSetGetPackageName() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * <p>Tests for {@link Rule#getViolations()}  and {@link
     * Rule#addViolation(String)} </p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testGetViolations() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * <p>Tests for {@link Rule#addViolation(String)} </p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testAddViolation() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * <p>Tests for {@link Rule#removeViolation(String)} </p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testRemoveViolations() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * <p>Tests for {@link Rule#equals(Object)}  </p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testEquals() throws Exception {
        //TODO: Test goes here...
    }
}
