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
 *         http://architecturerules.googlecode.com/
 */
package com.seventytwomiles.architecturerules.configuration;


import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

import org.seventytwomiles.springframework.core.io.ClassPathResource;


/**
 * AbstractConfigurationFactory Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/27/2008</pre>
 */
public class AbstractConfigurationFactoryTest extends TestCase {

    private AbstractConfigurationFactory factory;

    public AbstractConfigurationFactoryTest(final String name) {
        super(name);
    }

    @Override
    public void setUp()
            throws Exception {

        super.setUp();

        factory = new AbstractConfigurationFactory() {

                    @Override
                    protected void validateConfiguration(final String configuration) {

                        /* do nothing*/
                    }
                };
    }


    @Override
    public void tearDown()
            throws Exception {

        factory = null;

        super.tearDown();
    }


    public void testGetConfigurationAsXml_classPathResource() {

        final String xml;

        xml = factory.getConfigurationAsXml("architecture-rules.xml");
        assertTrue(xml.length() > 0);

        try {

            factory.getConfigurationAsXml("some-file-that-does-not-exist.xml");
            fail("expected IllegalArgumentException because input file does not exist");
        } catch (IllegalArgumentException e) {

            /* success */
        }
    }


    public void testGetConfigurationAsXml_absolutePath() {

        final ClassLoader classLoader = getClass().getClassLoader();

        final ClassPathResource resource = new ClassPathResource("architecture-rules.xml", classLoader);

        try {

            final String filepath = resource.getFile().getAbsolutePath();
            factory.getConfigurationAsXml(filepath);
        } catch (IOException e) {

            fail("could not locate architecture-rules.xml which was expected to exist");
        }

        try {

            final File file = new File("some-file-that-does-not-exist.xml");
            final String filepath = file.getAbsolutePath();
            factory.getConfigurationAsXml(filepath);

            fail("expected IllegalArgumentException because input file does not exist");
        } catch (IllegalArgumentException e) {

            /* success */
        }
    }
}
