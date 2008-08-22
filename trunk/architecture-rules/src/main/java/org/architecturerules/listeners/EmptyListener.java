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
package org.architecturerules.listeners;


import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.architecturerules.api.listeners.Listener;
import org.architecturerules.domain.JPackage;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see Listener
 */
public class EmptyListener implements Listener {

    private Properties properties;

    /**
     * @param properties
     * @see Listener#registerListener
     */
    public void registerListener(final Properties properties) {

        Set<Map.Entry<Object, Object>> entries = properties.entrySet();

        for (Map.Entry<Object, Object> property : entries) {

            this.properties.put(property.getKey(), property.getValue());
        }
    }


    /**
     * @see Listener#terminateListener()
     */
    public void terminateListener() {

        // do nothing.
    }


    /**
     * @see Listener#onRuleAdded(Rule)
     */
    public void onRuleAdded(final Rule rule) {

        // do nothing.
    }


    /**
     * @see Listener#onRuleRemoved(Rule)
     */
    public void onRuleRemoved(final Rule rule) {

        // do nothing.
    }


    /**
     * @see Listener#onSourceDirectoryAdded(SourceDirectory)
     */
    public void onSourceDirectoryAdded(final SourceDirectory sourceDirectory) {

        // do nothing.
    }


    /**
     * @see Listener#onSourceDirectoryRemoved(SourceDirectory)
     */
    public void onSourceDirectoryRemoved(final SourceDirectory sourceDirectory) {

        // do nothing.
    }


    /**
     * @see Listener#onSourceDirectoryNotFound(SourceDirectory)
     */
    public void onSourceDirectoryNotFound(final SourceDirectory sourceDirectory) {

        // do nothing
    }


    /**
     * @see Listener#onSourceDirectoryEmpty(SourceDirectory)
     */
    public void onSourceDirectoryEmpty(final SourceDirectory sourceDirectory) {

        // do nothing
    }


    /**
     * @see Listener#onCyclicDependencyTestBegin()
     */
    public void onCyclicDependencyTestBegin() {

        // do nothing.
    }


    /**
     * @see Listener#onCyclicDependencyTestEnd()
     */
    public void onCyclicDependencyTestEnd() {

        // do nothing.
    }


    /**
     * @see Listener#onCyclicDependencyDiscovered(String, Collection, String, Collection)
     */
    public void onCyclicDependencyDiscovered(final String jPackage1, final Collection<String> package1DependenciesOnPackage2, final String jPackage2, final Collection<String> package2DependenciesOnPackage1) {

        // do nothing
    }


    /**
     * @see Listener#onBeginTestingRules()
     */
    public void onBeginTestingRules() {

        /* rules test */
    }


    /**
     * @see Listener#onEndTestingRules()
     */
    public void onEndTestingRules() {

        /* rules test */
    }


    /**
     * @see Listener#onWildcardPatternMatched(JPackage, JPackage)
     */
    public void onWildcardPatternMatched(final JPackage wildcard, final JPackage jPackage) {

        /* rules test */
    }


    /**
     * @see Listener#onBeginRuleTest(Rule)
     */
    public void onBeginRuleTest(final Rule rule) {

        /* rules test */
    }


    /**
     * @see Listener#onEndRuleTest(Rule)
     */
    public void onEndRuleTest(final Rule rule) {

        /* rules test */
    }


    /**
     * @see Listener#onPackageDependencyDiscovered(String, String)
     */
    public void onPackageDependencyDiscovered(final String packageName, final String dependency) {

        /* rules test */
    }


    /**
     * @see Listener#onPackageDependencyViolationDiscovered(Rule, String, String)
     */
    public void onPackageDependencyViolationDiscovered(final Rule rule, final String packageName, final String dependencyPackageName) {

        /* rules test */
    }


    /**
     * @see Listener#onSourceDirectoryLoaded(String, SourceDirectory)
     */
    public void onSourceDirectoryLoaded(final String path, final SourceDirectory sourceDirectory) {

        /* do nothing*/
    }


    public void onBeginPackageInvestigation(final JPackage javaPackage, final Rule ruleReference) {

        /* do nothing  */
    }


    protected Properties getProperties() {

        return this.properties;
    }
}
