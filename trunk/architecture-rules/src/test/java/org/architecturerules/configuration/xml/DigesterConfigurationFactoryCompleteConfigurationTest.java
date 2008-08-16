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
 *         http://architecturerules.googlecode.com/
 */

package org.architecturerules.configuration.xml;


import junit.framework.TestCase;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * DigesterConfigurationFactory Tester.
 *
 * @author mikenereson
 */
public class DigesterConfigurationFactoryCompleteConfigurationTest extends TestCase {

    private final String completeConfiguration = "<?xml version=\"1.0\"?>\n" + "\n" + "<!--<!DOCTYPE architecture SYSTEM\n" + "        \"http://architecturerules.googlecode.com/svn/trunk/src/main/resources/architecture-rules.dtd\">-->\n" + "\n" + "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"exception\">\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"exception\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "        </sources>\n" + "\n" + "        <cyclicalDependency test=\"false\"/>\n" + "<listeners>\n" + "            <include>\n" + "                <listener>org.architecturerules.listeners.ExampleListener</listener>\n" + "                <listener>org.architecturerules.listeners.LoggerListener</listener>\n" + "            </include>\n" + "            <exclude>\n" + "                <listener>org.architecturerules.listeners.LoggerListener</listener>\n" + "            </exclude>\n" + "        </listeners>" + "\n" + "    </configuration>\n" + "\n" + "\n" + "    <rules>\n" + "\n" + "        <rule id=\"dao\">\n" + "\n" + "            <comment>\n" + "                The dao interface package should rely on nothing.\n" + "            </comment>\n" + "\n" + "            <packages>\n" + "                <package>\n" + "                    com.seventytwomiles.pagerank.core.dao\n" + "                </package>\n" + "                <package>\n" + "                    com.seventytwomiles.pagerank.core.dao.hibernate\n" + "                </package>\n" + "            </packages>\n" + "\n" + "            <violations>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.services\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.builder\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.util\n" + "                </violation>\n" + "            </violations>\n" + "        </rule>\n" + "\n" + "        <rule id=\"strategy\">\n" + "\n" + "            <comment>\n" + "                Strategies should be as pluggable as possible\n" + "            </comment>\n" + "\n" + "            <packages>\n" + "                <package>\n" + "                    com.seventytwomiles.pagerank.serviceproviders.startegies\n" + "                </package>\n" + "            </packages>\n" + "\n" + "\n" + "            <violations>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.services\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.dao.hibernate\n" + "                </violation>\n" + "            </violations>\n" + "        </rule>\n" + "\n" + "        <rule id=\"model\">\n" + "\n" + "            <comment>\n" + "                Model should remain completely isolated\n" + "            </comment>\n" + "\n" + "            <packages>\n" + "                <package>\n" + "                    com.seventytwomiles.pagerank.core.model\n" + "                </package>\n" + "            </packages>\n" + "\n" + "            <violations>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.dao\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.dao.hibernate\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.services\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.strategy\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.builder\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.util\n" + "                </violation>\n" + "            </violations>\n" + "        </rule>\n" + "\n" + "    </rules>\n" + "\n" + "</architecture>\n" + "\n" + "";

    public DigesterConfigurationFactoryCompleteConfigurationTest(final String name) {
        super(name);
    }

    public void testProcessConfiguration()
            throws Exception {

        final DigesterConfigurationFactory factory = new DigesterConfigurationFactory();

        factory.processConfiguration(completeConfiguration);

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
}
