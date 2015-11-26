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


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.architecturerules.configuration.AbstractConfigurationFactory;
import org.architecturerules.domain.CyclicDependencyConfiguration;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;
import org.architecturerules.domain.SourcesConfiguration;
import org.architecturerules.exceptions.InvalidConfigurationException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * <p>Apache Commons Digester implementation of the <code>ConfigurationFactory</code></p>
 *
 * @author mikenereson
 * @see AbstractConfigurationFactory
 */
public class DigesterConfigurationFactory extends AbstractConfigurationFactory {

    private final class DtdEntityResolver implements EntityResolver {

        private final HashMap<String, String> DTD_TO_PUBLICID_MAP = new HashMap<String, String>();

        {

            DTD_TO_PUBLICID_MAP.put("-//A-R//DTD Rules 3.0.0//EN", "/architecture-rules-3.0.0.dtd");
        }

        public InputSource resolveEntity(String publicId, String systemId)
                throws SAXException, IOException {

            final InputStream dtd = getClass().getResourceAsStream(DTD_TO_PUBLICID_MAP.get(publicId));
            InputSource inputSource = null;

            if (dtd != null) {

                inputSource = new InputSource(dtd);
                inputSource.setEncoding("UTF-8");
            }

            return inputSource;
        }
    }

    /**
     * <p>To log with. See <tt>log4j.xml</tt>.</p>
     *
     * @parameter log Log
     */
    protected static final Log log = LogFactory.getLog(DigesterConfigurationFactory.class);

    public DigesterConfigurationFactory() {

    }


    public DigesterConfigurationFactory(final String fileName) {
        super(fileName);
    }

    /**
     * <p>Validate the configurationXml.</p>
     *
     * @param configurationXml String xml content to validate
     * @see "architecture-rules.dtd"
     */
    @Override
    protected void validateConfiguration(final String configurationXml) {

        try {

            final Digester digester = getDigester();

            final StringReader configurationReader = new StringReader(configurationXml);

            digester.parse(configurationReader);
        } catch (final Exception e) {

            throw new InvalidConfigurationException("configuration xml file contains invalid configuration properties", e);
        }
    }


    /**
     * <p>Read the configuration in the configuration l File to a <code>Configuration</code> entity.</p>
     * <p/>
     * <p>protected scope so that it could be individually tested.</p>
     *
     * @param configurationXml String of xml configuration
     */
    @Override
    protected void processConfiguration(final String configurationXml) {

        try {

            processProperties(configurationXml);
            processListeners(configurationXml);
            processSources(configurationXml);
            processRules(configurationXml);
            processCyclicDependencyConfiguration(configurationXml);
            processSourcesNotFoundConfiguration(configurationXml);
        } catch (final Exception e) {

            /* Can this be handled better? Should it be? */
            throw new RuntimeException(e);
        }
    }


    void processProperties(final String xml)
            throws IOException, SAXException, ParserConfigurationException {

        final Digester digester = getDigester();
        digester.addObjectCreate(XmlConfiguration.properties, Properties.class);
        digester.addCallMethod(XmlConfiguration.property, "put", 2);
        digester.addCallParam(XmlConfiguration.property, 0, "key");
        digester.addCallParam(XmlConfiguration.property, 1, "value");

        final StringReader reader = new StringReader(xml.trim());

        final Object o;

        try {

            o = digester.parse(reader);
        } catch (SAXException e) {

            if (e.getException().toString().equals("java.lang.NullPointerException")) {

                throw new InvalidConfigurationException("Invalid XML configuration for <properties>. <property> must only contain both a key and a value attribute.", e);
            }

            // at this time, I don't even know how to get to this path, but I'm sure some user will figure it out : P
            throw new InvalidConfigurationException("Unable to parse XML configuration for <properties>: " + e.getMessage(), e);
        }

        if (o == null) {

            return;
        }

        final Properties properties = (Properties) o;

        addProperties(properties);
    }


    void processListeners(final String xml)
            throws IOException, SAXException, ParserConfigurationException {

        final Set<String> includedListenerClassNames = new HashSet<String>();
        final Set<String> excludeListenerClassNames = new HashSet<String>();

        includedListenerClassNames.addAll(getListenerClassNames(xml, XmlConfiguration.includedListeners));
        excludeListenerClassNames.addAll(getListenerClassNames(xml, XmlConfiguration.excludedListeners));

        getIncludedListeners().addAll(includedListenerClassNames);
        getExcludedListeners().addAll(excludeListenerClassNames);
    }


    private Set<String> getListenerClassNames(final String xml, final String path)
            throws IOException, SAXException, ParserConfigurationException {

        final Digester digester = getDigester();
        digester.addObjectCreate(XmlConfiguration.listeners, ArrayList.class);
        digester.addObjectCreate(path, StringBuffer.class); // TODO rather than StringBuffer can
        digester.addCallMethod(path, "append", 0); // TODO this be a String?
        digester.addSetRoot(path, "add");

        final Set<String> classNames = new HashSet<String>();

        final StringReader includeReader = new StringReader(xml);
        Object o = digester.parse(includeReader);

        if (o == null) {

            // return empty Set
            return classNames;
        }

        Collection<StringBuffer> classNamesAsStringBuffers = (Collection<StringBuffer>) o;

        /**
         * When the configuration contains no listener settings, return the empty Set
         */
        if (classNamesAsStringBuffers == null) {

            return classNames;
        }

        for (StringBuffer classNamesAsStringBuffer : classNamesAsStringBuffers) {

            classNames.add(classNamesAsStringBuffer.toString());
        }

        return classNames;
    }


    /**
     * <p>Read xml configuration for source directories into SourceDirectory instances.</p>
     * <p/>
     * <p>package scope so that it could be individually tested</p>
     *
     * @param xml String xml to parse
     * @throws IOException  when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     * @throws ParserConfigurationException
     */
    void processSources(final String xml)
            throws IOException, SAXException, ParserConfigurationException {

        final Digester digester = getDigester();

        digester.addObjectCreate(XmlConfiguration.sources, ArrayList.class);
        digester.addObjectCreate(XmlConfiguration.source, SourceDirectory.class);
        digester.addCallMethod(XmlConfiguration.source, "setPath", 0);
        digester.addSetProperties(XmlConfiguration.source, "not-found", "notFound");
        digester.addSetNext(XmlConfiguration.source, "add");

        final StringReader reader = new StringReader(xml);
        Object o = digester.parse(reader);

        if ((o != null) && o instanceof List) {

            final List<SourceDirectory> parsedSources = (ArrayList<SourceDirectory>) o;

            for (final SourceDirectory sourceDirectory : parsedSources) {

                getSources().add(sourceDirectory);
            }
        }
    }


    /**
     * <p>Process XML configuration to read rules elements into <code>Rules</code></p>
     * <p/>
     * <p>package scope so that it could be individually tested</p>
     *
     * @param xml String xml to parse
     * @throws IOException  when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     * @throws ParserConfigurationException
     */
    void processRules(final String xml)
            throws IOException, SAXException, ParserConfigurationException {

        final Digester digester = getDigester();

        digester.addObjectCreate(XmlConfiguration.rules, ArrayList.class);
        digester.addObjectCreate(XmlConfiguration.rule, Rule.class);
        digester.addSetProperties(XmlConfiguration.rule, "id", "idString");
        digester.addCallMethod(XmlConfiguration.ruleComment, "setComment", 0);
        digester.addCallMethod(XmlConfiguration.rulePackage, "addPackage", 0);
        digester.addCallMethod(XmlConfiguration.ruleViolation, "addViolation", 0);
        digester.addSetNext(XmlConfiguration.rule, "add");

        final StringReader reader = new StringReader(xml);

        Object o = digester.parse(reader);

        if (o != null) {

            final List<Rule> parsedRules = (ArrayList<Rule>) o;
            getRules().addAll(parsedRules);
        }
    }


    /**
     * <p>Process <tt>cyclicDependency</tt> element into <code>CyclicDependencyConfiguration</code> entity.</p>
     * <p/>
     * <p>protected scope so that it could be individually tested</p>
     *
     * @param configurationXml String xml to parse
     * @throws IOException  when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     * @throws ParserConfigurationException
     */
    void processCyclicDependencyConfiguration(final String configurationXml)
            throws IOException, SAXException, ParserConfigurationException {

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

            setDoCyclicDependencyTest(Boolean.valueOf(test));
        } else {

            throw new InvalidConfigurationException("'" + test + "' is not a valid value for " + "cyclicalDependency configuration. " + "Use <cyclicalDependency test=\"true\"/> " + "or <cyclicalDependency test=\"false\"/>");
        }
    }


    /**
     * <p>Process XML sources <tt>not-found</tt> attribute to a <code>SourcesConfiguration</code> entity.</p>
     * <p/>
     * <p>package scope so that it could be individually tested</p>
     *
     * @param configurationXml String xml to parse
     * @throws IOException  when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     * @throws ParserConfigurationException
     */
    void processSourcesNotFoundConfiguration(final String configurationXml)
            throws IOException, SAXException, ParserConfigurationException {

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

            boolean shouldThrowException = value.equalsIgnoreCase("exception");
            setThrowExceptionWhenNoPackages(shouldThrowException);
        } else {

            throw new InvalidConfigurationException("'" + value + "' is not a valid value for the " + "sources no-packages configuration. " + "Use <sources no-packages=\"ignore\">, " + "<sources no-packages=\"exception\"> or " + "leave the property unset.");
        }
    }


    /**
     * <p>Configures a Digester</p>
     *
     * @return Digester
     * @throws ParserConfigurationException
     */
    private Digester getDigester()
            throws ParserConfigurationException {

        final Digester digester = new Digester();
        enableValidation(digester);

        final SaxErrorHandler errorHandler = new SaxErrorHandler();
        digester.setErrorHandler(errorHandler);
        digester.setEntityResolver(new DtdEntityResolver());

        return digester;
    }


    private void enableValidation(final Digester digester)
            throws ParserConfigurationException {

        digester.setValidating(true);

        try {

            digester.setFeature("http://apache.org/xml/features/validation/dynamic", true);
        } catch (SAXException e) {

            log.warn("XML parser will validate the document even if a grammar is NOT specified");
            log.debug("Failed to setup on-demand XML validation", e);
        }
    }

    private static final Set<String> SUPPORTED_FILE_TYPES = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER) {

        {

            add("xml");
        }
    };

    @Override
    public Set<String> getSupportedFileTypes() {

        return SUPPORTED_FILE_TYPES;
    }
}
