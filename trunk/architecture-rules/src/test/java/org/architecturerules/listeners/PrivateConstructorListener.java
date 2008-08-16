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
import org.architecturerules.configuration.Configuration;
import org.architecturerules.domain.JPackage;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * /** <p>To test {@link Configuration#addListener(String)} <code>IllegalAccessException</code> handling</p>
 *
 * @author mnereson
 * @see Listener
 */
public class PrivateConstructorListener implements Listener {

    private PrivateConstructorListener() {

    }

    public void register() {

        // do nothing
    }


    public void terminate() {

        // do nothing
    }


    public void onRuleAdded(final Rule rule) {

        // do nothing
    }


    public void onRuleRemoved(final Rule rule) {

        // do nothing
    }


    public void onSourceDirectoryAdded(final SourceDirectory sourceDirectory) {

        // do nothing
    }


    public void onSourceDirectoryRemoved(final SourceDirectory sourceDirectory) {

        // do nothing
    }


    public void onSourceDirectoryNotFound(final SourceDirectory sourceDirectory) {

        // do nothing
    }


    public void onSourceDirectoryEmpty(final SourceDirectory sourceDirectory) {

        // do nothing
    }


    public void onCyclicDependencyTestBegin() {

        // do nothing
    }


    public void onCyclicDependencyTestEnd() {

        // do nothing
    }


    public void onCyclicDependencyDiscovered(final JPackage jPackage1, final JPackage jPackage2) {

        // do nothing
    }


    public void onBeginTestingRules() {

        // do nothing
    }


    public void onEndTestingRules() {

        // do nothing
    }


    public void onWildcardPatternMatched(final JPackage wildcard, final JPackage jPackage) {

        // do nothing
    }


    public void onBeginRuleTest(final Rule rule) {

        // do nothing
    }


    public void onEndRuleTest(final Rule rule) {

        // do nothing
    }


    public void onPackageDependencyDiscovered(final String packageName, final String dependencyPackageName) {

        // do nothing
    }


    public void onPackageDependencyViolationDiscovered(final Rule rule, final String packageName, final String dependencyPackageName) {

        // do nothing
    }
}
