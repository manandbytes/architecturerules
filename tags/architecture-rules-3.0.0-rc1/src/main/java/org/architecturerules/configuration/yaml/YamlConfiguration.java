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
package org.architecturerules.configuration.yaml;


import java.util.Collection;
import java.util.Map;

import org.architecturerules.domain.SourceDirectory;


public class YamlConfiguration {

    private boolean doCyclicDependencyTest;

    public boolean isDoCyclicDependencyTest() {

        return doCyclicDependencyTest;
    }


    public void setDoCyclicDependencyTest(boolean doCycles) {

        this.doCyclicDependencyTest = doCycles;
    }

    private Collection<YamlRule> rules;

    public Collection<YamlRule> getRules() {

        return rules;
    }


    public void setRules(Collection<YamlRule> rules) {

        this.rules = rules;
    }

    private boolean throwExceptionWhenNoPackages;

    public boolean isThrowExceptionWhenNoPackages() {

        return throwExceptionWhenNoPackages;
    }


    public void setThrowExceptionWhenNoPackages(boolean throwExceptionWhenNoPackages) {

        this.throwExceptionWhenNoPackages = throwExceptionWhenNoPackages;
    }

    private Map<String, String> properties;

    public Map<String, String> getProperties() {

        return properties;
    }


    public void setProperties(Map<String, String> properties) {

        this.properties = properties;
    }

    private Collection<SourceDirectory> sources;

    public Collection<SourceDirectory> getSources() {

        return sources;
    }


    public void setSources(Collection<SourceDirectory> sources) {

        this.sources = sources;
    }

    private Collection<String> listeners;

    public Collection<String> getListeners() {

        return listeners;
    }


    public void setListeners(Collection<String> listeners) {

        this.listeners = listeners;
    }
}
