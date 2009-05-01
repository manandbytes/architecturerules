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
package org.architecturerules.api.listeners;


import java.util.Collection;
import java.util.Properties;

import org.architecturerules.domain.JPackage;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 */
public interface Listener {

    /**
     * <p>TODO: javadoc</p>
     * @param properties
     */
    void registerListener(final Properties properties);


    /**
     * <p>TODO: javadoc</p>
     */
    void terminateListener();


    /* configuration */

    /**
     * <p>TODO: javadoc</p>
     *
     * @param rule
     */
    void onRuleAdded(Rule rule);


    /**
     * <p>TODO: javadoc</p>
     *
     * @param rule
     */
    void onRuleRemoved(Rule rule);


    /**
     * <p>TODO: javadoc</p>
     *
     * @param sourceDirectory
     */
    void onSourceDirectoryAdded(SourceDirectory sourceDirectory);


    /**
     * <p>TODO: javadoc</p>
     *
     * @param sourceDirectory
     */
    void onSourceDirectoryRemoved(SourceDirectory sourceDirectory);


    /**
     * <p>TODO: javadoc</p>
     *
     * @param sourceDirectory
     */
    void onSourceDirectoryNotFound(final SourceDirectory sourceDirectory);


    /**
     * <p>TODO: javadoc</p>
     *
     * @param sourceDirectory
     */
    public void onSourceDirectoryEmpty(final SourceDirectory sourceDirectory);


    /* cyclic dependency test */

    /**
     * <p>TODO: javadoc</p>
     */
    void onCyclicDependencyTestBegin();


    /**
     * <p>TODO: javadoc</p>
     */
    void onCyclicDependencyTestEnd();


    /**
     * <p>TODO: javadoc</p>
     *
     * @param jPackage1
     * @param package1DependenciesOnPackage2
     * @param jPackage2
     * @param package2DependenciesOnPackage1
     */
    void onCyclicDependencyDiscovered(String jPackage1, final Collection<String> package1DependenciesOnPackage2, String jPackage2, final Collection<String> package2DependenciesOnPackage1);


    /* rules test */

    /**
     * <p>TODO: javadoc</p>
     */
    void onBeginTestingRules();


    /**
     * <p>TODO: javadoc</p>
     */
    void onEndTestingRules();


    /**
     * <p>TODO: javadoc</p>
     *
     * @param wildcard
     * @param jPackage
     */
    void onWildcardPatternMatched(JPackage wildcard, JPackage jPackage);


    /**
     * <p>TODO: javadoc</p>
     *
     * @param rule
     */
    void onBeginRuleTest(Rule rule);


    /**
     * <p>TODO: javadoc</p>
     *
     * @param rule
     */
    void onEndRuleTest(Rule rule);


    /**
     * <p>TODO: javadoc</p>
     *
     * @param packageName String full name of the package.
     * @param dependencyPackageName String full name of the dependency package.
     */
    void onPackageDependencyDiscovered(String packageName, String dependencyPackageName);


    /**
     * <p>TODO: javadoc</p>
     *
     * @param rule Rule that defines the package and violation.
     * @param packageName String full package name of package that the Rule restricts
     * @param dependencyPackageName String full package name of the package that the packageName depends on but that the
     * Rule restricts
     */
    void onPackageDependencyViolationDiscovered(final Rule rule, String packageName, String dependencyPackageName);


    /**
     * <p>TODO: javadoc </p>
     * @param path todo
     * @param sourceDirectory todo
     */
    void onSourceDirectoryLoaded(final String path, final SourceDirectory sourceDirectory);


    /**
     * <p>TODO: javadoc </p>
     * @param javaPackage TODO
     * @param ruleReference TODO
     */
    void onBeginPackageInvestigation(final JPackage javaPackage, final Rule ruleReference);
}
