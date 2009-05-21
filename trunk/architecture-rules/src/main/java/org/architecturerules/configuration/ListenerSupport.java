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
package org.architecturerules.configuration;


import java.util.Collection;
import java.util.Properties;

import org.architecturerules.api.listeners.Listener;
import org.architecturerules.domain.JPackage;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * <p>Provides the mechanisms neccessary to register and terminate listeners, and to pass messages to all interested
 * <code>Listener</code> implementations.</p>
 *
 * @author mnereson
 * @see Listener
 */
public abstract class ListenerSupport implements Listener {

    /**
     * @see Listener#registerListener(Properties)
     * @param properties
     */
    public void registerListener(final Properties properties) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.registerListener(properties);
        }
    }


    /**
     * @see Listener#terminateListener()
     */
    public void terminateListener() {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.terminateListener();
        }
    }


    /**
     * @see Listener#onRuleAdded(Rule)
     */
    public void onRuleAdded(final Rule rule) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onRuleAdded(rule);
        }
    }


    /**
     * @see Listener#onRuleRemoved(Rule)
     */
    public void onRuleRemoved(final Rule rule) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onRuleRemoved(rule);
        }
    }


    /**
     * @see Listener#onSourceDirectoryAdded(SourceDirectory)
     */
    public void onSourceDirectoryAdded(final SourceDirectory sourceDirectory) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onSourceDirectoryAdded(sourceDirectory);
        }
    }


    /**
     * @see Listener#onSourceDirectoryRemoved(SourceDirectory)
     */
    public void onSourceDirectoryRemoved(final SourceDirectory sourceDirectory) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onSourceDirectoryRemoved(sourceDirectory);
        }
    }


    /**
     * @see Listener#onSourceDirectoryNotFound(SourceDirectory)
     */
    public void onSourceDirectoryNotFound(final SourceDirectory sourceDirectory) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onSourceDirectoryNotFound(sourceDirectory);
        }
    }


    /**
     * @see Listener#onSourceDirectoryEmpty(SourceDirectory)
     */
    public void onSourceDirectoryEmpty(final SourceDirectory sourceDirectory) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onSourceDirectoryEmpty(sourceDirectory);
        }
    }


    /**
     * @see Listener#onCyclicDependencyTestBegin()
     */
    public void onCyclicDependencyTestBegin() {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onCyclicDependencyTestBegin();
        }
    }


    /**
     * @see Listener#onCyclicDependencyTestEnd()
     */
    public void onCyclicDependencyTestEnd() {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onCyclicDependencyTestEnd();
        }
    }


    /**
     * @see Listener#onCyclicDependencyDiscovered(String, Collection, String, Collection)
     */
    public void onCyclicDependencyDiscovered(final String jPackage1, final Collection<String> package1DependenciesOnPackage2, final String jPackage2, final Collection<String> package2DependenciesOnPackage1) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onCyclicDependencyDiscovered(jPackage1, package1DependenciesOnPackage2, jPackage2, package2DependenciesOnPackage1);
        }
    }


    /**
     * @see Listener#onBeginTestingRules()
     */
    public void onBeginTestingRules() {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onBeginTestingRules();
        }
    }


    /**
     * @see Listener#onEndTestingRules()
     */
    public void onEndTestingRules() {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onEndTestingRules();
        }
    }


    /**
     * @see Listener#onWildcardPatternMatched(JPackage, JPackage)
     */
    public void onWildcardPatternMatched(final JPackage wildcard, final JPackage jPackage) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onWildcardPatternMatched(wildcard, jPackage);
        }
    }


    /**
     * @see Listener#onBeginRuleTest(Rule)
     */
    public void onBeginRuleTest(final Rule rule) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onBeginRuleTest(rule);
        }
    }


    /**
     * @see Listener#onEndRuleTest(Rule)
     */
    public void onEndRuleTest(final Rule rule) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onEndRuleTest(rule);
        }
    }


    /**
     * @see Listener#onPackageDependencyDiscovered(String, String)
     */
    public void onPackageDependencyDiscovered(final String packageName, final String dependencyPackageName) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onPackageDependencyDiscovered(packageName, dependencyPackageName);
        }
    }


    /**
     * @see Listener#onPackageDependencyViolationDiscovered(Rule, String, String)
     */
    public void onPackageDependencyViolationDiscovered(final Rule rule, final String packageName, final String dependencyPackageName) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onPackageDependencyViolationDiscovered(rule, packageName, dependencyPackageName);
        }
    }


    public void onSourceDirectoryLoaded(final String path, final SourceDirectory sourceDirectory) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onSourceDirectoryLoaded(path, sourceDirectory);
        }
    }


    public void onBeginPackageInvestigation(final JPackage javaPackage, final Rule ruleReference) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onBeginPackageInvestigation(javaPackage, ruleReference);
        }
    }


    /**
     *
     */
    abstract Collection<Listener> getListeners();
}
