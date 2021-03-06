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
package org.architecturerules;


import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.architecturerules.api.configuration.ConfigurationFactory;
import org.architecturerules.api.services.CyclicRedundancyService;
import org.architecturerules.api.services.RulesService;
import org.architecturerules.configuration.Configuration;
import org.architecturerules.configuration.DefaultConfigurationFactory;
import org.architecturerules.configuration.UnmodifiableConfiguration;
import org.architecturerules.configuration.xml.DigesterConfigurationFactory;
import org.architecturerules.services.CyclicRedundancyServiceImpl;
import org.architecturerules.services.RulesServiceImpl;


/**
 * <p>Abstract class that the users of this library will extend in order to create a unit test that asserts
 * architecture.</p><p>Once extended, implement {@link #testArchitecture()} and call {@link #doTests()}. Also
 * override {@link #getConfigurationFileName()} if you want to load an XML configuration file.</p><p>If you want
 * to define the configuration programmatically in addition to the xml configuration, or want to solely use programmatic
 * configuration, you may call {@link #getConfiguration()} which will return <code>Configuration</code> that you may then
 * add new <code>Rule</code>, or <code>SourceDirectory</code> to.</p>
 *
 * @author mikenereson
 * @noinspection PointlessBooleanExpression
 */
public abstract class AbstractArchitectureRulesConfigurationTest extends TestCase {

    /**
     * <p>To log with. See <tt>log4j.xml</tt>.</p>
     *
     * @parameter log Log
     */
    protected static final Log log = LogFactory.getLog(AbstractArchitectureRulesConfigurationTest.class);

    /**
     * <p>The <code>Configuration</code> containing the <code>SourceDirectory</code> and <code>Rules</code> to
     * assert.</p>
     *
     * @parameter configuration Configuration
     */
    private final Configuration configuration = new Configuration();

    /**
     * Upon instantiation, instances a new Configuration by reading the XML configuration file named
     * architecture-rules.xml (by default) or by reading the XMl file name by the <code>getConfigurationFileName</code>
     * method.
     */
    protected AbstractArchitectureRulesConfigurationTest() {

        /* 1. load configuration if a configuration file name was provided */
        final String configurationFileName = getConfigurationFileName();

        ConfigurationFactory configurationFactory;

        if ((configurationFileName != null) && (configurationFileName.length() > 0)) {

            configurationFactory = DefaultConfigurationFactory.createInstance(configurationFileName);

            // TODO handle default in a some other way
            if (configurationFactory == null) {

                configurationFactory = new DigesterConfigurationFactory(configurationFileName);
            }

            Set<String> listeners = new HashSet<String>();
            listeners.addAll(configurationFactory.getIncludedListeners());
            listeners.removeAll(configurationFactory.getExcludedListeners());

            for (String listenerClassName : listeners) {

                configuration.addListener(listenerClassName);
            }

            final Properties properties = configuration.getProperties();
            configuration.registerListener(properties);

            /**
             * Ensure listeners are added before Rules and Sources so that those events can be listened.
             */
            configuration.getRules().addAll(configurationFactory.getRules());
            configuration.getSources().addAll(configurationFactory.getSources());
            configuration.setDoCyclicDependencyTest(configurationFactory.doCyclicDependencyTest());
        }
    }

    /* ----------------------------------------------------- abstract methods */
    /**
     * <p>Get the name of the xml configuration file that is located in the classpath.</p> <p/> <p>Recommend value, and
     * the default value, is <samp>architecture-rules.xml</samp></p>
     *
     * @return String name of the xml file including <samp>.xml</samp>
     * @see ConfigurationFactory#DEFAULT_CONFIGURATION_FILE_NAME
     */
    protected String getConfigurationFileName() {

        return ConfigurationFactory.DEFAULT_CONFIGURATION_FILE_NAME;
    }


    /**
     * Getter for property {@link #configuration}.
     *
     * @return Value for property <tt>configuration</tt>.
     */
    protected Configuration getConfiguration() {

        return configuration;
    }


    /**
     * <p>Runs tests that are configured either programmatically, or by the XML configuration file that is loaded.</p>
     *
     * @return boolean <tt>true</tt> when the tests all pass.
     */
    protected final boolean doTests() {

        final RulesService rulesService = new RulesServiceImpl(new UnmodifiableConfiguration(configuration));

        final boolean rulesResults = rulesService.performRulesTest();

        if (configuration.shouldDoCyclicDependencyTest()) {

            final UnmodifiableConfiguration unmodifiableConfiguration = new UnmodifiableConfiguration(configuration);

            final CyclicRedundancyService redundancyService = new CyclicRedundancyServiceImpl(unmodifiableConfiguration);

            redundancyService.performCyclicRedundancyCheck();
        }

        configuration.terminateListener();

        return rulesResults;
    }


    /**
     * <p>Implement this method and call {@link #doTests}</p>
     */
    public abstract void testArchitecture();
}
