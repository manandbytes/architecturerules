package com.seventytwomiles.architecturerules.domain;

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


import com.seventytwomiles.architecturerules.configuration.ConfigurationFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * <p>Represents the configuration information read from the XML configuration
 * file.</p>
 *
 * @author mikenereson
 */
public class SourcesConfiguration {


    private static final Log log
            = LogFactory.getLog(SourcesConfiguration.class);

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
     * <p>Instantiates a new SourcesConfiguration with the given
     * <tt>noPackages</tt> value.</p>
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
