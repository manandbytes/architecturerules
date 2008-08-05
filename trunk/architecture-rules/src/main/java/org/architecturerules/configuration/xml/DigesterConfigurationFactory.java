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
package org.architecturerules.configuration.xml;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.architecturerules.configuration.AbstractConfigurationFactory;
import org.architecturerules.domain.CyclicDependencyConfiguration;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;
import org.architecturerules.domain.SourcesConfiguration;
import org.architecturerules.exceptions.InvalidConfigurationException;

import org.xml.sax.SAXException;


/**
 * <p>Apache Commons Digester implementation of the <code>ConfigurationFactory</code></p>
 *
 * @author mikenereson
 * @see AbstractConfigurationFactory
 */
public class DigesterConfigurationFactory extends AbstractConfigurationFactory {

    protected static final Log log = LogFactory.getLog(DigesterConfigurationFactory.class);

    /**
     * @todo remove this (it's useless)?
     */
    public DigesterConfigurationFactory() {

    }


    /**
     * <p>Instantiates a new <code>ConfigurationFactory</code> and processes the configuration found in the
     * <code>File</code> with the given <tt>configurationFileName</tt>.</p>
     *
     * @param fileName name of the <code>File</code> in the classpath to load configuration from.
     */
    public DigesterConfigurationFactory(final String fileName) {

        final String configurationXml = getConfigurationAsXml(fileName);

        validateConfiguration(configurationXml);
        processConfiguration(configurationXml);
    }

    /**
     * <p>Validate the configurationXml.</p>
     *
     * @param configurationXml String xml content to validate
     * @see "architecture-rules.dtd"
     */
    @Override
    protected void validateConfiguration(final String configurationXml) {

        final Digester digester = new Digester();
        digester.setValidating(false); // TODO: set to true to actually validate

        /**
         * TODO: apply DTD to configuration then try digester.parse
         */
        final StringReader configurationReader = new StringReader(configurationXml);

        try {

            digester.parse(configurationReader);
        } catch (final IOException e) {

            throw new InvalidConfigurationException("configuration xml file contains invalid configuration properties", e);
        } catch (final SAXException e) {

            throw new InvalidConfigurationException("configuration xml file contains invalid configuration properties", e);
        }
    }


    /**
     * <p>Read the configuration in the configuration l File to a <code>Configuration</code> entity.</p>
     *
     * <p>protected scope so that it could be individually tested.</p>
     *
     * @param configurationXml String of xml configuration
     */
    void processConfiguration(final String configurationXml) {

        try {

            processSources(configurationXml);
            processRules(configurationXml);
            processCyclicDependencyConfiguration(configurationXml);
            processSourcesNotFoundConfiguration(configurationXml);
        } catch (final IOException e) {

            /* Can this be handled better? Should it be? */
            throw new RuntimeException(e);
        } catch (final SAXException e) {

            /* Can this be handled better? Should it be? */
            throw new RuntimeException(e);
        }
    }


    /**
     * <p>Read xml configuration for source directories into SourceDirectory instances.</p>
     *
     * <p>package scope so that it could be individually tested</p>
     *
     * @param xml String xml to parse
     * @throws IOException when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     */
    void processSources(final String xml)
            throws IOException, SAXException {

        final Digester digester = getDigester();

        digester.addObjectCreate(XmlConfiguration.sources, ArrayList.class);

        digester.addObjectCreate(XmlConfiguration.source, SourceDirectory.class);

        digester.addCallMethod(XmlConfiguration.source, "setPath", 0);

        digester.addSetProperties(XmlConfiguration.source, "not-found", "notFound");

        digester.addSetNext(XmlConfiguration.source, "add");

        final StringReader reader = new StringReader(xml);

        final List<SourceDirectory> parsedSources = (ArrayList<SourceDirectory>) digester.parse(reader);

        sources.clear();

        for (final SourceDirectory sourceDirectory : parsedSources) {

            sources.add(sourceDirectory);
        }
    }


    /**
     * <p>Process XML configuration to read rules elements into <code>Rules</code></p>
     *
     * <p>package scope so that it could be individually tested</p>
     *
     * @param xml String xml to parse
     * @throws IOException when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     */
    void processRules(final String xml)
            throws IOException, SAXException {

        final Digester digester = getDigester();

        digester.addObjectCreate(XmlConfiguration.rules, ArrayList.class);
        digester.addObjectCreate(XmlConfiguration.rule, Rule.class);
        digester.addSetProperties(XmlConfiguration.rule, "id", "idString");
        digester.addCallMethod(XmlConfiguration.ruleComment, "setComment", 0);
        digester.addCallMethod(XmlConfiguration.rulePackage, "addPackage", 0);

        digester.addCallMethod(XmlConfiguration.ruleViolation, "addViolation", 0);

        digester.addSetNext(XmlConfiguration.rule, "add");

        final StringReader reader = new StringReader(xml);
        final List<Rule> parsedRules = (ArrayList<Rule>) digester.parse(reader);

        rules.clear();
        rules.addAll(parsedRules);
    }


    /**
     * <p>Process <tt>cyclicDependency</tt> element into <code>CyclicDependencyConfiguration</code> entity.</p>
     *
     * <p>protected scope so that it could be individually tested</p>
     *
     * @param configurationXml String xml to parse
     * @throws IOException when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     */
    void processCyclicDependencyConfiguration(final String configurationXml)
            throws IOException, SAXException {

        final Digester digester = getDigester();

        final StringReader configurationReader = new StringReader(configurationXml);

        digester.addObjectCreate(XmlConfiguration.cyclicalDependency, CyclicDependencyConfiguration.class);

        digester.addSetProperties(XmlConfiguration.cyclicalDependency, "test", "test");

        CyclicDependencyConfiguration configuration;
        configuration = (CyclicDependencyConfiguration) digester.parse(configurationReader);

        /**
         * If no configuration was provided in the xml, then use the default
         * values.
         */
        if (configuration == null) {

            configuration = new CyclicDependencyConfiguration();
        }

        final String test = configuration.getTest();

        if (test.equalsIgnoreCase("true") || test.equalsIgnoreCase("false")) {

            doCyclicDependencyTest = Boolean.valueOf(test);
        } else {

            throw new InvalidConfigurationException("'" + test + "' is not a valid value for " + "cyclicalDependency configuration. " + "Use <cyclicalDependency test=\"true\"/> " + "or <cyclicalDependency test=\"false\"/>");
        }
    }


    /**
     * <p>Process XML sources <tt>not-found</tt> attribute to a <code>SourcesConfiguration</code> entity.</p>
     *
     * <p>package scope so that it could be individually tested</p>
     *
     * @param configurationXml String xml to parse
     * @throws IOException when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     */
    void processSourcesNotFoundConfiguration(final String configurationXml)
            throws IOException, SAXException {

        final Digester digester = getDigester();

        final StringReader configurationReader = new StringReader(configurationXml);
        digester.addObjectCreate(XmlConfiguration.sources, SourcesConfiguration.class);
        digester.addSetProperties(XmlConfiguration.sources, "no-packages", "noPackages");

        SourcesConfiguration configuration = (SourcesConfiguration) digester.parse(configurationReader);

        /**
         * If no configuration was provided in the xml, then use the default
         * value.
         */
        if (configuration == null) {

            configuration = new SourcesConfiguration();
        }

        final String value = configuration.getNoPackages();

        final boolean isIgnore = value.equalsIgnoreCase("ignore");
        final boolean isException = value.equalsIgnoreCase("exception");

        if (isIgnore || isException) {

            throwExceptionWhenNoPackages = value.equalsIgnoreCase("exception");
        } else {

            throw new InvalidConfigurationException("'" + value + "' is not a valid value for the " + "sources no-packages configuration. " + "Use <sources no-packages=\"ignore\">, " + "<sources no-packages=\"exception\"> or " + "leave the property unset.");
        }
    }


    /**
     * <p>Configures a Digester</p>
     *
     * @return Digester
     */
    private Digester getDigester() {

        final SaxErrorHandler errorHandler = new SaxErrorHandler();

        final Digester digester = new Digester();
        digester.setErrorHandler(errorHandler);

        return digester;
    }
}
