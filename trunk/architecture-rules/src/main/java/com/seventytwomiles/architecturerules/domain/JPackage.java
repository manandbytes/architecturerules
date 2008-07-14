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
package com.seventytwomiles.architecturerules.domain;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static java.lang.String.format;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>A java package. This class wraps the java package to give it
 * functionality, such as the ability to check and see if it matches another
 * package.</p>
 *
 * @author mikenereson
 */
public class JPackage
{
    private static final Log log = LogFactory.getLog( JPackage.class );

    /**
     * <p>All of the symbols or characters that represent a wildcard.</p>
     */
    private static final char[] WILDCHARS = new char[] { '*' };

    /**
     * <p>period separated path to package such as <samp>com.seventeytwomiles.architecturerules.domain</samp>.</p>
     *
     * @parameter path String
     */
    private String path;

    /**
     * <p>Constructs a new <code>JPackage</code></p>
     */
    public JPackage(  )
    {
    }

    /**
     * <p>Constructs a new <code>JPackage</code> with the given
     * <tt>path</tt></p>
     *
     * @param path String to set for {@link #path}
     */
    public JPackage( final String path )
    {
        setPath( path );
    }

    /**
     * <p>Getter for property {@link #path}.</p>
     *
     * @return Value for property <tt>path</tt>.
     */
    public String getPath(  )
    {
        return path;
    }

    /**
     * <p>Setter for property  {@link #path}</p>
     *
     * @param path Value to set for property <tt>path</tt>
     */
    public void setPath( final String path )
    {
        this.path = path;
    }

    @Override
    public boolean equals( final Object o )
    {
        if ( this == o )
        {
            return true;
        }

        if ( ! ( o instanceof JPackage ) )
        {
            return false;
        }

        final JPackage that = (JPackage) o;

        if ( ( path != null ) ? ( ! path.equals( that.getPath(  ) ) ) : ( that.getPath(  ) != null ) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode(  )
    {
        return ( ( path != null ) ? path.hashCode(  ) : 0 );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString(  )
    {
        return this.path;
    }

    /**
     * <p>Determines if a given <code>JPackage</code> or <code>String</code> is
     * represented by this <code>JPackage</code>.</p>
     *
     * <p>If given Object is empty String then <tt>false<tt><</p>
     *
     * @param that a String or JPackage
     * @return boolean <tt>true</tt> when a perfect match is found or when the
     *         wildcards match.
     */
    public boolean matches( final Object that )
    {
        if ( ! ( that instanceof String ) && ! ( that instanceof JPackage ) )
        {
            return false;
        }

        if ( that.equals( "" ) )
        {
            return false;
        }

        if ( hasWildcards(  ) )
        {
            return regExMatch( that );
        } else
        {
            return prefectMatch( that );
        }
    }

    /**
     * <p>Determines if this <code>JPackage</code> uses wildcards to match more
     * than one package.</p>
     *
     * @return boolean <tt>true</tt> when <tt>path</tt> contains any of the
     *         {@link #WILDCHARS}.
     */
    private boolean hasWildcards(  )
    {
        for ( final char wildChar : WILDCHARS )
        {
            if ( this.path.contains( String.valueOf( wildChar ) ) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>Manipulates the <tt>path</tt> value to add Regular Expression support
     * then attempts to match the Reg Ex against the given <tt>Object</tt>.</p>
     *
     * <p>This supports <dl> <dt>terminating package</dt> <dd>1.2.*</dd>
     * <dt>terminating package or sub package description</dt> <dd>1.2..*</dd>
     * <dt>internal package</dt> <dd>1.*.2</dd> <dt>internal package or sub
     * package</dt> <dd>1.*..4</dd> <dt>internal and terminating</dt>
     * <dd>1.*.3.*</dd> <dd>1.*.3..*</dd><dd>1..*.5.*</dd> </dl></p>
     *
     *
     * <p>TODO: This does not support the single character <tt>*</tt> yet.</p>
     *
     * @param that <code>Object</code> of type <code>String</code> or
     * <code>JPackage</code>
     * @return boolean <tt>true</tt> when the given <tt>Object</tt> is a
     *         supported type, and then regular expression that is constructed
     *         matches.
     */
    private boolean regExMatch( final Object that )
    {
        /**
         * TODO: code support for path = "*"
         * TODO: then Update javadoc
         */
        final String regex =
            this.path.replaceAll( "\\.", "\\\\." ).replaceAll( "\\\\.\\\\.\\\\*", "\\\\.\\[A-Za-z_0-9.]" )
                     .replaceAll( "\\.\\*", "\\.[A-Za-z_0-9]*" );

        final Pattern pattern = Pattern.compile( regex );
        final Matcher matcher;

        final boolean matched;

        if ( that instanceof String )
        {
            final String thatPackage = (String) that;
            matcher = pattern.matcher( thatPackage );

            matched = matcher.matches(  );
        } else if ( that instanceof JPackage )
        {
            final JPackage thatPackage = (JPackage) that;
            matcher = pattern.matcher( thatPackage.getPath(  ) );

            matched = matcher.matches(  );
        } else
        {
            matched = false;
        }

        if ( matched )
        {
            log.debug( format( "matched %s to %s", this.path, that ) );
        }

        return matched;
    }

    /**
     * <p>Matches by String equals against a String or JPackage</p>
     *
     * @param that <code>Object</code> of type <code>String</code> or
     * <code>JPackage</code>
     * @return <tt>true</tt> when the given <tt>Object</tt> is a supported type,
     *         and an exact match to this <code>JPackage</code>.
     */
    private boolean prefectMatch( final Object that )
    {
        if ( that instanceof String )
        {
            final String thatPackage = (String) that;

            return this.path.equals( thatPackage );
        }

        if ( that instanceof JPackage )
        {
            final JPackage thatPackage = (JPackage) that;

            return this.path.equals( thatPackage.getPath(  ) );
        }

        return false;
    }
}
