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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.architecturerules.api.listeners.Listener;
import org.architecturerules.domain.JPackage;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see EmptyListener
 * @see Listener
 */
public class LoggerListener extends EmptyListener {

    private static final Log log = LogFactory.getLog(LoggerListener.class);

    /**
     * @see Listener#onRuleAdded(Rule)
     */
    public void onRuleAdded(final Rule rule) {

        String debug = String.format("added rule '%s' to configuration", rule.getId());
        log.debug(debug);
    }


    /**
     * @see Listener#onRuleRemoved(Rule)
     */
    public void onRuleRemoved(final Rule rule) {

        String debug = String.format("removed rule '%s' from configuration", rule.getId());
        log.debug(debug);
    }


    /**
     * @see Listener#onSourceDirectoryAdded(SourceDirectory)
     */
    public void onSourceDirectoryAdded(final SourceDirectory sourceDirectory) {

        String debug = String.format("added SourceDirectory '%s' to configuration", sourceDirectory.getPath());
        log.debug(debug);
    }


    /**
     * @see Listener#onSourceDirectoryRemoved(SourceDirectory)
     */
    public void onSourceDirectoryRemoved(final SourceDirectory sourceDirectory) {

        String debug = String.format("removed SourceDirectory '%s' from configuration", sourceDirectory.getPath());
        log.debug(debug);
    }


    /**
     * @see Listener#onSourceDirectoryNotFound(SourceDirectory)
     */
    public void onSourceDirectoryNotFound(final SourceDirectory sourceDirectory) {

        String warn = String.format("SourceDirectory '%s' was not found", sourceDirectory.getPath());
        log.warn(warn);
    }


    /**
     * @see Listener#onSourceDirectoryEmpty(SourceDirectory)
     */
    public void onSourceDirectoryEmpty(final SourceDirectory sourceDirectory) {

        String warn = String.format("SourceDirectory '%s' is empty", sourceDirectory.getPath());
        log.warn(warn);
    }


    /**
     * @see Listener#onCyclicDependencyTestBegin()
     */
    public void onCyclicDependencyTestBegin() {

        String info = String.format("beginning Cyclic Dependency Test");
        log.info(info);
    }


    /**
     * @see Listener#onCyclicDependencyTestEnd()
     */
    public void onCyclicDependencyTestEnd() {

        String debug = String.format("ending Cyclic Dependency Test");
        log.debug(debug);
    }


    /**
     * @see Listener#onBeginTestingRules()
     */
    public void onBeginTestingRules() {

        String info = String.format("beginning Architecture Rules Test");
        log.info(info);
    }


    /**
     * @see Listener#onEndTestingRules()
     */
    public void onEndTestingRules() {

        String debug = String.format("ending Architecture Rules Test");
        log.debug(debug);
    }


    /**
     * @see Listener#onWildcardPatternMatched(JPackage , JPackage)
     */
    public void onWildcardPatternMatched(final JPackage wildcard, final JPackage jPackage) {

        String pattern = wildcard.getPath();
        String match = jPackage.getPath();

        String debug = String.format("matched wildcard pattern '%s' to package '%s'", pattern, match);
        log.debug(debug);
    }


    /**
     * @see Listener#onBeginRuleTest(Rule)
     */
    public void onBeginRuleTest(final Rule rule) {

        String debug = String.format("beginning test for Rule '%s'", rule.getId());
        log.debug(debug);
    }


    /**
     * @see Listener#onEndRuleTest(Rule)
     */
    public void onEndRuleTest(final Rule rule) {

        String debug = String.format("ending test for Rule '%s'", rule.getId());
        log.debug(debug);
    }


    /**
     * @see Listener#onPackageDependencyDiscovered(String, String)
     */
    public void onPackageDependencyDiscovered(final String packageName, final String dependencyPackageName) {

        String debug = String.format("'%s' depends on '%s'", packageName, dependencyPackageName);
        log.debug(debug);
    }


    /**
     * @see Listener#onPackageDependencyViolationDiscovered(Rule, String, String)
     */
    public void onPackageDependencyViolationDiscovered(final Rule rule, final String packageName, final String dependencyPackageName) {

        String ruleId = rule.getId();

        String warn = String.format("'%s' dependency on '%s' violates rule '%s'", packageName, dependencyPackageName, ruleId);

        log.warn(warn);
    }
}
