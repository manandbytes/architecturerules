/**
 * Copyright 2007 - 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://wiki.architecturerules.org/ and
 *         http://blog.architecturerules.org/
 */
package org.architecturerules.listeners;


import java.util.Properties;

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
    public void registerListener(final Properties properties) {

        System.out.println("** this Listener is for testing ** #registerListener " + name);
    }


    @Override
    public void terminateListener() {

        System.out.println("** this Listener is for testing ** #terminateListener " + name);
    }
}
