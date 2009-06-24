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
package org.architecturerules.configuration.yaml;


import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.architecturerules.configuration.yaml.YamlConfigurationFactory;
import org.architecturerules.domain.JPackage;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


public class YamlConfigurationFactoryTest extends TestCase {

    private static final String EXT = "%EXT%";
    private static final String EXTENSION_SHOUD_BE_SUPPORTED = "'." + EXT + "' extension shoud be supported";
    private static final String YML_EXTENSION = "yml";
    private static final String YAML_EXTENSION = "yaml";
    private YamlConfigurationFactory factoryUnderTest;

    @Override
    protected void setUp()
            throws Exception {

        super.setUp();
        factoryUnderTest = new YamlConfigurationFactory("architecture-rules.yaml");
    }


    public void testYamlExtension() {

        assertTrue(EXTENSION_SHOUD_BE_SUPPORTED.replace(EXT, YAML_EXTENSION), factoryUnderTest.getSupportedFileTypes().contains(YAML_EXTENSION));
    }


    public void testYmlExtension() {

        assertTrue(EXTENSION_SHOUD_BE_SUPPORTED.replace(EXT, YML_EXTENSION), factoryUnderTest.getSupportedFileTypes().contains(YML_EXTENSION));
    }


    public void testSomeConfiguration() {

        assertFalse(factoryUnderTest.isDoCyclicDependencyTest());
        assertTrue(factoryUnderTest.throwExceptionWhenNoPackages());

        final Collection<Rule> rules = factoryUnderTest.getRules();
        assertEquals(1, rules.size());
    }


    public void testViolationsContent() {

        final Collection<Rule> rules = factoryUnderTest.getRules();
        final Rule firstRule = rules.iterator().next();
        final Collection<JPackage> violations = firstRule.getViolations();
        assertTrue(violations.containsAll(Arrays.asList(new JPackage[] {
                                                            new JPackage("w.s.x"),
                                                            new JPackage("e.d.c")
                                                        })));
    }


    public void testPackagesContent() {

        final Collection<Rule> rules = factoryUnderTest.getRules();
        final Rule firstRule = rules.iterator().next();
        final Collection<JPackage> packages = firstRule.getPackages();
        assertTrue(packages.containsAll(Arrays.asList(new JPackage[] {
                                                          new JPackage("a.b.c"),
                                                          new JPackage("d.e.f"),
                                                          new JPackage("q.a.z")
                                                      })));
    }


    public void testPackagesSize() {

        final Collection<Rule> rules = factoryUnderTest.getRules();
        final Rule firstRule = rules.iterator().next();
        final Collection<JPackage> packages = firstRule.getPackages();
        assertEquals(3, packages.size());
    }


    public void testViolationsSize() {

        final Collection<Rule> rules = factoryUnderTest.getRules();
        final Rule firstRule = rules.iterator().next();
        final Collection<JPackage> violations = firstRule.getViolations();
        assertEquals(2, violations.size());
    }


    public void testProperties() {

        final Properties properties = factoryUnderTest.getProperties();
        assertEquals(3, properties.size());
        assertTrue(properties.containsKey("prop.a.b.c"));
        assertTrue(properties.containsKey("prop.z.x.c"));
        assertTrue(properties.containsKey("prop.a.s"));
    }


    public void testSources() {

        final List<SourceDirectory> sources = factoryUnderTest.getSources();
        assertEquals(1, sources.size());
        assertTrue(sources.contains(new SourceDirectory("target/classes")));
    }


    public void testListeners() {

        assertEquals(1, factoryUnderTest.getIncludedListeners().size());
    }
}
