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

import org.junit.Before;
import org.junit.Test;


public class LoggerListenerTest {

    private LoggerListener listenerToTest;

    @Test(expected = NullPointerException.class)
    public void testRegisterWithNullProperties() {

        listenerToTest.registerListener(null);
    }


    @Test
    public void testRegisterWithEmptyProperties() {

        listenerToTest.registerListener(new Properties());
    }


    @Test
    public void testRegisterWithNonEmptyProperties() {

        final Properties properties = new Properties();
        properties.put("abc", "123");
        listenerToTest.registerListener(properties);
    }


    @Before
    public void setUp() {

        listenerToTest = new LoggerListener();
    }
}
