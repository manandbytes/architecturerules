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
public abstract class EmptyListener implements Listener {

    /**
     * @see Listener#register
     */
    public void register() {

        // do nothing.
    }


    /**
     * @see Listener#terminate()
     */
    public void terminate() {

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
     * @see Listener#onCyclicDependencyDiscovered(JPackage, JPackage)
     */
    public void onCyclicDependencyDiscovered(final JPackage jPackage1, final JPackage jPackage2) {

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
}
