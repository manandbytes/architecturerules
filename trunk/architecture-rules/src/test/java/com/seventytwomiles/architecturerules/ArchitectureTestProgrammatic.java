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
 *         http://architecturerules.googlecode.com
 */
package com.seventytwomiles.architecturerules;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import com.seventytwomiles.architecturerules.exceptions.DependencyConstraintException;


/**
 * <p>Tests rules. Also tests programmatic-only configuration.</p>
 *
 * @author mikenereson
 * @see AbstractArchitectureRulesConfigurationTest
 */
public class ArchitectureTestProgrammatic extends AbstractArchitectureRulesConfigurationTest {

    /**
     * <p>Expect this test to fail because presentationRule will be
     * violated</p>
     */
    @Override
    public void testArchitecture() {

        /* do nothing, this method is required */
    }


    /**
     * <p>Expect this test to fail because presentationRule will be
     * violated</p>
     */
    public void testArchitecture_fail() {

        final Configuration configuration = getConfiguration();

        configuration.addSource(new SourceDirectory("target\\test-classes", true));

        configuration.setDoCyclicDependencyTest(false);
        configuration.setThrowExceptionWhenNoPackages(true);

        final Rule presentationRule = new Rule("presentation");
        presentationRule.setComment("presentation may not access dao directly.");

        presentationRule.addPackage("test.com.seventytwomiles.web.spring");
        presentationRule.addViolation("test.com.seventytwomiles.dao");
        presentationRule.addViolation("test.com.seventytwomiles.dao.hibernate");

        configuration.addRule(presentationRule);

        try {

            assertTrue(doTests());
            fail("expected DependencyConstraintException");
        } catch (Exception e) {

            assertTrue(e instanceof DependencyConstraintException);
        }
    }


    /**
     * <p>This test has more than one rule, some that are valid and some that
     * are invalid. This test ensures that a failure is detected and
     * reported.</p>
     */
    public void testArchitecture_mixtureButFails() {

        final Configuration configuration = getConfiguration();

        configuration.addSource(new SourceDirectory("target\\test-classes", true));

        configuration.setDoCyclicDependencyTest(false);
        configuration.setThrowExceptionWhenNoPackages(true);

        final Rule daoRuleIsNotViolated = new Rule("dao");
        daoRuleIsNotViolated.setComment("dao may not access presentation.");
        daoRuleIsNotViolated.addPackage("test.com.seventytwomiles.dao.hibernate");
        daoRuleIsNotViolated.addViolation("test.com.seventytwomiles.web.spring");

        configuration.addRule(daoRuleIsNotViolated);

        final Rule presentationRuleIsViolated = new Rule("presentation");
        presentationRuleIsViolated.setComment("presentation may not access dao directly.");
        presentationRuleIsViolated.addPackage("test.com.seventytwomiles.web.spring");
        presentationRuleIsViolated.addViolation("test.com.seventytwomiles.dao");
        presentationRuleIsViolated.addViolation("test.com.seventytwomiles.dao.hibernate");

        configuration.addRule(presentationRuleIsViolated);

        try {

            assertTrue(doTests());
            fail("expected DependencyConstraintException");
        } catch (Exception e) {

            assertTrue(e instanceof DependencyConstraintException);

            final String message = e.getMessage();
            assertTrue(message.indexOf("test.com.seventytwomiles.web.spring") > -1);
            assertTrue(message.indexOf("test.com.seventytwomiles.dao.hibernate") > -1);
        }
    }


    /**
     * <p>Expect this test to pass.</p>
     */
    public void testArchitecture_pass() {

        final Configuration configuration = getConfiguration();

        configuration.addSource(new SourceDirectory("target\\test-classes", true));

        configuration.setDoCyclicDependencyTest(false);
        configuration.setThrowExceptionWhenNoPackages(true);

        final Rule daoRule = new Rule("dao");
        daoRule.setComment("dao may not access presentation.");
        daoRule.addPackage("test.com.seventytwomiles.dao.hibernate");
        daoRule.addViolation("test.com.seventytwomiles.web.spring");

        configuration.addRule(daoRule);

        assertTrue(doTests());
    }
}
