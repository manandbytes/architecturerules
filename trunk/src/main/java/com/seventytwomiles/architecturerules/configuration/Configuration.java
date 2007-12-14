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


import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.HashSet;



/**
 * <p>An instance of <code>Configuration</code> allows the application to
 * specifiy where the source directories are, what rules to test against and
 * under what conditions should an <code>Exception</code> be thrown.</p>
 *
 * <p>This <code>Configuration</code> may be loaded by configuration from an XML
 * file in the classpath, through programmaticconfigurationn, or both.</p>
 *
 * @author mikenereson
 * @see ConfigurationFactory
 * @see UnmodifiableConfiguration
 */
public class Configuration {


    private static final Log log = LogFactory.getLog(Configuration.class);
    /**
     * <p><code>Rules</code> that are read from the configuration file or added
     * programatically.</p>
     *
     * @parameter rules Set
     */
    final Collection rules = new HashSet();
    /**
     * <p>List of <code>SourceDirectory</code> that are read from the
     * configuration file and or added programatically.</p>
     *
     * @parameter sources List
     */
    final Collection sources = new HashSet();
    /**
     * <p>sets to true when <samp>&lt;sources no-packages="exception"&gt;</samp>,
     * false when <samp>&lt;sources no-packages="ignore"&gt;</samp></p>
     *
     * @parameter throwExceptionWhenNoPackages boolean
     */
    boolean throwExceptionWhenNoPackages;
    /**
     * <p>sets to true when <samp>&lt;cyclicalDependency test="true"/> </samp>,
     * false when <samp>&lt;cyclicalDependency test="false"/> </samp></p>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    boolean doCyclicDependencyTest;


    /**
     * <p>Getter for property {@link #rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    public Collection getRules() {
        return rules;
    }


    /**
     * <p>Getter for property {@link #sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    public Collection getSources() {
        return sources;
    }


    /**
     * <p>Setter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @param doCyclicDependencyTest Value to set for property
     * <tt>doCyclicDependencyTest</tt>.
     */
    public void setDoCyclicDependencyTest(final boolean doCyclicDependencyTest) {
        this.doCyclicDependencyTest = doCyclicDependencyTest;
    }


    /**
     * <p>Setter for property {@link #throwExceptionWhenNoPackages}.</p>
     *
     * @param throwExceptionWhenNoPackages Value to set for property
     * <tt>throwExceptionWhenNoPackages</tt>.
     */
    public void setThrowExceptionWhenNoPackages(final boolean throwExceptionWhenNoPackages) {
        this.throwExceptionWhenNoPackages = throwExceptionWhenNoPackages;
    }


    /**
     * <p>Add a new <code>Rule</code> to {@link #rules}</p>
     *
     * @param rule Rule to add
     * @return boolean <tt>true</tt> if this set did not already contain the
     *         specified element.
     */
    public boolean addRule(final Rule rule) {
        /* validate input */
        Assert.assertNotNull("rule can not be null", rule);

        Assert.assertNotNull("rule id can not be null", rule.getId());
        Assert.assertFalse("rule id must not be empty", rule.getId().equals(""));

        Assert.assertNotNull("rule packages can not be null", rule.getPackages());
        Assert.assertFalse("rule packages must not be empty", rule.getPackages().isEmpty());

        Assert.assertFalse("rule violations must not be empty", rule.getViolations().isEmpty());

        return rules.add(rule);
    }


    public boolean addSource(final SourceDirectory sourceDirectory) {
        if (sourceDirectory == null)
            throw new IllegalArgumentException("sourceDirectory can not be null");

        final String path = sourceDirectory.getPath();

        if (path == null || path.equals(""))
            throw new IllegalArgumentException("sourceDirectory.path can not be empty or null");

        return sources.add(sourceDirectory);
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
}
