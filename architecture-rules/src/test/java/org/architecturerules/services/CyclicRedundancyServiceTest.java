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
package org.architecturerules.services;


import junit.framework.TestCase;

import java.util.Map;
import java.util.Set;

import org.architecturerules.api.services.CyclicRedundancyService;
import org.architecturerules.configuration.Configuration;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;
import org.architecturerules.exceptions.CyclicRedundancyException;


/**
 * <p><code>CyclicRedundancyService</code> Tester.</p>
 *
 * @author mikenereson
 */
public class CyclicRedundancyServiceTest extends TestCase {

    private CyclicRedundancyService cyclicRedundancyService;

    public CyclicRedundancyServiceTest(final String name) {
        super(name);
    }

    @Override
    public void setUp()
            throws Exception {

        super.setUp();

        Configuration configuration = new Configuration();
        configuration.addSource(new SourceDirectory("target\\test-classes", true));

        /**
         * Expect that ..web.spring depends on both ..dao and ..dao.hibernate
         */
        Rule controllerRule = new Rule();
        controllerRule.setId("controllers");
        controllerRule.addPackage("test.com.seventytwomiles.web.spring");
        controllerRule.addViolation("test.com.seventytwomiles.dao");
        controllerRule.addViolation("test.com.seventytwomiles.dao.hibernate");
        configuration.addRule(controllerRule);

        /**
         * Expect model to have cyclic dependency with services
         */
        Rule modelRule = new Rule();
        modelRule.setId("model");
        modelRule.addPackage("test.com.seventytwomiles.model");
        modelRule.addViolation("test.com.seventytwomiles.services");
        configuration.addRule(modelRule);
        configuration.setThrowExceptionWhenNoPackages(true);

        cyclicRedundancyService = new CyclicRedundancyServiceImpl(configuration);
    }


    public void testPerformCyclicRedundancyCheck()
            throws Exception {

        try {

            cyclicRedundancyService.performCyclicRedundancyCheck();
            fail("expected CyclicRedundancyException");
        } catch (final CyclicRedundancyException e) {

            final String message = e.getMessage().replaceAll("\r", "");

            /**
             * ..services depends on ..model and ..dao.hibernate.
             */
            assertTrue("expected violation at test.com.seventytwomiles.services", message.contains("test.com.seventytwomiles.services"));

            assertTrue("expected violation at test.com.seventytwomiles.model", message.contains("test.com.seventytwomiles.model"));

            assertTrue("expected violation at test.com.seventytwomiles.dao.hibernate", message.contains("test.com.seventytwomiles.dao.hibernate"));

            Map<String, Set<String>> cycles = e.getCycles();

            assertEquals(5, cycles.size());

            Set<String> servicesCycles = cycles.get("test.com.seventytwomiles.services");
            Set<String> modelCycles = cycles.get("test.com.seventytwomiles.model");
            Set<String> hibernateCycles = cycles.get("test.com.seventytwomiles.dao.hibernate");

            assertNotNull(servicesCycles);
            assertNotNull(modelCycles);
            assertNotNull(hibernateCycles);

            assertEquals(2, servicesCycles.size());
            assertEquals(1, modelCycles.size());
            assertEquals(1, hibernateCycles.size());
        }
    }
}
