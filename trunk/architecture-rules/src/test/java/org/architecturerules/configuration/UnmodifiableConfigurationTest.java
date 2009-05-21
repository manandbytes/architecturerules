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
 *         http://wiki.architecturerules.org/ and
 *         http://blog.architecturerules.org
 */
package org.architecturerules.configuration;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;
import org.architecturerules.listeners.EmptyListener;


/**
 * <p>UnmodifiableConfiguration Tester.</p>
 *
 * @author mikenereson
 */
public class UnmodifiableConfigurationTest extends TestCase {

    private Configuration configuration = new Configuration();

    public static Test suite() {

        return new TestSuite(UnmodifiableConfigurationTest.class);
    }

    public UnmodifiableConfigurationTest(final String name) {
        super(name);
    }

    @Override
    public void setUp()
            throws Exception {

        configuration.addSource(new SourceDirectory("core/target/classes"));
        configuration.addSource(new SourceDirectory("dao/target/classes"));

        final Rule rule = new Rule("dao", "com.seventytwomiles.dao");
        rule.addViolation("com.seventymiles.services");
        configuration.addRule(rule);

        final Rule rule1 = new Rule("model", "com.seventytwomiles.model");
        rule1.addViolation("com.seventymiles.services");
        configuration.addRule(rule1);

        configuration.setDoCyclicDependencyTest(true);
        configuration.setThrowExceptionWhenNoPackages(true);

        super.setUp();
    }


    @Override
    public void tearDown()
            throws Exception {

        configuration = null;

        super.tearDown();
    }


    public void testUnmodifiability() {

        final Configuration unmodifiableConfiguration = new UnmodifiableConfiguration(configuration);

        try {

            unmodifiableConfiguration.getRules().add(new Rule("test"));
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.addRule(new Rule("test"));
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.getSources().add(new SourceDirectory("web/target/classes"));
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.addSource(new SourceDirectory("web/target/classes"));
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.setDoCyclicDependencyTest(false);
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.setThrowExceptionWhenNoPackages(false);
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.addListener(new EmptyListener());
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.addListener(EmptyListener.class.getName());
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.getListeners().add(new EmptyListener());
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.removeListener(new EmptyListener());
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.removeListener(EmptyListener.class.getName());
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {

            unmodifiableConfiguration.getListeners().remove(new EmptyListener());
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {

            assertTrue(e instanceof UnsupportedOperationException);
        }
    }
}
