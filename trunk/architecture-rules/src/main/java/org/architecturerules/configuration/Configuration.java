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
package org.architecturerules.configuration;


import junit.framework.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.architecturerules.api.configuration.ConfigurationFactory;
import org.architecturerules.api.listeners.Listener;
import org.architecturerules.domain.JPackage;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * <p>An instance of <code>Configuration</code> allows the application to specifiy where the source directories are,
 * what rules to test against and under what conditions should an <code>Exception</code> be thrown.</p>
 *
 * <p>This <code>Configuration</code> may be loaded by configuration from an XML file in the classpath, through
 * programmatic configuration, or both.</p>
 *
 * @author mikenereson
 * @see ConfigurationFactory
 * @see UnmodifiableConfiguration
 */
public class Configuration extends ListenerSupport {

    /**
     * <p>To log with. See <tt>log4j.xml</tt>.</p>
     *
     * @parameter log Log
     */
    protected static final Log log = LogFactory.getLog(Configuration.class);

    /**
     * <p><code>Rules</code> that are read from the configuration file or added programatically.</p>
     *
     * @parameter rules Set
     */
    private final Collection<Rule> rules = new HashSet<Rule>();

    /**
     * <p>List of <code>SourceDirectory</code> that are read from the configuration file and or added
     * programatically.</p>
     *
     * @parameter sources List
     */
    private final Collection<SourceDirectory> sources = new HashSet<SourceDirectory>();

    /**
     * <p>sets to true when <samp>&lt;sources no-packages="exception"&gt;</samp>, false when <samp>&lt;sources
     * no-packages="ignore"&gt;</samp></p>
     *
     * @parameter throwExceptionWhenNoPackages boolean
     */
    private boolean throwExceptionWhenNoPackages;

    /**
     * <p>sets to true when <samp>&lt;cyclicalDependency test="true"/> </samp>, false when <samp>&lt;cyclicalDependency
     * test="false"/> </samp></p>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    private boolean doCyclicDependencyTest;

    /**
     * <p>Listeners are notified when specified events occur. Each listener implementation in this collection is
     * notified in no specific order.</p>
     *
     * @paramerter listeners Set<Listener>
     */
    private final Set<Listener> listeners = new HashSet<Listener>();

    /**
     * <p>Properties defined by the user or default configuration which can be used by other entities such as
     * <tt>Listener</tt> implementations at {@link Listener#registerListener(Properties)} or services (which can access
     * {@link #getProperties()}.</p>
     *
     * <p>Instantiates to empty properties.</p>
     *
     * @parameter properties Properties
     */
    private Properties properties = new Properties();

    /**
     * <p>Instantiate a new <code>Configuration</code> and load up default values.</p>
     */
    public Configuration() {

    }

    /**
     * <p>Add a new <code>Listener</code> to {@link #listeners} by its class name</p>
     *
     * @param listenerClass Fully qualified class name of a <code>Listener</code> implementation.
     * @return Configuration this <code>Configuration</code> to allow for method chaining.
     */
    public Configuration addListener(final String listenerClass) {

        if (listenerClass == null) {

            throw new IllegalArgumentException("listener can not be null");
        }

        if (listenerClass.equals("")) {

            throw new IllegalArgumentException("listener can not be empty String");
        }

        try {

            Class<?> clazz = Class.forName(listenerClass);

            if (!Listener.class.isAssignableFrom(clazz)) {

                String message = String.format("%s is not a Listener implementation. See %s", listenerClass, Listener.class.getName());

                throw new IllegalArgumentException(message);
            }

            if (clazz.getName().equals(Configuration.class.getName())) {

                String message = String.format("%s is not a valid Listener implementation.", listenerClass, Listener.class.getName());

                throw new IllegalArgumentException(message);
            }

            Listener implementation = (Listener) Class.forName(listenerClass).newInstance();

            return addListener(implementation);
        } catch (ClassNotFoundException e) {

            String message = String.format("can not locate listener %s in classpath", listenerClass);
            throw new IllegalArgumentException(message);
        } catch (IllegalAccessException e) {

            String message = String.format("can not access listener %s in classpath", listenerClass);
            throw new IllegalArgumentException(message);
        } catch (InstantiationException e) {

            String message = String.format("can not instantiate listener %s in classpath", listenerClass);
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * <p>Getter for property {@link #listeners}.</p>
     *
     * @return Value for property <tt>listeners</tt>.
     */
    public Set<Listener> getListeners() {

        return listeners;
    }


    /**
     * <p>Getter for property {@link #rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    public Collection<Rule> getRules() {

        return this.rules;
    }


    /**
     * <p>Getter for property {@link #sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    public Collection<SourceDirectory> getSources() {

        return this.sources;
    }


    /**
     * <p>Add a new <code>Listener</code> to {@link #listeners}</p>
     *
     * @param listener Listener to add
     * @return Configuration this <code>Configuration</code> to allow for method chaining.
     * @throws IllegalArgumentException when <tt>listener</tt> is <tt>null</tt>
     */
    public Configuration addListener(final Listener listener) {

        if (listener == null) {

            throw new IllegalArgumentException("listener can not be null");
        }

        String name = listener.getClass().getSimpleName();

        final boolean added = listeners.add(listener);

        if (added) {

            log.debug(String.format("added listener %s to Configuration", name));
        } else {

            log.debug(String.format("failed to add source %s to Configuration", name));
        }

        return this;
    }


    /**
     * <p>Add a new <code>Rule</code> to {@link #rules}</p>
     *
     * @param rule Rule to add
     * @return Configuration this <code>Configuration</code> to allow for method chaining.
     */
    public Configuration addRule(final Rule rule) {

        /* validate input */
        Assert.assertNotNull("rule can not be null", rule);

        Assert.assertNotNull("rule id can not be null", rule.getId());

        final String id = rule.getId();
        Assert.assertFalse("rule id must not be empty", id.equals(""));

        final Collection<JPackage> packages = rule.getPackages();
        Assert.assertNotNull("rule packages can not be null", packages);

        Assert.assertFalse("rule packages must not be empty", rule.getPackages().isEmpty());

        Assert.assertFalse("rule violations must not be empty", rule.getViolations().isEmpty());

        final boolean added = rules.add(rule);

        if (added) {

            super.onRuleAdded(rule);
        } else {

            log.warn(String.format("failed to add Rule %s to Configuration", id));
        }

        return this;
    }


    /**
     * <p>Add a new <code>SourceDirectory</code> to {@link #sources}</p>
     *
     * @param sourceDirectory SourceDirectory to add
     * @return Configuration this <code>Configuration</code> to allow for method chaining.
     */
    public Configuration addSource(final SourceDirectory sourceDirectory) {

        if (sourceDirectory == null) {

            throw new IllegalArgumentException("sourceDirectory can not be null");
        }

        final String path = sourceDirectory.getPath();

        if ((path == null) || path.equals("")) {

            throw new IllegalArgumentException("sourceDirectory.path can not be empty or null");
        }

        final boolean added = sources.add(sourceDirectory);

        if (added) {

            super.onSourceDirectoryAdded(sourceDirectory);
        } else {

            log.warn(String.format("failed to add source %s to Configuration", path));
        }

        return this;
    }


    /**
     * <p>Try to remove an exiting <code>Listener</code> from {@link #listeners}. Given Listener will not be removed if
     * it is not in the set of Listeners.</p>
     *
     * @param listener Listener to try to remove
     * @return Configuration this <code>Configuration</code> to allow for method chaining.
     */
    public Configuration removeListener(final Listener listener) {

        if (listener == null) {

            throw new IllegalArgumentException("listener can not be null");
        }

        String name = listener.getClass().getSimpleName();
        boolean removed = listeners.remove(listener);

        if (removed) {

            log.debug(String.format("removed listener %s from Configuration", name));
        } else {

            log.warn(String.format("failed to remove listener %s from Configuration", name));
        }

        return this;
    }


    /**
     * <p>Tries to remove all Listeners in  in {@link #listeners} that are the given listenerClass.</p>
     *
     * @param listenerClass Fully qualified class name of a <code>Listener</code> implementation.
     * @return Configuration this <code>Configuration</code> to allow for method chaining.
     */
    public Configuration removeListener(final String listenerClass) {

        if (listenerClass == null) {

            throw new IllegalArgumentException("listener can not be null");
        }

        if (listenerClass.equals("")) {

            throw new IllegalArgumentException("listener can not be empty String");
        }

        try {

            Class clazz = Class.forName(listenerClass);

            for (Listener listener : listeners) {

                if (listener.getClass().isAssignableFrom(clazz)) {

                    removeListener(listener);
                }
            }
        } catch (ClassNotFoundException e) {

            String message = String.format("can not locate listener %s in classpath", listenerClass);
            throw new IllegalArgumentException(message);
        }

        return this;
    }


    /**
     * <p>Setter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @param doCyclicDependencyTest Value to set for property <tt>doCyclicDependencyTest</tt>.
     * @return Configuration this <code>Configuration</code> which allows for method chaining
     */
    public Configuration setDoCyclicDependencyTest(final boolean doCyclicDependencyTest) {

        this.doCyclicDependencyTest = doCyclicDependencyTest;

        return this;
    }


    /**
     * <p>Setter for property {@link #throwExceptionWhenNoPackages}.</p>
     *
     * @param throwExceptionWhenNoPackages Value to set for property <tt>throwExceptionWhenNoPackages</tt>.
     * @return Configuration this <code>Configuration</code> which allows for method chaining
     */
    public Configuration setThrowExceptionWhenNoPackages(final boolean throwExceptionWhenNoPackages) {

        this.throwExceptionWhenNoPackages = throwExceptionWhenNoPackages;

        return this;
    }


    /**
     * <p>Getter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @return Value for property <tt>doCyclicDependencyTest</tt>.
     */
    public boolean shouldDoCyclicDependencyTest() {

        return doCyclicDependencyTest;
    }


    /**
     * <p> Getter for property {@link #throwExceptionWhenNoPackages}.</p>
     *
     * @return Value for property <tt>throwExceptionWhenNoPackages</tt>.
     */
    public boolean shouldThrowExceptionWhenNoPackages() {

        return throwExceptionWhenNoPackages;
    }


    /**
     * <p>Setter for property {@link #properties}.</p>
     *
     * @param properties Value to set for property <tt>properties</tt>.
     */
    public void setProperties(final Properties properties) {

        if (properties == null) {

            throw new IllegalArgumentException("properties can not be null");
        }

        this.properties = properties;
    }


    /**
     * <p> Getter for property {@link #properties}.</p>
     *
     * @return Value for property <tt>properties</tt>.
     */
    public Properties getProperties() {

        return properties;
    }
}
