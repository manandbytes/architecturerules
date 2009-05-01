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
package org.architecturerules.domain;


import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.architecturerules.exceptions.IllegalArchitectureRuleException;


/**
 * <p><code>Rule</code> Tester.</p>
 *
 * @author mikenereson
 */
public class RuleTest extends TestCase {

    /**
     * <p>Instance of a <code>Rule</code> to test against.</p>
     *
     * @parameter rule Rule
     */
    private Rule rule;

    /**
     * <p>Contracts a new test with the given <tt>name</tt></p>
     *
     * @param name String name of the test
     */
    public RuleTest(final String name) {
        super(name);
    }

    /**
     * @see TestCase#setUp()
     */
    @Override
    public void setUp()
            throws Exception {

        super.setUp();

        rule = new Rule();
    }


    /**
     * @see TestCase#tearDown()
     */
    @Override
    public void tearDown()
            throws Exception {

        rule = null;

        super.tearDown();
    }


    /**
     * <p>Tests for {@link Rule#getViolations()}, {@link Rule#addViolation(String)} and {@link
     * Rule#removeViolation(String)}</p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected <code>Exception</code>
     */
    public void testAddGetViolations()
            throws Exception {

        final JPackage violation1 = new JPackage("com.seventytwomiles.dao");
        final JPackage violation2 = new JPackage("com.seventytwomiles.dao.hibernate");
        final JPackage violation3 = new JPackage("com.seventytwomiles.package.does.not.exist");

        assertEquals(0, rule.getViolations().size());

        assertNotNull(rule.addViolation(violation1));
        assertTrue(rule.getViolations().contains(violation1));
        assertEquals(1, rule.getViolations().size());

        assertNotNull(rule.addViolation(violation2));
        assertTrue(rule.getViolations().contains(violation2));
        assertEquals(2, rule.getViolations().size());

        assertNotNull(rule.addViolation(violation1));
        assertTrue(rule.getViolations().contains(violation1));
        assertEquals(2, rule.getViolations().size());

        assertNotNull(rule.removeViolation(violation1));
        assertFalse(rule.getViolations().contains(violation1));
        assertEquals(1, rule.getViolations().size());

        assertNotNull(rule.removeViolation(violation3));
        assertFalse(rule.getViolations().contains(violation3));
        assertEquals(1, rule.getViolations().size());
    }


    /**
     * <p>Tests for {@link Rule#getViolations()}, {@link Rule#addViolation(String)} and {@link
     * Rule#removeViolation(String)}</p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected <code>Exception</code>
     */
    public void testAddGetViolations_illegalArguments()
            throws Exception {

        try {

            rule.addViolation("");
            fail("expected AssertionFailedError because violation can not be null");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("violation") > -1);
        }

        try {

            rule.addViolation(new JPackage(""));
            fail("expected AssertionFailedError because violation can not be null");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("violation") > -1);
        }

        try {

            rule.addViolation((JPackage) null);
            fail("expected AssertionFailedError because violation can not be null");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("violation") > -1);
        }

        try {

            rule.addViolation((String) null);
            fail("expected AssertionFailedError because violation can not be null");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("violation") > -1);
        }

        try {

            rule.getViolations().remove("com.seventytwomiles.dao");
            fail("expected UnsupportedOperationException");
        } catch (final UnsupportedOperationException e) {

            // success
        }

        try {

            rule.getViolations().remove(new JPackage("com.seventytwomiles.dao"));
            fail("expected UnsupportedOperationException");
        } catch (final UnsupportedOperationException e) {

            // success
        }

        rule = new Rule("dao");
        assertTrue(rule.addPackage("com.seventytwomiles.dao"));

        try {

            rule.addViolation("com.seventytwomiles.dao");
            fail("expected IllegalArchitectureRuleException because packageName can not also be a violation");
        } catch (final IllegalArchitectureRuleException e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("com.seventytwomiles.dao") > -1);
        }

        try {

            rule.removeViolation("");
            fail("expected AssertionFailedError because violation can not be null");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("violation") > -1);
        }

        try {

            rule.removeViolation((JPackage) null);
            fail("expected AssertionFailedError because violation can not be null");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("violation") > -1);
        }

        try {

            rule.removeViolation((String) null);
            fail("expected AssertionFailedError because violation can not be null");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("violation") > -1);
        }
    }


    public void testAddPackage()
            throws Exception {

        //TOO: write this method
    }


    public void testAddPackage_illegalArguments()
            throws Exception {

        //TOO: write this method
    }


    public void testDescribe()
            throws Exception {

        rule = new Rule("web", "com.seventytwomiles.web");
        rule.addViolation("com.seventytwomiles.dao");

        final String description = rule.describe();

        assertTrue(description.indexOf("web") > -1);
        assertTrue(description.indexOf("com.seventytwomiles.web") > -1);
    }


    /**
     * <p>Tests for {@link Rule#equals(Object)}  </p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected <code>Exception</code>
     */
    public void testEquals()
            throws Exception {

        Rule that = new Rule("web");
        assertTrue(that.addPackage("com.seventytwomiles.web"));

        rule.setId("web");
        assertTrue(rule.addPackage("com.seventytwomiles.web"));
        assertTrue(rule.equals(that));
        assertTrue(rule.hashCode() == that.hashCode());

        that = new Rule("controllers");
        assertTrue(that.addPackage("com.seventytwomiles.web.controllers"));
        assertFalse(rule.equals(that));
        assertFalse(rule.hashCode() == that.hashCode());
    }


    /**
     * <p>Tests for {@link Rule#Rule(String)}</p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected <code>Exception</code>
     */
    public void testInterestingConstructors()
            throws Exception {

        rule = new Rule("dao");
        assertTrue(rule.getId().equals("dao"));
    }


    public void testInterestingConstructors_illegalArguments()
            throws Exception {

        try {

            rule = new Rule(null);
            fail("expected AssertionFailedError because id can not be null");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("id") > -1);
        }

        try {

            rule = new Rule("");
            fail("expected AssertionFailedError because id can not be empty");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("id") > -1);
        }
    }


    /**
     * <p>Tests for {@link Rule#setComment(String)}  and {@link Rule#getComment()} </p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected <code>Exception</code>
     */
    public void testSetGetComment()
            throws Exception {

        rule.setComment("controllers are cool");
        assertTrue(rule.getComment().equals("controllers are cool"));

        rule.setComment("");
        assertTrue(rule.getComment().equals(""));
    }


    public void testSetGetComment_illegalArguments()
            throws Exception {

        try {

            rule.setComment(null);
            fail("expected AssertionFailedError because comment can not be null");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("comment") > -1);
        }
    }


    /**
     * <p>Tests for {@link Rule#setId(String)} and {@link Rule#getId()}</p>
     *
     * @throws Exception when <code>Rule</code> throws an unexpected <code>Exception</code>
     */
    public void testSetGetId()
            throws Exception {

        rule.setId("controllers");
        assertTrue(rule.getId().equals("controllers"));
    }


    public void testSetGetId_illegalArguments()
            throws Exception {

        try {

            rule.setId("");
            fail("expected AssertionFailedError because id can not be empty");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("id") > -1);
        }

        try {

            rule.setId(null);
            fail("expected AssertionFailedError because id can not be null");
        } catch (final AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("id") > -1);
        }
    }
}
