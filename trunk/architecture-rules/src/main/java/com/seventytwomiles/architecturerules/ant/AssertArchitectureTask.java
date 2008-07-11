/**
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com/svn/docs/index.html
 */

package com.seventytwomiles.architecturerules.ant;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.configuration.ConfigurationFactory;
import com.seventytwomiles.architecturerules.configuration.UnmodifiableConfiguration;
import com.seventytwomiles.architecturerules.configuration.xml.DigesterConfigurationFactory;
import com.seventytwomiles.architecturerules.services.CyclicRedundancyService;
import com.seventytwomiles.architecturerules.services.CyclicRedundancyServiceImpl;
import com.seventytwomiles.architecturerules.services.RulesService;
import com.seventytwomiles.architecturerules.services.RulesServiceImpl;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;



/**
 * <p>Ant task to assert architecture.</p>
 *
 * <p>Usage looks like:</p>
 *
 * <pre>
 * &lt;taskdef name="assertArchitecture"
 *          classname="com.seventytwomiles.architecturerules.ant.AssertArchitectureTask">
 *    &lt;classpath refid="class.path"/>
 * &lt;/taskdef>
 *
 * &lt;target name="assert-architecture" depends="compile">
 *    &lt;assertArchitecture configurationFileName="architecture-rules-pass.xml"/>
 * &lt;/target>
 * </pre>
 *
 * <p>Requires the optional dependency</p>
 *
 * <pre>
 *  &lt;dependency>
 *      &lt;groupId>org.apache.ant&lt;/groupId>
 *      &lt;artifactId>ant&lt;/artifactId>
 *      &lt;version>1.7.0&lt;/version>
 *  &lt;/dependency>
 * </pre>
 *
 * @author mikenereson
 */
public class AssertArchitectureTask extends Task {


    /**
     * <p>The name of the configuration file that is in the classpath that holds
     * the xml configuration. Recommend <samp>architecture-rules.xml</samp></p>
     *
     * @parameter configurationFileName String
     */
    private String configurationFileName;

    /**
     * <p>Reference the configuration that is built by the
     * <code>ConfiguraitonFactory</code> that reads the configurationFile. This
     * configuration may be modified.</p>
     *
     * @parameter configuration Configuration
     */
    final private Configuration configuration = new Configuration();


    /**
     * Setter for property 'configurationFileName'.
     *
     * @param configurationFileName Value to set for property
     * 'configurationFileName'.
     */
    public void setConfigurationFileName(final String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }


    /**
     * @throws BuildException
     * @see Task#execute()
     */
    @Override
    public void execute() throws BuildException {

        super.execute();

        if (null == configurationFileName || "".equals(configurationFileName))
            throw new IllegalStateException(
                    "set configurationFileName property");

        /**
         * 1. load configuration
         */
        final ConfigurationFactory configurationFactory
                = new DigesterConfigurationFactory(configurationFileName);

        this.configuration.getRules().addAll(configurationFactory.getRules());

        this.configuration
                .getSources().addAll(configurationFactory.getSources());

        this.configuration.setDoCyclicDependencyTest(
                configurationFactory.doCyclicDependencyTest());

        this.configuration.setThrowExceptionWhenNoPackages(
                configurationFactory.throwExceptionWhenNoPackages());

        /**
         * 2. assert configuration rules
         */
        Configuration configuration
                = new UnmodifiableConfiguration(this.configuration);

        final RulesService rulesService
                = new RulesServiceImpl(configuration);

        rulesService.performRulesTest();

        /**
         * 3. check for cyclic dependency, if requested
         */
        if (this.configuration.shouldDoCyclicDependencyTest()) {

            configuration = new UnmodifiableConfiguration(this.configuration);

            final CyclicRedundancyService redundancyService
                    = new CyclicRedundancyServiceImpl(configuration);

            redundancyService.performCyclicRedundancyCheck();
        }
    }
}
