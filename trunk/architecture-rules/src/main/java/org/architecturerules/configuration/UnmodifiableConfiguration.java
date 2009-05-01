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


import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.architecturerules.api.listeners.Listener;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;


/**
 * <p>An UnmodifiableConfiguration is a <code>Configuration</code> instance whose setters and collections are
 * unavailable or unmodifiable.</p>
 *
 * @author mikenereson
 */
public final class UnmodifiableConfiguration extends Configuration {

    /**
     * <p>Instantiates a new unmodifiable configuration class.</p>
     *
     * @param configuration Configuration to offer as unmodifiable
     */
    public UnmodifiableConfiguration(final Configuration configuration) {

        super.getRules().addAll(configuration.getRules());
        super.getSources().addAll(configuration.getSources());

        super.setDoCyclicDependencyTest(configuration.shouldDoCyclicDependencyTest());

        super.setThrowExceptionWhenNoPackages(configuration.shouldThrowExceptionWhenNoPackages());

        super.getListeners().addAll(configuration.getListeners());
    }

    @Override
    public Configuration addListener(final Listener listener) {

        throw new UnsupportedOperationException("This configuration can not be modified");
    }


    @Override
    public Configuration addListener(final String listenerClass) {

        throw new UnsupportedOperationException("This configuration can not be modified");
    }


    @Override
    public Configuration addRule(final Rule rule) {

        throw new UnsupportedOperationException("This configuration can not be modified");
    }


    @Override
    public Configuration addSource(final SourceDirectory sourceDirectory) {

        throw new UnsupportedOperationException("This configuration can not be modified");
    }


    @Override
    public Set<Listener> getListeners() {

        return Collections.unmodifiableSet(super.getListeners());
    }


    /**
     * <p>Getter for property {@link #rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    @Override
    public Collection<Rule> getRules() {

        return Collections.unmodifiableCollection(super.getRules());
    }


    /**
     * <p>Getter for property {@link #sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    @Override
    public Collection<SourceDirectory> getSources() {

        return Collections.unmodifiableCollection(super.getSources());
    }


    @Override
    public Configuration removeListener(final Listener listener) {

        throw new UnsupportedOperationException("This configuration can not be modified");
    }


    @Override
    public Configuration removeListener(final String listenerClass) {

        throw new UnsupportedOperationException("This configuration can not be modified");
    }


    /**
     * <p>Setter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @param doCyclicDependencyTest Value to set for property <tt>doCyclicDependencyTest</tt>.
     */
    @Override
    public UnmodifiableConfiguration setDoCyclicDependencyTest(final boolean doCyclicDependencyTest) {

        throw new UnsupportedOperationException("");
    }


    /**
     * <p>Setter for property {@link #throwExceptionWhenNoPackages}.</p>
     *
     * @param throwExceptionWhenNoPackages Value to set for property <tt>throwExceptionWhenNoPackages</tt>.
     */
    @Override
    public UnmodifiableConfiguration setThrowExceptionWhenNoPackages(final boolean throwExceptionWhenNoPackages) {

        throw new UnsupportedOperationException("");
    }
}
