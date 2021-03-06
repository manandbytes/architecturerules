/**
 * Copyright 2007 - 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://wiki.architecturerules.org/ and
 *         http://blog.architecturerules.org/
 */
package org.architecturerules;


import org.architecturerules.configuration.Configuration;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * <p>Architecture test example.</p>
 *
 * @author mikenereson
 * @see AbstractArchitectureRulesConfigurationTest
 */
public class SimpleProgrammaticArchitectureTest extends AbstractArchitectureRulesConfigurationTest {

    /**
     * Sets up the fixture, for example, open a network connection. This method is called before a test is executed.
     */
    @Override
    protected void setUp()
            throws Exception {

        super.setUp();

        /* get the configuration reference */
        final Configuration configuration = getConfiguration();

        /* add sources */
        configuration.addSource(new SourceDirectory("target\\test-classes", true));

        /* set options */
        configuration.setDoCyclicDependencyTest(false);
        configuration.setThrowExceptionWhenNoPackages(true);

        /* add Rules */
        final Rule daoRule = new Rule("dao");
        daoRule.setComment("dao may not access presentation.");
        daoRule.addPackage("test.com.seventytwomiles.dao.hibernate");
        daoRule.addViolation("test.com.seventytwomiles.web.spring");

        configuration.addRule(daoRule);
    }


    /**
     * @see AbstractArchitectureRulesConfigurationTest#testArchitecture()
     */
    @Override
    public void testArchitecture() {

        /**
         * Run the test via doTest(). If any rules are broken, or if
         * the configuration can not be loaded properly, then the appropriate
         * Exception will be thrown.
         */
        assertTrue(doTests());
    }
}
