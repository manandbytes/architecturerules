/**
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com/svn/docs/index.html
 */
package com.seventytwomiles.architecturerules.configuration;


import com.seventytwomiles.architecturerules.domain.JPackage;
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
 * file in the classpath, through programmatic configuration, or both.</p>
 *
 * @author mikenereson
 * @see ConfigurationFactory
 * @see UnmodifiableConfiguration
 */
public class Configuration
{
    protected static final Log log = LogFactory.getLog( Configuration.class );

    /**
     * <p><code>Rules</code> that are read from the configuration file or added
     * programatically.</p>
     *
     * @parameter rules Set
     */
    private final Collection<Rule> rules = new HashSet<Rule>(  );

    /**
     * <p>List of <code>SourceDirectory</code> that are read from the
     * configuration file and or added programatically.</p>
     *
     * @parameter sources List
     */
    private final Collection<SourceDirectory> sources = new HashSet<SourceDirectory>(  );

    /**
     * <p>sets to true when <samp>&lt;sources no-packages="exception"&gt;</samp>,
     * false when <samp>&lt;sources no-packages="ignore"&gt;</samp></p>
     *
     * @parameter throwExceptionWhenNoPackages boolean
     */
    private boolean throwExceptionWhenNoPackages;

    /**
     * <p>sets to true when <samp>&lt;cyclicalDependency test="true"/> </samp>,
     * false when <samp>&lt;cyclicalDependency test="false"/> </samp></p>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    private boolean doCyclicDependencyTest;

    /**
     * <p>Getter for property {@link #rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    public Collection<Rule> getRules(  )
    {
        return this.rules;
    }

    /**
     * <p>Getter for property {@link #sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    public Collection<SourceDirectory> getSources(  )
    {
        return this.sources;
    }

    /**
     * <p>Setter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @param doCyclicDependencyTest Value to set for property
     * <tt>doCyclicDependencyTest</tt>.
     * @return Configuration this <code>Configuration</code> which allows for
     *         method chaining
     */
    public Configuration setDoCyclicDependencyTest( final boolean doCyclicDependencyTest )
    {
        this.doCyclicDependencyTest = doCyclicDependencyTest;

        return this;
    }

    /**
     * <p>Setter for property {@link #throwExceptionWhenNoPackages}.</p>
     *
     * @param throwExceptionWhenNoPackages Value to set for property
     * <tt>throwExceptionWhenNoPackages</tt>.
     * @return Configuration this <code>Configuration</code> which allows for
     *         method chaining
     */
    public Configuration setThrowExceptionWhenNoPackages( final boolean throwExceptionWhenNoPackages )
    {
        this.throwExceptionWhenNoPackages = throwExceptionWhenNoPackages;

        return this;
    }

    /**
     * <p>Add a new <code>Rule</code> to {@link #rules}</p>
     *
     * @param rule Rule to add
     * @return Configuration this <code>Configuration</code> to allow for method
     *         chaining.
     */
    public Configuration addRule( final Rule rule )
    {
        /* validate input */
        Assert.assertNotNull( "rule can not be null", rule );

        Assert.assertNotNull( "rule id can not be null",
                              rule.getId(  ) );

        final String id = rule.getId(  );
        Assert.assertFalse( "rule id must not be empty",
                            id.equals( "" ) );

        final Collection<JPackage> packages = rule.getPackages(  );
        Assert.assertNotNull( "rule packages can not be null", packages );

        Assert.assertFalse( "rule packages must not be empty",
                            rule.getPackages(  ).isEmpty(  ) );

        Assert.assertFalse( "rule violations must not be empty",
                            rule.getViolations(  ).isEmpty(  ) );

        final boolean added = rules.add( rule );

        if ( added )
        {
            log.debug( String.format( "added Rule %s to Configuration", id ) );
        } else
        {
            log.debug( String.format( "failed to add Rule %s to Configuration", id ) );
        }

        return this;
    }

    /**
     * <p>Add a new <code>SourceDirectory</code> to {@link #sources}</p>
     *
     * @param sourceDirectory SourceDirectory to add
     * @return Configuration this <code>Configuration</code> to allow for method
     *         chaining.
     */
    public Configuration addSource( final SourceDirectory sourceDirectory )
    {
        if ( sourceDirectory == null )
        {
            throw new IllegalArgumentException( "sourceDirectory can not be null" );
        }

        final String path = sourceDirectory.getPath(  );

        if ( ( path == null ) || path.equals( "" ) )
        {
            throw new IllegalArgumentException( "sourceDirectory.path can not be empty or null" );
        }

        final boolean added = sources.add( sourceDirectory );

        if ( added )
        {
            log.debug( String.format( "added source %s to Configuration", path ) );
        } else
        {
            log.debug( String.format( "failed to add source %s to Configuration", path ) );
        }

        return this;
    }

    /**
     * <p>Getter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @return Value for property <tt>doCyclicDependencyTest</tt>.
     */
    public boolean shouldDoCyclicDependencyTest(  )
    {
        return doCyclicDependencyTest;
    }

    /**
     * <p> Getter for property {@link #throwExceptionWhenNoPackages}.</p>
     *
     * @return Value for property <tt>throwExceptionWhenNoPackages</tt>.
     */
    public boolean shouldThrowExceptionWhenNoPackages(  )
    {
        return throwExceptionWhenNoPackages;
    }
}
