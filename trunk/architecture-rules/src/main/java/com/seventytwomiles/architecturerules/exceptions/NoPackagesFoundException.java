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
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com
 */
package com.seventytwomiles.architecturerules.exceptions;



/**
 * <p>Exception to be thrown when no packages are found in the given source path
 * if <samp>&lt;sources no-packages="exception"> </samp></p>
 *
 * @author mikenereson
 * @see ArchitectureException
 */
public class NoPackagesFoundException extends ArchitectureException {

    /**
     * @see RuntimeException#RuntimeException()
     */
    public NoPackagesFoundException() {
        super("no packages found");
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public NoPackagesFoundException(final Throwable cause) {
        super("no packages found", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public NoPackagesFoundException(final String path) {
        super("source directory '{0}' does not exist or can not be found".replaceAll("\\{0}", path.replaceAll("\\\\", "/")));
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public NoPackagesFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
