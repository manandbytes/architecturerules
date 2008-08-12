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
package org.architecturerules.exceptions;


import java.util.Collection;


/**
 * <p>Exception to be thrown when a configured source is not found and <samp>&lt;source
 * not-found="exception"></samp></p>
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see ArchitectureException
 */
public class SourceNotFoundException extends ArchitectureException {

    /**
     * @see RuntimeException#RuntimeException()
     */
    public SourceNotFoundException() {
        super("sources not found");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public SourceNotFoundException(final String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public SourceNotFoundException(final Throwable cause) {
        super("sources not found", cause);
    }


    /**
     * <p>Instantiates a new SourceNotFoundException with a message containing all of the sources that were
     * intteroagated.</p>
     *
     * @param sources
     */
    public SourceNotFoundException(final Collection sources) {
        // remove \\ from path because regex replace all removes them
        // remove the [ and ] at the ends of Collection.toString
        super("unable to find any source files in given source directories {0}".replaceAll("\\{0}", sources.toString().replaceAll("\\\\", "/")).replaceAll("\\[", "").replaceAll("\\]", ""));
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public SourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
