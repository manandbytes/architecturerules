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

package com.seventytwomiles.architecturerules.exceptions;

/**
 * <p>Top-level Architecture Rules Exception. All domain Exceptions should
 * extend this class.</p>
 *
 * @author mikenereson
 * @see RuntimeException
 */
@SuppressWarnings({"JavaDoc"})
public class ArchitectureException extends RuntimeException {


    /**
     * @see RuntimeException#Exception()
     */
    public ArchitectureException() {
        super();
    }

    /**
     * @see RuntimeException#Exception(String)
     */
    public ArchitectureException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException#Exception(String, Throwable)
     */
    public ArchitectureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see RuntimeException#Exception(Throwable)
     */
    public ArchitectureException(Throwable cause) {
        super(cause);
    }
}