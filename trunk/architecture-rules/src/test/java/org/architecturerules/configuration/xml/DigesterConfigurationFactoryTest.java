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
package org.architecturerules.configuration.xml;


import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;
import org.architecturerules.exceptions.InvalidConfigurationException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * DigesterConfigurationFactory Tester.
 *
 * @author mikenereson
 */
public class DigesterConfigurationFactoryTest extends AbstractDigesterTest {

    public DigesterConfigurationFactoryTest(final String name) {
        super(name);
    }

    public void testConstructor_invalidFilePath()
            throws Exception {

        try {

            new DigesterConfigurationFactory("this_file_does_not_exist.xml");
            fail("expected IllegalArgumentException");
        } catch (final Exception e) {

            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("could not load resource this_file_does_not_exist.xml " + "from classpath. File not found.", e.getMessage());
        }
    }


    public void testProcessCyclicDependencyConfiguration()
            throws Exception {

        /* assert starting state */
        DigesterConfigurationFactory factory = new DigesterConfigurationFactory();
        assertTrue(factory.doCyclicDependencyTest());

        factory = new DigesterConfigurationFactory();
        factory.processCyclicDependencyConfiguration(skipCyclicDependencyTestConfiguration);
        assertFalse(factory.doCyclicDependencyTest());

        factory.processCyclicDependencyConfiguration(doCyclicDependencyTestConfiguration);
        assertTrue(factory.doCyclicDependencyTest());

        factory = new DigesterConfigurationFactory();
        factory.processCyclicDependencyConfiguration(blankCyclicDependencyTestConfiguration);
        assertTrue(factory.doCyclicDependencyTest());
    }


    public void testProcessCyclicDependencyConfiguration_invalid()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();

        try {

            factory.processCyclicDependencyConfiguration(illegalCyclicDependencyTestConfiguration);

            fail("expected InvalidConfigurationException");
        } catch (final Exception e) {

            assertTrue(e instanceof InvalidConfigurationException);

            assertEquals("'INVALID' is not a valid value for cyclicalDependency " + "configuration. Use <cyclicalDependency test=\"true\"/> " + "or <cyclicalDependency test=\"false\"/>", e.getMessage());
        }
    }


    public void testProcessRules()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();

        factory.processRules(rulesXmlConfiguration);

        final List<Rule> rules = new ArrayList();
        rules.addAll(factory.getRules());

        assertEquals(2, rules.size());

        /**
         * Validate values of the first Rule
         */
        final Rule rule0 = rules.get(0);

        /* id */
        assertEquals("model", rule0.getId());

        /* comment */
        assertEquals("Model should remain completely isolated", rule0.getComment());

        /* packages */
        assertEquals(1, rule0.getPackages().size());

        assertEquals("com.seventytwomiles.pagerank.core.model", rule0.getPackages().toArray()[0].toString());

        /* violations */
        assertEquals(6, rule0.getViolations().size());

        assertEquals("com.seventytwomiles.pagerank.core.services", rule0.getViolations().toArray()[0].toString());

        assertEquals("com.seventytwomiles.pagerank.core.builder", rule0.getViolations().toArray()[1].toString());

        assertEquals("com.seventytwomiles.pagerank.core.dao", rule0.getViolations().toArray()[2].toString());

        assertEquals("com.seventytwomiles.pagerank.core.strategy", rule0.getViolations().toArray()[3].toString());

        assertEquals("com.seventytwomiles.pagerank.core.dao.hibernate", rule0.getViolations().toArray()[4].toString());
        assertEquals("com.seventytwomiles.pagerank.util", rule0.getViolations().toArray()[5].toString());

        /**
         * Validate values of the second Rule
         */
        final Rule rule1 = rules.get(1);

        /* id */
        assertEquals("dao", rule1.getId());

        /* comment */
        assertEquals("The dao interface package should rely on nothing.", rule1.getComment());

        /* packages */
        assertEquals(2, rule1.getPackages().size());

        assertEquals("com.seventytwomiles.pagerank.core.dao", rule1.getPackages().toArray()[0].toString());

        assertEquals("com.seventytwomiles.pagerank.core.dao.hibernate", rule1.getPackages().toArray()[1].toString());

        /* violations */
        assertEquals(3, rule1.getViolations().size());

        assertEquals("com.seventytwomiles.pagerank.core.services", rule1.getViolations().toArray()[0].toString());

        assertEquals("com.seventytwomiles.pagerank.core.builder", rule1.getViolations().toArray()[1].toString());

        assertEquals("com.seventytwomiles.pagerank.util", rule1.getViolations().toArray()[2].toString());
    }


    /**
     * <p>Test process sources</p>
     *
     * @throws Exception when anything goes wrong
     */
    public void testProcessSources()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();

        factory.processSources(sourcesXmlConfiguration);

        final List sources = new ArrayList();
        sources.addAll(factory.getSources());

        assertEquals(4, sources.size());

        final SourceDirectory source0 = (SourceDirectory) sources.get(0);

        assertEquals("core" + File.separator + "target" + File.separator + "classes", source0.getPath());

        assertFalse(source0.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source1 = (SourceDirectory) sources.get(1);

        assertEquals("util" + File.separator + "target" + File.separator + "classes", source1.getPath());

        assertTrue(source1.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source2 = (SourceDirectory) sources.get(2);

        assertEquals("parent-pom" + File.separator + "target" + File.separator + "classes", source2.getPath());

        assertFalse(source2.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source3 = (SourceDirectory) sources.get(3);

        assertEquals("web" + File.separator + "target" + File.separator + "classes", source3.getPath());

        assertFalse(source3.shouldThrowExceptionWhenNotFound());
    }


    public void testProcessSourcesNotFoundConfiguration()
            throws Exception {

        /* assert starting state */
        DigesterConfigurationFactory factory = new DigesterConfigurationFactory();

        assertFalse(factory.throwExceptionWhenNoPackages());

        factory = new DigesterConfigurationFactory();

        factory.processSourcesNotFoundConfiguration(noPackagesIgnoreConfiguration);

        assertFalse(factory.throwExceptionWhenNoPackages());

        factory = new DigesterConfigurationFactory();

        factory.processSourcesNotFoundConfiguration(noPackagesExceptionConfiguration);

        assertTrue(factory.throwExceptionWhenNoPackages());

        factory = new DigesterConfigurationFactory();

        factory.processSourcesNotFoundConfiguration(noPackagesBlankConfiguration);

        assertFalse(factory.throwExceptionWhenNoPackages());
    }


    public void testProcessSourcesNotFoundConfiguration_invalid()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();

        try {

            factory.processSourcesNotFoundConfiguration(noPackagesInvalidConfiguration);
            fail("expected InvalidConfigurationException");
        } catch (final Exception e) {

            assertTrue(e instanceof InvalidConfigurationException);
            assertEquals("'INVLALID' is not a valid value for the sources " + "no-packages configuration. Use <sources no-packages=\"ignore\">, " + "<sources no-packages=\"exception\"> or " + "leave the property unset.", e.getMessage());
        }
    }
}
