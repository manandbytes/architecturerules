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
 *         http://72miles.com/ and
 *         http://architecturerules.googlecode.com/
 */
package org.architecturerules.listeners;


import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
public class LoggerListener implements Listener {

    private static final Log log = LogFactory.getLog(LoggerListener.class);
    private Properties properties;

    public void registerListener(final Properties properties) {

        String debug = String.format("registered LoggerListener");
        log.debug(debug);

        Set<Map.Entry<Object, Object>> entries = properties.entrySet();

        for (Map.Entry<Object, Object> property : entries) {

            this.properties.put(property.getKey(), property.getValue());
        }

        entries = properties.entrySet();

        log.debug("Properties: ");

        for (Map.Entry<Object, Object> property : entries) {

            log.debug("   " + property.getKey() + " => " + property.getValue());
        }
    }


    public void terminateListener() {

        String debug = String.format("tests complete. good bye.");
        log.debug(debug);
    }


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


    public void onSourceDirectoryLoaded(final String path, final SourceDirectory sourceDirectory) {

        StringBuffer debug = new StringBuffer();

        debug.append("loaded ");
        debug.append(sourceDirectory.shouldThrowExceptionWhenNotFound() ? "required " : "");
        debug.append("sourceDirectory ");
        debug.append(path);

        log.debug(debug.toString());
    }


    public void onBeginPackageInvestigation(final JPackage javaPackage, final Rule ruleReference) {

        String debug = String.format("checking dependencies on package '%s' for Rule '%s'", javaPackage.getPath(), ruleReference.getId());

        log.debug(debug);
    }


    public void onCyclicDependencyDiscovered(final String package1, final Collection<String> package1DependenciesOnPackage2, final String package2, final Collection<String> package2DependenciesOnPackage1) {

        StringBuffer warn = new StringBuffer();

        warn.append("cycle found between ");
        warn.append(package1);
        warn.append(" and ");
        warn.append(package2);
        warn.append("\r\n");

        warn.append(package1);
        warn.append(" depends on ");
        warn.append(package2);
        warn.append(" at ");
        warn.append("\r\n");

        for (String className : package1DependenciesOnPackage2) {

            warn.append("\t");
            warn.append(className);
            warn.append("\r\n");
        }

        warn.append(package2);
        warn.append(" depends on ");
        warn.append(package1);
        warn.append(" at ");
        warn.append("\r\n");

        for (String className : package2DependenciesOnPackage1) {

            warn.append("\t");
            warn.append(className);
            warn.append("\r\n");
        }

        log.warn(warn.toString());
    }
}
