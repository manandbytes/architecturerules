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


import com.seventytwomiles.architecturerules.configuration.ConfigurationFactory;
import com.seventytwomiles.architecturerules.exceptions.SourceNotFoundException;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;


/**
 * <p>Representation of a source directory to search for packages and .class
 * files in.</p>
 *
 * @author mikenereson
 */
public class SourceDirectory {


    private static final Log log = LogFactory.getLog(SourceDirectory.class);

    /**
     * <p>The value, which is set inside of the xml configuration file, which
     * indicates that when a source directory is not found, the source directory
     * should be ignored.</p>
     *
     * @parameter NOT_FOUND_IGNORE String
     */
    private static final String NOT_FOUND_IGNORE = "ignore";


    /**
     * <p>The value, which is set inside of the xml configuration file, which
     * indicates that when a source directory is not found, that an exception
     * should be thrown.</p>
     *
     * <p>When {@link #setNotFound(String)} is set to this value, a {@link
     * SourceNotFoundException} will be thrown.</p>
     *
     * @parameter NOT_FOUND_EXCEPTION String
     */
    private static final String NOT_FOUND_EXCEPTION = "exception";

    /**
     * <p>When true, if this source {@link #path} is not found a
     * <code>SourceNotFoundException</code> will be thrown.</p>
     *
     * * <p>If the value is not provided in the configuration, the default value
     * is used. {@link ConfigurationFactory#DEFAULT_CYCLICAL_DEPENDENCY_CONFIGURATION_VALUE}</p>
     *
     * @parameter shouldThrowExceptionWhenNotFound boolean
     */
    private boolean shouldThrowExceptionWhenNotFound
            = ConfigurationFactory.DEFAULT_NO_PACKAGES_CONFIGURATION_BOOLEAN_VALUE;

    /**
     * <p>Relative url to the path to search in for .class files.</p>
     *
     * @parameter path String
     */
    private String path;

    /**
     * <p>Holds the value in the xml configuration for the not-found
     * property.</p>
     *
     * @parameter notFound String
     * @noinspection UnusedDeclaration
     */
    private String notFound;


    /**
     * <p>Instantiates a new SourceDirectory entity.</p>
     */
    public SourceDirectory() {
    }


    /**
     * <p>Instantiates a new SourceDirectory with the given <tt>path</tt></p>
     *
     * @param path String {@link #path}
     */
    public SourceDirectory(final String path) {
        setPath(path);
    }


    /**
     * <p>Instantiates a new SourceDirectory with the given <tt>path</tt> and
     * <tt>shouldThrowExceptionWhenNotFound</tt> values</p>
     *
     * @param path String {@link #path}
     * @param shouldThrowExceptionWhenNotFound
     *             boolean {@link #shouldThrowExceptionWhenNotFound}
     */
    public SourceDirectory(final String path,
                           final boolean shouldThrowExceptionWhenNotFound) {

        setShouldThrowExceptionWhenNotFound(shouldThrowExceptionWhenNotFound);
        setPath(path);
    }


    /**
     * Setter for property {@link #shouldThrowExceptionWhenNotFound}.
     *
     * @param shouldThrowExceptionWhenNotFound
     *         Value to set for property <tt>shouldThrowExceptionWhenNotFound</tt>.
     */
    public void setShouldThrowExceptionWhenNotFound(
            final boolean shouldThrowExceptionWhenNotFound) {

        /**
         * Update notFound property so that the String value and boolean
         * values coincide
         */
        if (shouldThrowExceptionWhenNotFound) {

            notFound = NOT_FOUND_EXCEPTION;

        } else {

            notFound = NOT_FOUND_IGNORE;
        }

        this.shouldThrowExceptionWhenNotFound
                = shouldThrowExceptionWhenNotFound;
    }


    /**
     * <p>Instantiates a new SourceDirectory with the given <tt>path</tt> and
     * <tt>notFound</tt> values.</p>
     *
     * @param path     String {@link #path}
     * @param notFound boolean {@link @notFound}
     */
    public SourceDirectory(final String path, final String notFound) {
        setPath(path);
        setNotFound(notFound);
    }


    /**
     * Setter for property 'notFound'.
     *
     * @param notFound Value to set for property 'notFound'.
     */
    public void setNotFound(String notFound) {

        /**
         * When null, set to "null" so that the exception message that is about
         * to be thrown is meaningful.
         */
        if (notFound == null)
            notFound = "null";

        /**
         * Validate input.
         * Expect either 'ignore' or 'exception'
         */
        if (!(notFound.equalsIgnoreCase(NOT_FOUND_IGNORE) ||
                notFound.equalsIgnoreCase(NOT_FOUND_EXCEPTION))) {

            throw new IllegalArgumentException(
                    "'not-found' property of '" + notFound +
                            "' is invalid. valid values are " +
                            NOT_FOUND_IGNORE + " and "
                            + NOT_FOUND_EXCEPTION);
        }

        this.notFound = notFound;

        /**
         * Update shouldThrowExceptionWhenNotFound property so that the
         * String value and boolean values coincide
         */
        final boolean shouldThrowException
                = getNotFound().equalsIgnoreCase(NOT_FOUND_EXCEPTION);

        setShouldThrowExceptionWhenNotFound(shouldThrowException);
    }


    private String getNotFound() {
        return notFound;
    }


    /**
     * <p>Getter for property {@link #path}.</p>
     *
     * @return Value for property <tt>path</tt>.
     */
    public String getPath() {
        return path;
    }


    /**
     * <p>Setter for property {@link #path}.</p>
     *
     * @param path Value to set for property <tt>path</tt>.
     */
    public void setPath(final String path) {

        try {

            Assert.assertNotNull(path);
            Assert.assertFalse("".equals(path));

        } catch (final Throwable e) {

            throw new IllegalArgumentException(e.getMessage());
        }

        this.path = replaceBackslashForOS(path);
    }


    /**
     * <p>Replaces inappropriate backslash with the appropriate slash based on
     * the operating system's requirements</p>
     *
     * <p>For example, on a Windows system, <tt>src/main/resources</tt> becomes
     * <tt>src\\main\\resource</tt></p>
     *
     * <p>TODO: this may be able to be replaced with String.replaceAll, but I
     * couldn't get the regex just right</p>
     *
     * <p>This todo/issue is open at <a href="http://code.google.com/p/architecturerules/issues/detail?id=29">issue
     * 29</a></p>
     *
     * @param path String the path to fix
     * @return String the fixed path
     */
    String replaceBackslashForOS(final String path) {

        final StringBuffer result = new StringBuffer();

        final StringCharacterIterator iterator
                = new StringCharacterIterator(path);

        char character = iterator.current();

        final char goal = File.separator.toCharArray()[0];
        final char target = (goal == '\\' ? '/' : '\\');

        while (character != CharacterIterator.DONE) {

            result.append(character == target ? goal : character);
            character = iterator.next();
        }

        return result.toString();
    }


    /**
     * @see Object#equals(Object)
     */
    @SuppressWarnings({"RedundantIfStatement"})
    public boolean equals(final Object object) {

        if (this == object)
            return true;

        if (object == null)
            return false;

        if (!(object instanceof SourceDirectory))
            return false;

        final SourceDirectory that = (SourceDirectory) object;

        if (path != null ? !path.equals(
                that.getPath()) : that.getPath() != null) {

            return false;
        }

        return true;
    }


    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        return (path != null ? path.hashCode() : 0);
    }


    /**
     * @see Object#toString()
     */
    public String toString() {
        return path;
    }


    /**
     * Getter for property {@link #shouldThrowExceptionWhenNotFound}.
     *
     * @return Value for property <tt>shouldThrowExceptionWhenNotFound</tt>.
     */
    public boolean shouldThrowExceptionWhenNotFound() {
        return shouldThrowExceptionWhenNotFound;
    }
}
