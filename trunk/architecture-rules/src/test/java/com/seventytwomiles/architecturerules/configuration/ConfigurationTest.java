/**
 * Copyright 2007, 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com/
 */
package com.seventytwomiles.architecturerules.configuration;


import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;


/**
 * <p>Configuration Tester</p>
 *
 * @author mikenereson
 */
public class ConfigurationTest extends TestCase {

    /**
     * <p>Instance of <code>Configuration</code> to test against.</p>
     *
     * @parameter configuration Configuration
     */
    private Configuration configuration;

    /**
     * <p>Instantiates a new test with the given <tt>name</tt>.</p>
     *
     * @param name String name to give test
     */
    public ConfigurationTest(final String name) {
        super(name);
    }

    public void addSource() {

        SourceDirectory sourceDirectory = new SourceDirectory("core/target/classes");

        assertNotNull(configuration.addSource(sourceDirectory));

        assertTrue(configuration.getSources().contains(sourceDirectory));
        assertEquals(1, configuration.getSources().size());
    }


    public void addSource_illegalArguments() {

        try {

            configuration.addSource(null);
            fail("expected IllegalArgumentException");
        } catch (Exception e) {

            assertTrue(e instanceof IllegalArgumentException);
        }

        try {

            configuration.addSource(new SourceDirectory(""));
            fail("expected AssertionFailedError");
        } catch (Exception e) {

            assertTrue(e instanceof IllegalArgumentException);
        }
    }


    /**
     * @see TestCase#setUp()
     */
    @Override
    public void setUp()
            throws Exception {

        super.setUp();

        configuration = new Configuration();
    }


    /**
     * @see TestCase#tearDown()
     */
    @Override
    public void tearDown()
            throws Exception {

        configuration = null;

        super.tearDown();
    }


    /**
     * <p>Test for {@link Configuration#addRule(Rule)} </p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected
     *                   <code>Exception</code>
     */
    public void testAddRule()
            throws Exception {

        /* is empty by default */
        assertTrue(configuration.getRules().isEmpty());

        final Rule rule;

        rule = new Rule("dao", "com.seventytwomiles.dao");
        rule.addViolation("com.seventytwomiles.web.controllers");

        configuration.addRule(rule);

        assertEquals(1, configuration.getRules().size());
        assertTrue(configuration.getRules().contains(rule));
    }


    /**
     * <p>Test for {@link Configuration#addRule(Rule)} when the Rule is
     * invalid</p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected
     *                   <code>Exception</code>
     */
    public void testAddRule_illegalArguments()
            throws Exception {

        Rule rule = null;

        try {

            configuration.addRule(rule);
            fail("expected AssertionFailedError");
        } catch (AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("rule can not be null") > -1);
        }

        try {

            rule = new Rule();
            configuration.addRule(rule);
            fail("expected AssertionFailedError");
        } catch (AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("rule id can not be null") > -1);
        }

        try {

            rule.setId("");
            configuration.addRule(rule);
            fail("expected AssertionFailedError");
        } catch (AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("id can not be empty") > -1);
        }

        try {

            rule.setId("validId");
            configuration.addRule(rule);
            fail("expected AssertionFailedError");
        } catch (AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("rule packages must not be empty") > -1);
        }

        try {

            rule.addPackage("com.seventytwomiles.dao");
            configuration.addRule(rule);
            fail("expected AssertionFailedError");
        } catch (AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("rule violations must not be empty") > -1);
        }
    }


    /**
     * <p>Test for {@link Configuration#getRules()} </p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected
     *                   <code>Exception</code>
     */
    public void testGetRules()
            throws Exception {

        /* is empty by default */
        assertTrue(configuration.getRules().isEmpty());

        /* construct a new Rule and add to configuration */
        final Rule rule = new Rule("dao", "com.seventytwomiles.dao");
        rule.addViolation("com.seventytwomiles.web.controllers");

        assertNotNull(configuration.addRule(rule));
        assertTrue(configuration.getRules().contains(rule));

        /* assert increase in size and that it contains the Rule */
        assertEquals(1, configuration.getRules().size());
        assertTrue(configuration.getRules().contains(rule));
    }


    /**
     * <p>Test for {@link Configuration#getSources()}</p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected
     *                   <code>Exception</code>
     */
    public void testGetSources()
            throws Exception {

        //TOO: Test goes here...
    }


    /**
     * <p>Test for {@link Configuration#setDoCyclicDependencyTest(boolean)}
     * </p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected
     *                   <code>Exception</code>
     */
    public void testSetDoCyclicDependencyTest()
            throws Exception {

        //TOO: Test goes here...
    }


    /**
     * <p>Test for {@link Configuration#setThrowExceptionWhenNoPackages(boolean)}
     * </p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected
     *                   <code>Exception</code>
     */
    public void testSetThrowExceptionWhenNoPackages()
            throws Exception {

        //TOO: Test goes here...
    }
}
