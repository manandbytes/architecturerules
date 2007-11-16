package test.com.seventytwomiles.test;


import com.seventytwomiles.architecturerules.AbstractArchitectureRulesConfigurationTest;

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


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see $interface
 */
public class ExtendTest extends AbstractArchitectureRulesConfigurationTest {


    /**
     * <p>Get the name of the xml configuration file that is located in the
     * classpath.</p>
     *
     * <p>Recommend <samp>architecture-rules.xml</samp></p>
     *
     * @return String name of the xml file including <samp>.xml</smmp>
     */
    public String getConfigurationFileName() {
        return super.getConfigurationFileName();    //To change body of overridden methods use File | Settings | File Templates.
    }


    /**
     * <p>Implement this method and call {@link #doTests}</p>
     */
    public void testArchitecture() {
        assertTrue(doTests());
    }
}
