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



/**
 * <p>RuntimeException that is thrown when a configuration is invalid. A configuration could be invalid for a number of
 * reasons but happens most regularly in the creation of <code>Rules</code>.</p>
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see ArchitectureException
 */
public class InvalidConfigurationException extends ArchitectureException {

    /**
     * @see RuntimeException#RuntimeException()
     */
    public InvalidConfigurationException() {
        super();
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public InvalidConfigurationException(final String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public InvalidConfigurationException(final Throwable cause) {
        super(cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public InvalidConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
