/**
 * Copyright 2007 - 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://wiki.architecturerules.org/ and
 *         http://blog.architecturerules.org/
 */
package org.architecturerules.api.configuration;


import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * <p>Interface for <code>ConfigurationFactory</code> implementations to adhere to.</p>
 *
 * @author mikenereson
 */
public interface ConfigurationFactory {

    /**
     * <p>Holds the default value that should be used when no configuration is entered into the XML configuration file
     * for the cyclic dependency test.</p>
     *
     * @parameter DEFAULT_CYCLICAL_DEPENDENCY_CONFIGURATION_VALUE String
     * @deprecated see default-architecture-rules.xml
     */
    @Deprecated
    public static final String DEFAULT_CYCLICAL_DEPENDENCY_CONFIGURATION_VALUE = "true";

    /**
     * <p>Holds the default value that should be used when no configuration is entered into the XML configuration file
     * for the no packages attribute.</p>
     *
     * @parameter DEFAULT_NO_PACKAGES_CONFIGURATION_BOOLEAN_VALUE boolean
     */
    public static final boolean DEFAULT_NO_PACKAGES_CONFIGURATION_BOOLEAN_VALUE = false;

    /**
     * <p>The default name of the file containing the XML configuration.</p>
     *
     * @parameter DEFAULT_CONFIGURATION_FILE_NAME String
     */
    public static final String DEFAULT_CONFIGURATION_FILE_NAME = "architecture-rules.xml";

    /**
     * <p>The name of the XML file containing the default configuration to start with before loading the user's custom
     * configuration. Not to be confused with {@link #DEFAULT_CONFIGURATION_FILE_NAME} which is the name of the file
     * that the user should put their custom configuration in. {@link #DEFAULT_CONFIGURATION_FILE_NAME} is processed
     * after <tt>DEFAULT_CONFIGURATION_CONFIGURATION_FILE_NAME</tt>.</p>
     *
     * @parameter DEFAULT_CONFIGURATION_FILE_NAME String
     */
    public static final String DEFAULT_CONFIGURATION_CONFIGURATION_FILE_NAME = "default-architecture-rules.xml";

    /**
     * <p>Holds the value parsed from the XML configuration that indicates weather or not the cyclic dependency test
     * should be run.</p>
     *
     * @return boolean <tt>true</tt> when <samp>&lt;cyclicalDependency test="true"/> </samp>
     */
    boolean doCyclicDependencyTest();


    /**
     * <p>Getter for property {@link //rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    Collection<Rule> getRules();


    /**
     * <p>Getter for property {@link //sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    List<SourceDirectory> getSources();


    /**
     * <p>Getter for property {@link //includedListeners}.</p>
     *
     * @return Value for property <tt>includedListeners</tt>.
     */
    public Set<String> getIncludedListeners();


    /**
     * <p>Getter for property {@link //excludedListeners}.</p>
     *
     * @return Value for property <tt>excludedListeners</tt>.
     */
    public Set<String> getExcludedListeners();


    /**
     * <p>Holds the value parsed from the XML configuration that indicates weather or not a
     * <code>NoPackagesFoundException</code> should be thrown when no packages are found in any of the given source
     * paths.</p>
     *
     * @return boolean <tt>true</tt> when <samp>&lt;sources no-packages="exception"> </samp>
     */
    boolean throwExceptionWhenNoPackages();
}
