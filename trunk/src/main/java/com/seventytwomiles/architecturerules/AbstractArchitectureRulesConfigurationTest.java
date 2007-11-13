package com.seventytwomiles.architecturerules;

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
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Abstract class that the users of this library will extend in order to
 * create a unit test that asserts architecture. </p>
 *
 * <p>Once extended, implement <tt>testArchitecture</tt> and call
 * <tt>doTest()</tt>. Also override <tt>getConfigurationFileName()</tt> if you
 * want to load an XML configuration file.</p>
 *
 * <p>If you want to define the configuration programatically in addition to the
 * xml configuration, or want to solely use programatic configuraiton, you may
 * call <tt>getConfiguration</tt> which will return <code>Configuration</code>
 * that you may then add new <code>Rule</code>, or <code>SourceDirectory</code>
 * to. </p>
 *
 * @author mikenereson
 * @noinspection PointlessBooleanExpression
 */
public abstract class AbstractArchitectureRulesConfigurationTest extends TestCase {


    private static final Log log = LogFactory.getLog(AbstractArchitectureRulesConfigurationTest.class);

    /* -------------------------------------------------- fields and mutators */

    /**
     * <p></p>
     *
     * @parameter configuration Configuration
     */
    final private Configuration configuration = new Configuration();


    /**
     * Getter for property {@link #configuration}.
     *
     * @return Value for property <tt>configuration</tt>.
     */
    Configuration getConfiguration() {

        return configuration;
    }

    /* --------------------------------------------------------- constructors */


    protected AbstractArchitectureRulesConfigurationTest() {

        /* 1. load configuration if a configuration file name was provided */

        final String configurationFileName = getConfigurationFileName();

        final ConfigurationFactory configurationFactory;

        if (configurationFileName != null && configurationFileName.length() > 0) {

            configurationFactory = new DigesterConfigurationFactory(configurationFileName);

            configuration.getRules().addAll(configurationFactory.getRules());
            configuration.getSources().addAll(configurationFactory.getSources());
        }
    }


    boolean doTests() {

        final RulesService rulesService;
        rulesService = new RulesServiceImpl(new UnmodifiableConfiguration(configuration));

        final boolean rulesResults = rulesService.performRulesTest();

        if (configuration.shouldDoCyclicDependencyTest()) {

            final CyclicRedundencyService redundencyService = new CyclicRedundencyServiceImpl(new UnmodifiableConfiguration(configuration));
            redundencyService.performCyclicRedundencyCheck();
        }

        return rulesResults;
    }

    /* ----------------------------------------------------- abstract methods */


    /**
     * <p>Get the name of the xml configuration file that is located in the
     * classpath.</p>
     *
     * <p>Recommend <samp>architecture-rules.xml</samp></p>
     *
     * @return String name of the xml file including <samp>.xml</smmp>
     */
    String getConfigurationFileName() {
        return "";
    }


    /**
     * <p>Implement this method and call {@link #doTests}</p>
     */
    abstract void testArchitecture();

}
