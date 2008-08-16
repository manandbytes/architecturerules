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
 *         http://72miles.com/ and
 *         http://architecturerules.googlecode.com/
 */

package test.com.seventytwomiles.test;


import org.architecturerules.AbstractArchitectureRulesConfigurationTest;
import org.architecturerules.configuration.Configuration;


/**
 * <p>Sample subclass of AbstractArchitectureRulesConfigurationTest that is in a different package. Used to test
 * inheritance.</p>
 *
 * @author mikenereson
 * @see AbstractArchitectureRulesConfigurationTest
 */
public class ExtendTest extends AbstractArchitectureRulesConfigurationTest {

    /**
     * <p>Get the name of the xml configuration file that is located in the classpath.</p>
     *
     * <p>Recommend <samp>architecture-rules.xml</samp></p>
     *
     * @return String name of the xml file including <samp>.xml</smmp>
     */
    @Override
    protected String getConfigurationFileName() {

        return super.getConfigurationFileName();
    }


    /**
     * <p>Implement this method and call {@link #doTests}</p>
     */
    @Override
    public void testArchitecture() {

        final Configuration configuration = getConfiguration();
        configuration.setDoCyclicDependencyTest(false);
        configuration.setThrowExceptionWhenNoPackages(false);

        assertTrue(doTests());
    }
}
