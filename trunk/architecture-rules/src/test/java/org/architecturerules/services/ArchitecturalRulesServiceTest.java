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
 *         http://72miles.com/ and
 *         http://architecturerules.googlecode.com
 */
package org.architecturerules.services;


import junit.framework.TestCase;

import org.architecturerules.api.services.RulesService;
import org.architecturerules.configuration.Configuration;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;
import org.architecturerules.exceptions.DependencyConstraintException;


/**
 * <p><code>RulesService</code> Tester.</p>
 *
 * @author mikenereson
 */
public class ArchitecturalRulesServiceTest extends TestCase {

    private RulesService rulesService;
    private Configuration configuration = new Configuration();
    private final SourceDirectory testClassesSourceDirectory = new SourceDirectory("target\\test-classes", true);
    private Rule goodModelRule;
    private Rule badControllerRule;

    public ArchitecturalRulesServiceTest(final String name) {
        super(name);
    }

    @Override
    public void setUp()
            throws Exception {

        configuration.addSource(testClassesSourceDirectory);

        badControllerRule = new Rule("controller", "test.com.seventytwomiles.web.spring").addViolation("test.com.seventytwomiles.dao");

        goodModelRule = new Rule("model", "test.com.seventytwomiles.model").addViolation("test.com.seventytwomiles.dao").addViolation("test.com.seventytwomiles.dao.hibernate");

        super.setUp();
    }


    @Override
    public void tearDown()
            throws Exception {

        rulesService = null;
        configuration = null;

        super.tearDown();
    }


    public void testPerformRulesTest() {

        /* setup good configuration */
        configuration.addRule(goodModelRule);

        rulesService = new RulesServiceImpl(configuration);

        assertTrue(rulesService.performRulesTest());
    }


    public void testPerformRulesTest_violations() {

        /* setup bad configuration */
        configuration.addRule(badControllerRule);

        rulesService = new RulesServiceImpl(configuration);

        try {

            assertTrue(rulesService.performRulesTest());
        } catch (final Exception e) {

            assertTrue(e instanceof DependencyConstraintException);

            assertEquals("rule controller failed: test.com.seventytwomiles.web.spring " + "is not allowed to depend on test.com.seventytwomiles.dao", e.getMessage());
        }
    }
}
