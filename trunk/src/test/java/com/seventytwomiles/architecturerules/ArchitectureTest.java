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
 * For more information visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;



/**
 * <p>Architecture test example.</p>
 *
 * @author mikenereson
 * @see AbstractArchitectureRulesConfigurationTest
 */
public class ArchitectureTest extends AbstractArchitectureRulesConfigurationTest {


    public ArchitectureTest() {
        final Configuration configuration = getConfiguration();

        /**
         * Get the configuration that already has the architecture-rules.xml
         * configuration loaded.
         */

        /**
         * Changing a boolean like configuration.setDoCyclicDependencyTest(false)
         * would override the value in the configuration file, because the
         * configuration file is loaded first.
         */

        final Rule rule = new Rule("services");
        rule.setComment("services may not depend on web layer.");
        rule.addPackage("com.company.app.core.services");
        rule.addViolation("com.company.app.web");
        rule.addViolation("com.company.app.web.spring");
        rule.addViolation("com.company.app.web.decorators");

        configuration.addRule(rule);
//        configuration.setDoCyclicDependencyTest(false);
    }


    /**
     * @see AbstractArchitectureRulesConfigurationTest
     */
    public String getConfigurationFileName() {
        /**
         * Provide the name of the rules configuration file. File file is
         * loaded from the classpath.
         */
        return "architecture-rules.xml";
    }


    /**
     * @see AbstractArchitectureRulesConfigurationTest#testArchitecture()
     */
    public void testArchitecture() {
        /**
         * Finally, run the test via doTest(). If any rules are broken, or if
         * the configuration can not be loaded properly, then the appropriate
         * Exception will be thrown.
         */
        try {
            assertTrue(doTests());
            fail("Cycles have not been detected");
        } catch (final CyclicRedundancyException e) {
            e.printStackTrace();

            final String message = e.getMessage();

            assertTrue(message.indexOf("test.com.seventytwomiles.services") > -1);
            assertTrue(message.indexOf("test.com.seventytwomiles.model") > -1);
            assertTrue(message.indexOf("test.com.seventytwomiles.dao.hibernate") > -1);
        }


        final Configuration configuration = getConfiguration();
        configuration.setDoCyclicDependencyTest(false);
        assertTrue(doTests());

        configuration.getSources().clear();
        configuration.getSources().add(new SourceDirectory("target\\classes", true));

        assertTrue(doTests());

        configuration.getSources().clear();
        configuration.getSources().add(new SourceDirectory("target\\test-classes", true));

        try {
            assertTrue(doTests());
        } catch (final CyclicRedundancyException e) {
            e.printStackTrace();

            final String message = e.getMessage();

            assertTrue(message.indexOf("test.com.seventytwomiles.services") > -1);
            assertTrue(message.indexOf("test.com.seventytwomiles.model") > -1);
            assertTrue(message.indexOf("test.com.seventytwomiles.dao.hibernate") > -1);
        }
    }
}
