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
package org.architecturerules.configuration;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;

import org.springframework.core.io.ClassPathResource;


/**
 * <p>Abstract Factory that provides common functionality for <code>ConfigurationFactory</code> implementations.</p>
 *
 * @author mikenereson
 * @see ConfigurationFactory
 */
public abstract class AbstractConfigurationFactory implements ConfigurationFactory {

    /**
     * <p>To log with. See <tt>log4j.xml</tt>.</p>
     *
     * @parameter log Log
     */
    protected static final Log log = LogFactory.getLog(AbstractConfigurationFactory.class);

    /**
     * <p>Set of Rules read from the configuration file</p>
     *
     * @parameter rules Set
     */
    protected final Collection<Rule> rules = new HashSet<Rule>();

    /**
     * <p>Set of  <code>Source</code> read from configuration file</p>
     *
     * @parameter sources Set
     */
    protected final List<SourceDirectory> sources = new ArrayList<SourceDirectory>();

    /**
     * <p>Weather or not to throw exception when no packages are found for a given path.</p>
     *
     * @parameter throwExceptionWhenNoPackages boolean
     */
    protected boolean throwExceptionWhenNoPackages = false;

    /**
     * <p>Weather or not to check for cyclic dependencies.</p>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    protected boolean doCyclicDependencyTest = true;

    /**
     * <p>Getter for property {@link #rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    public Collection<Rule> getRules() {

        return this.rules;
    }


    /**
     * <p>Getter for property {@link #sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    public List<SourceDirectory> getSources() {

        return this.sources;
    }


    /**
     * @return boolean <tt>true</tt> when <samp>&lt;cyclicalDependency test="true"/> </samp>
     * @see ConfigurationFactory#doCyclicDependencyTest()
     */
    public boolean doCyclicDependencyTest() {

        return doCyclicDependencyTest;
    }


    /**
     * @return boolean <tt>true</tt> when <samp>&lt;sources no-packages="exception"> </samp>
     * @see ConfigurationFactory#throwExceptionWhenNoPackages()
     */
    public boolean throwExceptionWhenNoPackages() {

        return throwExceptionWhenNoPackages;
    }


    /**
     * <p>Read Xml configuration file to String.</p>
     *
     * @param configurationFileName String name of the XML file in the classpath to load and read OR the complete path
     * to the file.
     * @return String returns the contents of the configurationFile
     */
    protected String getConfigurationAsXml(final String configurationFileName) {

        File file = new File(configurationFileName);

        if (!file.exists()) {

            /**
             * This code kinda sucks. First, an exception is thrown if the resource
             * does not exist, then an exception could be thrown if the resource
             * could not be read.
             */
            final ClassLoader classLoader = getClass().getClassLoader();

            final ClassPathResource resource = new ClassPathResource(configurationFileName, classLoader);

            if (!resource.exists()) {

                throw new IllegalArgumentException("could not load resource " + configurationFileName + " from classpath. File not found.");
            }

            try {

                file = resource.getFile();
            } catch (IOException e) {

                throw new IllegalArgumentException("could not locate resource " + configurationFileName + " from classpath. File not found.");
            }
        }

        final String xml;

        try {

            xml = FileUtils.readFileToString(file, null);
        } catch (final IOException e) {

            final String path = file.getAbsolutePath();

            throw new IllegalArgumentException("could not load configuration from " + path);
        }

        return xml;
    }


    /**
     * <p>Validate the configuration.</p>
     *
     * @param configuration String xml content to validate
     * @see "architecture-rules.dtd"
     */
    protected abstract void validateConfiguration(final String configuration);
}
