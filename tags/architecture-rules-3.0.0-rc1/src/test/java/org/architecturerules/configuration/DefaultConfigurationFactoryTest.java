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
package org.architecturerules.configuration;


import junit.framework.TestCase;


public class DefaultConfigurationFactoryTest extends TestCase {

    public void testNullFileName() {

        try {

            DefaultConfigurationFactory.createInstance(null);
            fail("null file name is illegal");
        } catch (final IllegalArgumentException e) {

        }
    }


    public void testEmptyFileName() {

        try {

            DefaultConfigurationFactory.createInstance("");
            fail("empty file name is illegal");
        } catch (final IllegalArgumentException e) {

        }
    }


    public void testDotOnly() {

        try {

            final String fileName = ".";
            DefaultConfigurationFactory.createInstance(fileName);
            fail("'" + fileName + "'" + "is illegal");
        } catch (final IllegalArgumentException e) {

        }
    }
}
