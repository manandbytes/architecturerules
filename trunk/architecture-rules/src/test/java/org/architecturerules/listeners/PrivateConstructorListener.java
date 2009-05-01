/**
 * Copyright 2007, 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com/ and
 *         http://architecturerules.googlecode.com/
 */
package org.architecturerules.listeners;


import org.architecturerules.api.listeners.Listener;
import org.architecturerules.configuration.Configuration;


/**
 * /** <p>To test {@link Configuration#addListener(String)} <code>IllegalAccessException</code> handling</p>
 *
 * @author mnereson
 * @see Listener
 */
public class PrivateConstructorListener extends EmptyListener {

    private PrivateConstructorListener() {

    }
}
