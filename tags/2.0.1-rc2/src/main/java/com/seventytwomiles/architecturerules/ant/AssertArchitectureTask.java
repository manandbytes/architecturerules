package com.seventytwomiles.architecturerules.ant;

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


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.configuration.UnmodifiableConfiguration;
import com.seventytwomiles.architecturerules.configuration.xml.ConfigurationFactory;
import com.seventytwomiles.architecturerules.configuration.xml.DigesterConfigurationFactory;
import com.seventytwomiles.architecturerules.services.CyclicRedundencyService;
import com.seventytwomiles.architecturerules.services.CyclicRedundencyServiceImpl;
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
 * @author mikenereson
 */
public class AssertArchitectureTask extends Task {


    /**
     * <p>The name of the configuraiton file that is in the classpath that holds
     * the xml configuraiton. Recommend architecture-rules.xml</p>
     *
     * @parameter configurationFileName String
     */
    private String configurationFileName;


    /**
     * <p>Reference the configuraiton that is built by the ConfiguraitonFactory
     * that reads the configurationFile. This configuration may be
     * modified.</p>
     *
     * @parameter configuration Configuration
     */
    final private Configuration configuration = new Configuration();


    /**
     * @throws BuildException
     * @see Task#execute()
     */
    public void execute() throws BuildException {
        super.execute();

        if (null == configurationFileName || "".equals(configurationFileName))
            throw new IllegalStateException("set configurationFileName property");

        /**
         * 1. load configuration
         */
        final ConfigurationFactory configurationFactory = new DigesterConfigurationFactory(configurationFileName);

        configuration.getRules().addAll(configurationFactory.getRules());
        configuration.getSources().addAll(configurationFactory.getSources());
        configuration.setDoCyclicDependencyTest(configurationFactory.doCyclicDependencyTest());
        configuration.setThrowExceptionWhenNoPackages(configurationFactory.throwExceptionWhenNoPackages());

        /**
         * 2. assert configuration rules
         */
        final RulesService rulesService;
        rulesService = new RulesServiceImpl(new UnmodifiableConfiguration(configuration));
        rulesService.performRulesTest();

        /**
         * 3. check for cyclic dependency, if requested
         */
        if (configuration.shouldDoCyclicDependencyTest()) {

            final CyclicRedundencyService redundencyService = new CyclicRedundencyServiceImpl(new UnmodifiableConfiguration(configuration));
            redundencyService.performCyclicRedundencyCheck();
        }
    }


    /**
     * Setter for property 'configurationFileName'.
     *
     * @param configurationFileName Value to set for property
     * 'configurationFileName'.
     */
    public void setConfigurationFileName(final String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }
}
