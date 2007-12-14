package com.seventytwomiles.architecturerules.configuration;

/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more information visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Collections;



/**
 * <p>An UnmodifiableConfiguration is a <code>Configuration</code> instance
 * whose setters and collections are unavailable or unmodifiable.</p>
 *
 * @author mikenereson
 */
public final class UnmodifiableConfiguration extends Configuration {


    private static final Log log = LogFactory.getLog(UnmodifiableConfiguration.class);


    /**
     * <p>Instantiates a new unmodifiable configuration class.</p>
     *
     * @param configuration Configuration to offer as unmodifiable
     */
    public UnmodifiableConfiguration(final Configuration configuration) {
        this.rules.addAll(configuration.getRules());
        this.sources.addAll(configuration.getSources());
        this.doCyclicDependencyTest = configuration.shouldDoCyclicDependencyTest();
        this.throwExceptionWhenNoPackages = configuration.shouldThrowExceptionWhenNoPackages();
    }


    /**
     * <p>Getter for property {@link #rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    public Collection getRules() {
        return Collections.unmodifiableCollection(rules);
    }


    /**
     * <p>Getter for property {@link #sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    public Collection getSources() {
        return Collections.unmodifiableCollection(sources);
    }


    /**
     * <p>Setter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @param doCyclicDependencyTest Value to set for property
     * <tt>doCyclicDependencyTest</tt>.
     */
    public void setDoCyclicDependencyTest(final boolean doCyclicDependencyTest) {
        throw new UnsupportedOperationException("");
    }


    /**
     * <p>Setter for property {@link #throwExceptionWhenNoPackages}.</p>
     *
     * @param throwExceptionWhenNoPackages Value to set for property
     * <tt>throwExceptionWhenNoPackages</tt>.
     */
    public void setThrowExceptionWhenNoPackages(final boolean throwExceptionWhenNoPackages) {
        throw new UnsupportedOperationException("");
    }
}
