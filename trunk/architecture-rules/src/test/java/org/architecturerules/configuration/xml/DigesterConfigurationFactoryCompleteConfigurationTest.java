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
import java.util.List;
import java.util.Properties;

import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * DigesterConfigurationFactory Tester.
 *
 * @author mikenereson
 */
public class DigesterConfigurationFactoryCompleteConfigurationTest extends TestCase {

    private final String completeConfiguration = "<?xml version=\"1.0\"?> <architecture> " + "<configuration> " + "<sources no-packages=\"exception\"> <source not-found=\"ignore\">parent-pom\\target\\classes</source> <source not-found=\"exception\">util\\target\\classes</source> <source not-found=\"ignore\">web\\target\\classes</source> <source not-found=\"ignore\">core\\target\\classes</source> </sources> " + "<cyclicalDependency test=\"false\"/> " + "<listeners> <include> <listener>org.architecturerules.listeners.ExampleEventListener</listener> <listener>org.architecturerules.listeners.LoggerEventListener</listener> </include> <exclude> <listener>org.architecturerules.listeners.LoggerEventListener</listener> </exclude> </listeners> " + "<properties> <property key=\"report.output.directory\" value=\"target/architecture\" /> <property key=\"report.output.format\" value=\"xml\" /> <property key=\"example.property\" value=\"example.value\" /> </properties> " + "</configuration> " + "<rules> " + "<rule id=\"dao\"> <comment>The dao interface package should rely on nothing.</comment> <packages> <package>com.seventytwomiles.pagerank.core.dao</package> <package>com.seventytwomiles.pagerank.core.dao.hibernate</package> </packages> <violations> <violation>com.seventytwomiles.pagerank.core.services</violation> <violation> com.seventytwomiles.pagerank.core.builder</violation> <violation> com.seventytwomiles.pagerank.util</violation> </violations> </rule> " + "<rule id=\"strategy\"> <comment>Strategies should be as pluggable as possible </comment> <packages> <package>com.seventytwomiles.pagerank.serviceproviders.startegies</package> </packages> <violations> <violation>com.seventytwomiles.pagerank.core.services </violation> <violation>com.seventytwomiles.pagerank.core.dao.hibernate</violation> </violations> </rule> " + "<rule id=\"model\"> <comment>Model should remain completely isolated </comment> <packages> <package>com.seventytwomiles.pagerank.core.model</package> </packages> <violations> <violation>com.seventytwomiles.pagerank.core.dao </violation> <violation>com.seventytwomiles.pagerank.core.dao.hibernate </violation> <violation>com.seventytwomiles.pagerank.core.services </violation> <violation>com.seventytwomiles.pagerank.core.strategy </violation> <violation>com.seventytwomiles.pagerank.core.builder </violation> <violation>com.seventytwomiles.pagerank.util </violation> </violations> </rule> " + "</rules> " + "</architecture>";

    public DigesterConfigurationFactoryCompleteConfigurationTest(final String name) {
        super(name);
    }

    public void testProcessConfiguration()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();

        factory.processConfiguration(completeConfiguration);

        testSources(factory);
        testProperties(factory);
        testRules(factory);

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


    private void testRules(final DigesterConfigurationFactory factory) {

        final List rules = new ArrayList();
        rules.addAll(factory.getRules());

        assertEquals(3, rules.size());

        /**
         * Validate values of the first Rule
         */
        final Rule rule0 = (Rule) rules.get(0);

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
         * Nothing special about rule1, it is not tested.
         *
         * final Rule rule1 = (Rule) rules.get(1);
         */

        /**
         * Validate values of the second Rule
         */
        final Rule rule2 = (Rule) rules.get(2);

        /* id */
        assertEquals("dao", rule2.getId());

        /* comment */
        assertEquals("The dao interface package should rely on nothing.", rule2.getComment());

        /* packages */
        assertEquals(2, rule2.getPackages().size());
        assertEquals("com.seventytwomiles.pagerank.core.dao", rule2.getPackages().toArray()[0].toString());
        assertEquals("com.seventytwomiles.pagerank.core.dao.hibernate", rule2.getPackages().toArray()[1].toString());

        /* violations */
        assertEquals(3, rule2.getViolations().size());
        assertEquals("com.seventytwomiles.pagerank.core.services", rule2.getViolations().toArray()[0].toString());
        assertEquals("com.seventytwomiles.pagerank.core.builder", rule2.getViolations().toArray()[1].toString());
        assertEquals("com.seventytwomiles.pagerank.util", rule2.getViolations().toArray()[2].toString());
    }


    private void testProperties(final DigesterConfigurationFactory factory) {

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


    private void testSources(final DigesterConfigurationFactory factory) {

        final List sources = new ArrayList();
        sources.addAll(factory.getSources());

        assertEquals(4, sources.size());

        final SourceDirectory source0 = (SourceDirectory) sources.get(0);
        assertEquals("parent-pom" + File.separator + "target" + File.separator + "classes", source0.getPath());
        assertFalse(source0.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source1 = (SourceDirectory) sources.get(1);
        assertEquals("util" + File.separator + "target" + File.separator + "classes", source1.getPath());
        assertTrue(source1.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source2 = (SourceDirectory) sources.get(2);
        assertEquals("web" + File.separator + "target" + File.separator + "classes", source2.getPath());
        assertFalse(source2.shouldThrowExceptionWhenNotFound());

        final SourceDirectory source3 = (SourceDirectory) sources.get(3);
        assertEquals("core" + File.separator + "target" + File.separator + "classes", source3.getPath());
        assertFalse(source3.shouldThrowExceptionWhenNotFound());
    }
}
