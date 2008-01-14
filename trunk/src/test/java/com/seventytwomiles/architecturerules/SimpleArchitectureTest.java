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
/**
 * <p>Architecture test example.</p>
 *
 * @author mikenereson
 * @see AbstractArchitectureRulesConfigurationTest
 */
public class SimpleArchitectureTest
        extends AbstractArchitectureRulesConfigurationTest {


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
         * Run the test via doTest(). If any rules are broken, or if
         * the configuration can not be loaded properly, then the appropriate
         * Exception will be thrown.
         */
        assertTrue(doTests());
    }
}
