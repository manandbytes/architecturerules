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
package org.architecturerules.configuration.xml;


import java.io.File;
import java.util.*;

import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;
import org.architecturerules.exceptions.InvalidConfigurationException;
import org.architecturerules.listeners.ExampleListener;
import org.architecturerules.listeners.LoggerListener;


/**
 * DigesterConfigurationFactory Tester.
 *
 * @author mikenereson
 */
public class DigesterConfigurationFactoryTest extends AbstractDigesterTest {

    private static final String THIS_FILE_DOES_NOT_EXIST_XML = "this_file_does_not_exist.xml";

    public DigesterConfigurationFactoryTest(final String name) {
        super(name);
    }

    public void testConstructor_invalidFilePath()
            throws Exception {

        try {

            new DigesterConfigurationFactory(THIS_FILE_DOES_NOT_EXIST_XML);
            fail("expected IllegalArgumentException");
        } catch (final Exception e) {

            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("could not load resource " + THIS_FILE_DOES_NOT_EXIST_XML + " from classpath. File not found.", e.getMessage());
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

        final List<Rule> rules = new ArrayList<Rule>();
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

        Object[] violations = rule0.getViolations().toArray();
        Arrays.sort(violations);

        assertEquals(6, rule0.getViolations().size());

        assertEquals("com.seventytwomiles.pagerank.core.services", violations[3].toString());

        assertEquals("com.seventytwomiles.pagerank.core.builder", violations[0].toString());

        assertEquals("com.seventytwomiles.pagerank.core.dao", violations[1].toString());

        assertEquals("com.seventytwomiles.pagerank.core.strategy", violations[4].toString());

        assertEquals("com.seventytwomiles.pagerank.core.dao.hibernate", violations[2].toString());
        assertEquals("com.seventytwomiles.pagerank.util", violations[5].toString());

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

        Object[] packagesToTest = rule1.getPackages().toArray();
        Arrays.sort(packagesToTest);

        assertEquals("com.seventytwomiles.pagerank.core.dao", packagesToTest[0].toString());

        assertEquals("com.seventytwomiles.pagerank.core.dao.hibernate", packagesToTest[1].toString());

        /* violations */
        assertEquals(3, rule1.getViolations().size());

        violations = rule1.getViolations().toArray();
        Arrays.sort(violations);

        assertEquals("com.seventytwomiles.pagerank.core.services", violations[1].toString());

        assertEquals("com.seventytwomiles.pagerank.core.builder", violations[0].toString());

        assertEquals("com.seventytwomiles.pagerank.util", violations[2].toString());
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

        final List<SourceDirectory> sources = new ArrayList<SourceDirectory>();
        sources.addAll(factory.getSources());

        assertEquals(4, sources.size());

        final SourceDirectory source0 = sources.get(0);

        assertEquals("core" + File.separator + "target" + File.separator + "classes", source0.getPath());
        assertFalse(source0.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source1 = sources.get(1);

        assertEquals("util" + File.separator + "target" + File.separator + "classes", source1.getPath());
        assertTrue(source1.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source2 = sources.get(2);

        assertEquals("parent-pom" + File.separator + "target" + File.separator + "classes", source2.getPath());
        assertFalse(source2.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source3 = sources.get(3);

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


    public void testListeners_included()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();
        Set<String> includedListeners = new HashSet<String>();
        Set<String> excludedListeners = new HashSet<String>();

        factory.processListeners(withIncludeListenersConfiguration);
        includedListeners.addAll(factory.getIncludedListeners());
        excludedListeners.addAll(factory.getExcludedListeners());

        assertTrue(includedListeners.contains(ExampleListener.class.getName()));
        assertTrue(excludedListeners.isEmpty());
    }


    public void testListeners_excluded()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();
        Set<String> includedListeners = new HashSet<String>();
        Set<String> excludedListeners = new HashSet<String>();

        factory.processListeners(withExcludeListenersConfiguration);
        includedListeners.addAll(factory.getIncludedListeners());
        excludedListeners.addAll(factory.getExcludedListeners());

        assertTrue(excludedListeners.contains(LoggerListener.class.getName()));
    }


    public void testListeners_undefined()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory() {

            /**
             * provides no default configuration
             */
            @Override
            protected void loadDefaultConfiguration() {

            }
        };

        Set<String> includedListeners = new HashSet<String>();
        Set<String> excludedListeners = new HashSet<String>();

        factory.processListeners(withNoListenersConfiguration);
        includedListeners.addAll(factory.getIncludedListeners());
        excludedListeners.addAll(factory.getExcludedListeners());

        assertTrue("there are included listeners", includedListeners.isEmpty());
        assertTrue("there are excluded listeners", excludedListeners.isEmpty());
    }


    public void testProperties()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();
        factory.processProperties(withProperties);

        final Properties properties = factory.getProperties();

        assertEquals(2, properties.size());
        assertTrue(properties.containsKey("report.output.directory"));
        assertEquals(properties.getProperty("report.output.directory"), "target/architecture");

        assertTrue(properties.containsKey("report.output.format"));
        assertEquals(properties.getProperty("report.output.format"), "xml");
    }


    public void testProperties_wrongAttributes()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();

        try {

            factory.processProperties(withPropertiesWrongAttributes);
            fail("expected InvalidConfigurationException");
        } catch (InvalidConfigurationException e) {

            // expected
            assertTrue(e.getMessage().contains("a key and a value attribute"));
        }
    }


    public void testProperties_missingAttributes()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();

        try {

            factory.processProperties(withPropertiesMissingAttributes);
            fail("expected InvalidConfigurationException");
        } catch (InvalidConfigurationException e) {

            // expected
            assertTrue(e.getMessage().contains("a key and a value attribute"));
        }
    }


    public void testProperties_nonePresent()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory() {

            /**
             * provides no default configuration
             */
            @Override
            protected void loadDefaultConfiguration() {

            }
        };

        factory.processProperties(rulesXmlConfiguration);

        final Properties properties = factory.getProperties();

        assertTrue(properties.isEmpty());
    }
}
