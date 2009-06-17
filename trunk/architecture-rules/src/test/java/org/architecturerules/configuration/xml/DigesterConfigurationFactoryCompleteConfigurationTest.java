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


import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.architecturerules.configuration.AbstractConfigurationFactory;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * DigesterConfigurationFactory Tester.
 *
 * @author mikenereson
 */
public class DigesterConfigurationFactoryCompleteConfigurationTest extends TestCase {

    private static final String CUSTOM_XML_FILE = "some-custom-non-existent.xml";
    private final String completeConfiguration = "<?xml version=\"1.0\"?> <architecture> " + "<configuration> " + "<sources no-packages=\"exception\"> <source not-found=\"ignore\">parent-pom\\target\\classes</source> <source not-found=\"exception\">util\\target\\classes</source> <source not-found=\"ignore\">web\\target\\classes</source> <source not-found=\"ignore\">core\\target\\classes</source> </sources> " + "<cyclicalDependency test=\"false\"/> " + "<listeners> <include> <listener>org.architecturerules.listeners.ExampleEventListener</listener> <listener>org.architecturerules.listeners.LoggerEventListener</listener> </include> <exclude> <listener>org.architecturerules.listeners.LoggerEventListener</listener> </exclude> </listeners> " + "<properties> <property key=\"report.output.directory\" value=\"target/architecture\" /> <property key=\"report.output.format\" value=\"xml\" /> <property key=\"example.property\" value=\"example.value\" /> </properties> " + "</configuration> " + "<rules> " + "<rule id=\"dao\"> <comment>The dao interface package should rely on nothing.</comment> <packages> <package>com.seventytwomiles.pagerank.core.dao</package> <package>com.seventytwomiles.pagerank.core.dao.hibernate</package> </packages> <violations> <violation>com.seventytwomiles.pagerank.core.services</violation> <violation> com.seventytwomiles.pagerank.core.builder</violation> <violation> com.seventytwomiles.pagerank.util</violation> </violations> </rule> " + "<rule id=\"strategy\"> <comment>Strategies should be as pluggable as possible </comment> <packages> <package>com.seventytwomiles.pagerank.serviceproviders.startegies</package> </packages> <violations> <violation>com.seventytwomiles.pagerank.core.services </violation> <violation>com.seventytwomiles.pagerank.core.dao.hibernate</violation> </violations> </rule> " + "<rule id=\"model\"> <comment>Model should remain completely isolated </comment> <packages> <package>com.seventytwomiles.pagerank.core.model</package> </packages> <violations> <violation>com.seventytwomiles.pagerank.core.dao </violation> <violation>com.seventytwomiles.pagerank.core.dao.hibernate </violation> <violation>com.seventytwomiles.pagerank.core.services </violation> <violation>com.seventytwomiles.pagerank.core.strategy </violation> <violation>com.seventytwomiles.pagerank.core.builder </violation> <violation>com.seventytwomiles.pagerank.util </violation> </violations> </rule> " + "</rules> " + "</architecture>";
    private AbstractConfigurationFactory factory;

    public DigesterConfigurationFactoryCompleteConfigurationTest(final String name) {
        super(name);
    }

    @Override
    protected void setUp()
            throws Exception {

        super.setUp();
        factory = new DigesterConfigurationFactory(CUSTOM_XML_FILE) {

                    @Override
                    protected String getConfigurationContent(String configurationFileName) {

                        return configurationFileName.equals(CUSTOM_XML_FILE) ? completeConfiguration : super.getConfigurationContent(configurationFileName);
                    }
                };
    }


    public void testProcessConfiguration()
            throws Exception {

        /**
         * Test should perform cyclicalDependency
         * The default is true, so the configuration should make it false.
         */
        assertFalse(factory.doCyclicDependencyTest());

        /**
         * Test no-packages
         * The default is false, so the configuration should make it true.
         */
        assertTrue(factory.throwExceptionWhenNoPackages());
    }


    public void testRules() {

        final List<Rule> rules = new ArrayList<Rule>();
        final Rule[] array = factory.getRules().toArray(new Rule[0]);
        Arrays.sort(array, new Comparator<Rule>() {

                public int compare(Rule o1, Rule o2) {

                    return o1.getId().compareTo(o2.getId());
                }
            });
        rules.addAll(Arrays.asList(array));

        assertEquals(3, rules.size());

        /**
         * Validate values of the first Rule
         */
        final Rule rule0 = rules.get(1);

        /* id */
        assertEquals("model", rule0.getId());

        /* comment */
        assertEquals("Model should remain completely isolated", rule0.getComment());

        /* packages */
        assertEquals(1, rule0.getPackages().size());
        assertEquals("com.seventytwomiles.pagerank.core.model", rule0.getPackages().toArray()[0].toString());

        /* violations */
        assertEquals(6, rule0.getViolations().size());

        Object[] violations = rule0.getViolations().toArray();
        Arrays.sort(violations);
        assertEquals("com.seventytwomiles.pagerank.core.services", violations[3].toString());
        assertEquals("com.seventytwomiles.pagerank.core.builder", violations[0].toString());
        assertEquals("com.seventytwomiles.pagerank.core.dao", violations[1].toString());
        assertEquals("com.seventytwomiles.pagerank.core.strategy", violations[4].toString());
        assertEquals("com.seventytwomiles.pagerank.core.dao.hibernate", violations[2].toString());
        assertEquals("com.seventytwomiles.pagerank.util", violations[5].toString());

        /**
         * Nothing special about rule1, it is not tested.
         *
         * final Rule rule1 = (Rule) rules.get(1);
         */

        /**
         * Validate values of the second Rule
         */
        final Rule rule2 = rules.get(0);

        /* id */
        assertEquals("dao", rule2.getId());

        /* comment */
        assertEquals("The dao interface package should rely on nothing.", rule2.getComment());

        /* packages */
        assertEquals(2, rule2.getPackages().size());

        Object[] packages = rule2.getPackages().toArray();
        Arrays.sort(packages);

        assertEquals("com.seventytwomiles.pagerank.core.dao", packages[0].toString());
        assertEquals("com.seventytwomiles.pagerank.core.dao.hibernate", packages[1].toString());

        /* violations */
        assertEquals(3, rule2.getViolations().size());

        final Object[] array2 = rule2.getViolations().toArray();
        Arrays.sort(array2);

        assertEquals("com.seventytwomiles.pagerank.core.services", array2[1].toString());
        assertEquals("com.seventytwomiles.pagerank.core.builder", array2[0].toString());
        assertEquals("com.seventytwomiles.pagerank.util", array2[2].toString());
    }


    public void testProperties() {

        final Properties properties = new Properties();
        properties.putAll(factory.getProperties());

        assertEquals(3, properties.size());

        assertTrue(properties.containsKey("example.property"));
        assertEquals(properties.get("example.property").toString(), "example.value");

        assertTrue(properties.containsKey("report.output.format"));
        assertEquals(properties.get("report.output.format").toString(), "xml");

        assertTrue(properties.containsKey("report.output.directory"));
        assertEquals(properties.get("report.output.directory").toString(), "target/architecture");
    }


    public void testSources() {

        final List<SourceDirectory> sources = new ArrayList<SourceDirectory>();
        sources.addAll(factory.getSources());

        assertEquals(4, sources.size());

        final SourceDirectory source0 = sources.get(0);
        assertEquals("parent-pom" + File.separator + "target" + File.separator + "classes", source0.getPath());
        assertFalse(source0.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source1 = sources.get(1);
        assertEquals("util" + File.separator + "target" + File.separator + "classes", source1.getPath());
        assertTrue(source1.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source2 = sources.get(2);
        assertEquals("web" + File.separator + "target" + File.separator + "classes", source2.getPath());
        assertFalse(source2.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source3 = sources.get(3);
        assertEquals("core" + File.separator + "target" + File.separator + "classes", source3.getPath());
        assertFalse(source3.shouldThrowExceptionWhenNotFound());
    }
}
