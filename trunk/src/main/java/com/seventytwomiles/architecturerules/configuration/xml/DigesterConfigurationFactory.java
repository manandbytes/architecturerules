package com.seventytwomiles.architecturerules.configuration.xml;

/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more infomration visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


import com.seventytwomiles.architecturerules.domain.CyclicDependencyConfiguration;
import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import com.seventytwomiles.architecturerules.domain.SourcesConfiguration;
import com.seventytwomiles.architecturerules.exceptions.InvalidConfigurationException;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Apache Commons Digester implementatino of the <code>ConfigurationFactory</code></p>
 *
 * @author mikenereson
 * @see AbstractConfigurationFactory
 */
public class DigesterConfigurationFactory extends AbstractConfigurationFactory {


    private static final Log log = LogFactory.getLog(DigesterConfigurationFactory.class);


    public DigesterConfigurationFactory() {

    }


    /**
     * <p>Instanciates a new <code>ConfigurationFactory</code> and processes the
     * configuration found in the <code>File</code> with the given
     * <tt>configurationFileName</tt>.</p>
     *
     * @param configurationFileName name of the <code>File</code> in the
     * classpath to load configuration from.
     */
    public DigesterConfigurationFactory(final String configurationFileName) {

        final String configurationXml;

        configurationXml = getConfigurationAsXml(configurationFileName);

        validateConfigruation(configurationXml);
        processConfiguration(configurationXml);
    }


    /**
     * <p>Read the configuration in the configuration l File to a
     * <code>Configuration</code> entity.</p>
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
     * <p></p>
     *
     * <p>protected scope so that it could be individually tested</p>
     *
     * @param configurationXml String xml to parse
     * @throws IOException when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     */
    void processSourcesNotFoundConfiguration(final String configurationXml) throws IOException, SAXException {

        final Digester digester = getDigester();

        final StringReader configurationReader = new StringReader(configurationXml);

        digester.addObjectCreate(XmlConfiguration.sources, SourcesConfiguration.class);
        digester.addSetProperties(XmlConfiguration.sources, "no-packages", "noPackages");

        SourcesConfiguration configuration = (SourcesConfiguration) digester.parse(configurationReader);

        /**
         * If no configuration was provided in the xml, then use the default
         * vaule.
         */
        if (configuration == null)
            configuration = new SourcesConfiguration();

        final String value = configuration.getNoPackages();

        if (value.equalsIgnoreCase("ignore") || value.equalsIgnoreCase("exception")) {

            throwExceptionWhenNoPackages = value.equalsIgnoreCase("exception");

        } else {

            throw new InvalidConfigurationException("'" + value +
                                                    "' is not a valid value for the " +
                                                    "sources no-packages configuration. " +
                                                    "Use <sources no-packages=\"ignore\">, " +
                                                    "<sources no-packages=\"exception\"> or " +
                                                    "leave the property unset.");
        }
    }


    /**
     * <p></p>
     *
     * <p>protected scope so that it could be individually tested</p>
     *
     * @param configurationXml String xml to parse
     * @throws IOException when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     */
    void processCyclicDependencyConfiguration(final String configurationXml) throws IOException, SAXException {

        final Digester digester = getDigester();

        final StringReader configurationReader = new StringReader(configurationXml);

        digester.addObjectCreate(XmlConfiguration.cyclicalDependency, CyclicDependencyConfiguration.class);
        digester.addSetProperties(XmlConfiguration.cyclicalDependency, "test", "test");

        CyclicDependencyConfiguration configuration = (CyclicDependencyConfiguration) digester.parse(configurationReader);

        /**
         * If no configuration was provided in the xml, then use the default
         * vaules.
         */
        if (configuration == null)
            configuration = new CyclicDependencyConfiguration();

        final String test = configuration.getTest();

        if (test.equalsIgnoreCase("true") || test.equalsIgnoreCase("false")) {

            final Boolean doCyclicDependencyBoolean = Boolean.valueOf(test);
            doCyclicDependencyTest = doCyclicDependencyBoolean.booleanValue();

        } else {

            throw new InvalidConfigurationException("'" + test +
                                                    "' is not a valid value for " +
                                                    "cyclicalDependency configuration. " +
                                                    "Use <cyclicalDependency test=\"true\"/> " +
                                                    "or <cyclicalDependency test=\"false\"/>");
        }
    }


    /**
     * <p></p>
     *
     * <p>protected scope so that it could be individually tested</p>
     *
     * @param configurationXml String xml to parse
     * @throws IOException when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     */
    void processRules(final String configurationXml) throws IOException, SAXException {

        final Digester digester = getDigester();

        digester.addObjectCreate(XmlConfiguration.rules, ArrayList.class);
        digester.addObjectCreate(XmlConfiguration.rule, Rule.class);
        digester.addSetProperties(XmlConfiguration.rule, "id", "id");
        digester.addCallMethod(XmlConfiguration.ruleComment, "setComment", 0);
        digester.addCallMethod(XmlConfiguration.rulePackage, "addPackage", 0);
        digester.addCallMethod(XmlConfiguration.ruleViolation, "addViolation", 0);

        digester.addSetNext(XmlConfiguration.rule, "add");

        final StringReader configurationReader = new StringReader(configurationXml);
        List parsedRules = (ArrayList) digester.parse(configurationReader);

        rules.clear();
        rules.addAll(parsedRules);
    }


    /**
     * <p></p>
     *
     * <p>protected scope so that it could be individually tested</p>
     *
     * @param configurationXml String xml to parse
     * @throws IOException when an input/output error occurs
     * @throws SAXException when given xml can not be parsed
     */
    void processSources(final String configurationXml) throws IOException, SAXException {

        final Digester digester = getDigester();

        digester.addObjectCreate(XmlConfiguration.sources, ArrayList.class);

        digester.addObjectCreate(XmlConfiguration.source, SourceDirectory.class);
        digester.addCallMethod(XmlConfiguration.source, "setPath", 0);
        digester.addSetProperties(XmlConfiguration.source, "not-found", "notFound");

        digester.addSetNext(XmlConfiguration.source, "add");

        final StringReader configurationReader = new StringReader(configurationXml);
        final List parsedSources = (ArrayList) digester.parse(configurationReader);

        sources.clear();
        sources.addAll(parsedSources);
    }


    /**
     * <p>Validate the configurationXml.</p>
     *
     * @param configurationXml String xml content to validate
     * @see "architecture-rules.dtd"
     */
    void validateConfigruation(final String configurationXml) {


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
