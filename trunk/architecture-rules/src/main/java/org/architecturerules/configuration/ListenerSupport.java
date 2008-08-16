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
package org.architecturerules.configuration;


import org.architecturerules.api.listeners.Listener;
import org.architecturerules.domain.JPackage;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;

import java.util.Collection;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see Listener
 */
public abstract class ListenerSupport implements Listener {

    /**
     * @see Listener#register()
     */
    public void register() {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.register();
        }
    }


    /**
     * @see Listener#terminate()
     */
    public void terminate() {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.terminate();
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
     * @see Listener#onCyclicDependencyDiscovered(JPackage, JPackage)
     */
    public void onCyclicDependencyDiscovered(final JPackage jPackage1, final JPackage jPackage2) {

        Collection<Listener> listeners = getListeners();

        for (Listener listener : listeners) {

            listener.onCyclicDependencyDiscovered(jPackage1, jPackage2);
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


    /**
     *
     */
    abstract Collection<Listener> getListeners();
}
