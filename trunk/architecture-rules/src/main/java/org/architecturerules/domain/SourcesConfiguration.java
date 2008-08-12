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
 *         http://architecturerules.googlecode.com
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
public class SourcesConfiguration {

    /**
     * <p>To log with. See <tt>log4j.xml</tt>.</p>
     *
     * @parameter log Log
     */
    protected static final Log log = LogFactory.getLog(SourcesConfiguration.class);

    /**
     * <p></p>
     *
     * @parameter noPackages String
     * @see ConfigurationFactory#DEFAULT_NO_PACKAGES_CONFIGURATION_BOOLEAN_VALUE
     */
    private String noPackages = "ignore";

    /**
     * <p>Instantiate a new SourcesConfiguration</p>
     */
    public SourcesConfiguration() {

    }


    /**
     * <p>Instantiates a new SourcesConfiguration with the given <tt>noPackages</tt> value.</p>
     *
     * @param noPackages String
     */
    public SourcesConfiguration(final String noPackages) {

        this.noPackages = noPackages;
    }

    /**
     * Getter for property 'noPackages'.
     *
     * @return Value for property 'noPackages'.
     */
    public String getNoPackages() {

        return noPackages;
    }


    /**
     * Setter for property 'noPackages'.
     *
     * @param noPackages Value to set for property 'noPackages'.
     */
    public void setNoPackages(final String noPackages) {

        this.noPackages = noPackages;
    }
}
