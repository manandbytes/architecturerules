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
package com.seventytwomiles.architecturerules.domain;


import com.seventytwomiles.architecturerules.configuration.ConfigurationFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Represents the configuration information read from the XML configuration
 * file.</p>
 *
 * @author mikenereson
 */
public class CyclicDependencyConfiguration
{
    protected static final Log log = LogFactory.getLog( CyclicDependencyConfiguration.class );

    /**
     * <p>Holds the value for the XML entry <tt>&lt;cyclicalDependency
     * test="true"/&gt;</tt>.</p>
     *
     * <p>If the value is not provided in the configuration, the default value
     * is used. {@link ConfigurationFactory#DEFAULT_CYCLICAL_DEPENDENCY_CONFIGURATION_VALUE}</p>
     */
    private String test = ConfigurationFactory.DEFAULT_CYCLICAL_DEPENDENCY_CONFIGURATION_VALUE;

    /**
     * Getter for property 'test'.
     *
     * @return Value for property 'test'.
     */
    public String getTest(  )
    {
        return test;
    }

    /**
     * Setter for property 'test'.
     *
     * @param test Value to set for property 'test'.
     */
    public void setTest( final String test )
    {
        if ( ( test != null ) && ! test.equalsIgnoreCase( "null" ) )
        {
            this.test = test;
        }
    }
}
