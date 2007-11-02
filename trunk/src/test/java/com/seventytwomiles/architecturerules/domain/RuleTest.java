package com.seventytwomiles.architecturerules.domain;


import com.seventytwomiles.architecturerules.exceptions.IllegalArchitectureRuleException;
import junit.framework.AssertionFailedError;
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
     * <p>Tests for {@link Rule#Rule(String)}</p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testInterestingConstructors() throws Exception {

        rule = new Rule("dao");
        assertTrue(rule.getId().equals("dao"));
    }


    public void testInterestingConstructors_illegalArguments() throws Exception {

        try {

            rule = new Rule(null);
            fail("expected AssertionFailedError because id can not be null");

        } catch (final AssertionFailedError e) {

            assertTrue(e.getMessage().contains("id"));
        }


        try {

            rule = new Rule("");
            fail("expected AssertionFailedError because id can not be empty");

        } catch (final AssertionFailedError e) {

            assertTrue(e.getMessage().contains("id"));
        }

    }


    /**
     * <p>Tests for {@link Rule#setId(String)} and {@link Rule#getId()}</p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testSetGetId() throws Exception {

        rule.setId("controllers");
        assertTrue(rule.getId().equals("controllers"));
    }


    public void testSetGetId_illegalArguments() throws Exception {

        try {

            rule.setId("");
            fail("expected AssertionFailedError because id can not be empty");

        } catch (final AssertionFailedError e) {

            assertTrue(e.getMessage().contains("id"));
        }


        try {

            rule.setId(null);
            fail("expected AssertionFailedError because id can not be null");

        } catch (final AssertionFailedError e) {

            assertTrue(e.getMessage().contains("id"));
        }
    }


    /**
     * <p>Tests for {@link Rule#setComment(String)}  and {@link
     * Rule#getComment()} </p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testSetGetComment() throws Exception {

        rule.setComment("controllers are cool");
        assertTrue(rule.getComment().equals("controllers are cool"));

        rule.setComment("");
        assertTrue(rule.getComment().equals(""));
    }


    public void testSetGetComment_illegalArguments() throws Exception {

        try {

            rule.setComment(null);
            fail("expected AssertionFailedError because comment can not be null");

        } catch (final AssertionFailedError e) {

            assertTrue(e.getMessage().contains("comment"));
        }
    }


    public void testAddPackage() throws Exception {

    }


    public void testAddPackage_illegalArguments() throws Exception {

    }


    /**
     * <p>Tests for {@link Rule#getViolations()}, {@link
     * Rule#addViolation(String)} and {@link Rule#removeViolation(String)}</p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testAddGetViolations() throws Exception {

        assertEquals(0, rule.getViolations().size());


        assertTrue(rule.addViolation("com.seventytwomiles.dao"));
        assertEquals(1, rule.getViolations().size());

        assertTrue(rule.addViolation("com.seventytwomiles.dao.hibernate"));
        assertEquals(2, rule.getViolations().size());

        assertFalse(rule.addViolation("com.seventytwomiles.dao"));
        assertEquals(2, rule.getViolations().size());


        assertTrue(rule.removeViolation("com.seventytwomiles.dao"));
        assertEquals(1, rule.getViolations().size());


        assertFalse(rule.removeViolation("com.seventytwomiles.package.does.not.exist"));
        assertEquals(1, rule.getViolations().size());
    }


    /**
     * <p>Tests for {@link Rule#getViolations()}, {@link
     * Rule#addViolation(String)} and {@link Rule#removeViolation(String)}</p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testAddGetViolations_illegalArguments() throws Exception {
        try {

            rule.addViolation("");
            fail("expected AssertionFailedError because violation can not be null");

        } catch (final AssertionFailedError e) {

            assertTrue(e.getMessage().contains("violation"));
        }


        try {

            rule.addViolation(null);
            fail("expected AssertionFailedError because violation can not be null");

        } catch (final AssertionFailedError e) {

            assertTrue(e.getMessage().contains("violation"));
        }


        try {

            rule.getViolations().remove("com.seventytwomiles.dao");
            fail("expected UnsupportedOperationException");

        } catch (final UnsupportedOperationException e) {

            // success
        }

        rule = new Rule("dao");
        rule.addPackage("com.seventytwomiles.dao");

        try {

            rule.addViolation("com.seventytwomiles.dao");
            fail("expected IllegalArchitectureRuleException because packageName can not also be a violation");

        } catch (final IllegalArchitectureRuleException e) {

            assertTrue(e.getMessage().contains("com.seventytwomiles.dao"));
        }


        try {

            rule.removeViolation("");
            fail("expected AssertionFailedError because violation can not be null");

        } catch (final AssertionFailedError e) {

            assertTrue(e.getMessage().contains("violation"));
        }


        try {

            rule.removeViolation(null);
            fail("expected AssertionFailedError because violation can not be null");

        } catch (final AssertionFailedError e) {

            assertTrue(e.getMessage().contains("violation"));
        }
    }


    /**
     * <p>Tests for {@link Rule#equals(Object)}  </p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected
     * <code>Exception</code>
     */
    public void testEquals() throws Exception {


        Rule that = new Rule("web");
        that.addPackage("com.seventytwomiles.web");

        rule.setId("web");
        rule.addPackage("com.seventytwomiles.web");
        assertTrue(rule.equals(that));
        assertTrue(rule.hashCode() == that.hashCode());

        that = new Rule("controllers");
        that.addPackage("com.seventytwomiles.web.controllers");
        assertFalse(rule.equals(that));
        assertFalse(rule.hashCode() == that.hashCode());
    }


    public void testDescribe() throws Exception {

        rule = new Rule("web", "com.seventytwomiles.web");
        rule.addViolation("com.seventytwomiles.dao");

        String description = rule.describe();

        assertTrue(description.contains("web"));
        assertTrue(description.contains("com.seventytwomiles.web"));
    }
}
