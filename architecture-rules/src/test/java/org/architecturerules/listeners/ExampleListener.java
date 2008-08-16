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
package org.architecturerules.listeners;


import org.architecturerules.api.listeners.Listener;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see EmptyListener
 * @see Listener
 */
public class ExampleListener extends EmptyListener {

    private final String name = this.getClass().getSimpleName();

    @Override
    public void register() {

        System.out.println("registering " + name);
    }


    @Override
    public void terminate() {

        System.out.println("terminate " + name);
    }
}
