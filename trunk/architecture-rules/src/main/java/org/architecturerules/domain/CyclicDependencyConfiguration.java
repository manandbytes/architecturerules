/**
 * Copyright 2007, 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://wiki.architecturerules.org/ and
 *         http://blog.architecturerules.org
 */
package org.architecturerules.domain;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.architecturerules.api.configuration.ConfigurationFactory;


/**
 * <p>Represents the configuration information read from the XML configuration file.</p>
 *
 * @author mikenereson
 */
public class CyclicDependencyConfiguration {

    /**
     * <p>To log with. See <tt>log4j.xml</tt>.</p>
     *
     * @parameter log Log
     */
    protected static final Log log = LogFactory.getLog(CyclicDependencyConfiguration.class);

    /**
     * <p>Holds the value for the XML entry <tt>&lt;cyclicalDependency test="true"/&gt;</tt>.</p>
     *
     * <p>If the value is not provided in the configuration, the default value is used. {@link
     * ConfigurationFactory#DEFAULT_CYCLICAL_DEPENDENCY_CONFIGURATION_VALUE}</p>
     */
    private String test = ConfigurationFactory.DEFAULT_CYCLICAL_DEPENDENCY_CONFIGURATION_VALUE;

    /**
     * Getter for property 'test'.
     *
     * @return Value for property 'test'.
     */
    public String getTest() {

        return test;
    }


    /**
     * Setter for property 'test'.
     *
     * @param test Value to set for property 'test'.
     */
    public void setTest(final String test) {

        if ((test != null) && !test.equalsIgnoreCase("null")) {

            this.test = test;
        }
    }
}
