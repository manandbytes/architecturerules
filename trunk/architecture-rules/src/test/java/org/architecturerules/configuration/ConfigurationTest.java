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
package org.architecturerules.configuration;


import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.architecturerules.api.listeners.Listener;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;
import org.architecturerules.listeners.AbstractListener;
import org.architecturerules.listeners.EmptyListener;
import org.architecturerules.listeners.PrivateConstructorListener;


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
        configuration.getListeners().clear(); // clear default listeners
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
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
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
     * <p>Test for {@link Configuration#addRule(Rule)} when the Rule is invalid</p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
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
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
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
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
     */
    public void testGetSources()
            throws Exception {

        //TOO: Test goes here...
    }


    /**
     * <p>Test for {@link Configuration#setDoCyclicDependencyTest(boolean)} </p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
     */
    public void testSetDoCyclicDependencyTest()
            throws Exception {

        //TOO: Test goes here...
    }


    /**
     * <p>Test for {@link Configuration#setThrowExceptionWhenNoPackages(boolean)} </p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
     */
    public void testSetThrowExceptionWhenNoPackages()
            throws Exception {

        //TOO: Test goes here...
    }


    /**
     * <p>Test for {@link Configuration#addListener(Listener)} </p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
     */
    public void testAddListenerByClass()
            throws Exception {

        Listener emptyListener = new EmptyListener();

        assertTrue(configuration.getListeners().isEmpty());
        configuration.addListener(emptyListener);

        assertEquals(1, configuration.getListeners().size());
        assertTrue(configuration.getListeners().contains(emptyListener));
    }


    /**
     * <p>Test for {@link Configuration#addListener(Listener)}. Tests for illegal arguments like empty strings and
     * <tt>null</tt></p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
     */
    public void testAddListenerByClass_illegalArguments()
            throws Exception {

        Listener nullListener = null;

        assertTrue(configuration.getListeners().isEmpty());

        try {

            configuration.addListener(nullListener);
            fail("expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException e) {

            // expected
        } catch (Exception e) {

            fail("expected IllegalArgumentException");
        }
    }


    /**
     * <p>Test for {@link Configuration#addListener(String)} </p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
     */
    public void testAddListenerByClassName()
            throws Exception {

        String validClassName = EmptyListener.class.getName();

        assertTrue(configuration.getListeners().isEmpty());
        configuration.addListener(validClassName);

        assertEquals(1, configuration.getListeners().size());

        Listener theListener = configuration.getListeners().iterator().next();
        assertTrue(theListener.getClass().getName().equals(validClassName));
    }


    /**
     * <p>Test for {@link Configuration#addListener(String)}. Tests for illegal arguments like empty strings and
     * <tt>null</tt>/p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
     */
    public void testAddListenerByClassName_illegalArguments()
            throws Exception {

        assertTrue(configuration.getListeners().isEmpty());

        String nullString = null;

        try {

            configuration.addListener(nullString);
            fail("expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException e) {

            // expected
        } catch (Exception e) {

            fail("expected IllegalArgumentException");
        }

        assertTrue(configuration.getListeners().isEmpty());

        String emptyString = "";

        try {

            configuration.addListener(emptyString);
            fail("expected IllegalArgumentException for empty String argument");
        } catch (IllegalArgumentException e) {

            // expected
        } catch (Exception e) {

            fail("expected IllegalArgumentException");
        }
    }


    /**
     * <p>Test for {@link Configuration#addListener(String)}. Tests valid arguments that would cause a failure, such as
     * illegal and invalid classes.</p>
     *
     * @throws Exception when <code>Configuration</code> throws unexpected <code>Exception</code>
     */
    public void testAddListenerByClassName_fail()
            throws Exception {

        String invalidClass = Configuration.class.getName();

        assertTrue(configuration.getListeners().isEmpty());

        try {

            configuration.addListener(invalidClass);
            fail("expected IllegalArgumentException for empty String argument");
        } catch (IllegalArgumentException e) {

            assertTrue(e.getMessage().contains("valid Listener implementation"));
        } catch (Exception e) {

            fail("expected IllegalArgumentException");
        }

        String classDoesNotExist = "org.architecturerules.listeners.ItsAWonderfulListener";

        assertTrue(configuration.getListeners().isEmpty());

        try {

            configuration.addListener(classDoesNotExist);
            fail("expected IllegalArgumentException for empty String argument");
        } catch (IllegalArgumentException e) {

            assertTrue(e.getMessage().contains("classpath"));
        } catch (Exception e) {

            fail("expected IllegalArgumentException");
        }

        String whatTheHeck = "HarrrrrveyThePirate";

        assertTrue(configuration.getListeners().isEmpty());

        try {

            configuration.addListener(whatTheHeck);
            fail("expected IllegalArgumentException for empty String argument");
        } catch (IllegalArgumentException e) {

            assertTrue(e.getMessage().contains("classpath"));
        } catch (Exception e) {

            fail("expected IllegalArgumentException");
        }

        String abstractClassThrowsInstantiationException = AbstractListener.class.getName();

        assertTrue(configuration.getListeners().isEmpty());

        try {

            configuration.addListener(abstractClassThrowsInstantiationException);
            fail("expected IllegalArgumentException for empty String argument");
        } catch (IllegalArgumentException e) {

            assertTrue(e.getMessage().contains("instantiate"));
        } catch (Exception e) {

            fail("expected IllegalArgumentException");
        }

        String privateConstructorThrowsIllegalAccessException = PrivateConstructorListener.class.getName();

        assertTrue(configuration.getListeners().isEmpty());

        try {

            configuration.addListener(privateConstructorThrowsIllegalAccessException);
            fail("expected IllegalArgumentException for empty String argument");
        } catch (IllegalArgumentException e) {

            assertTrue(e.getMessage().contains("access"));
        } catch (Exception e) {

            fail("expected IllegalArgumentException");
        }
    }
}
