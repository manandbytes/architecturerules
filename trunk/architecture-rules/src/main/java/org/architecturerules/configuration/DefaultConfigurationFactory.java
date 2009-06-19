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


import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.discovery.tools.Service;

import org.architecturerules.configuration.xml.DigesterConfigurationFactory;
import org.architecturerules.exceptions.ArchitectureException;


public class DefaultConfigurationFactory {

    /**
     * Set of illegal file names
     */
    public static final Set<String> ILLEGAL_FILE_NAMES = new HashSet<String>() {

        {

            add(null);
            add("");
            add(".");
        }
    };

    /**
     * @param fileName
     *            name of the configuration file
     * @return configuration factory which is able to process specified
     *         configuration file. As a fallback and for a backward
     *         compatibility returns {@link DigesterConfigurationFactory} if
     *         there is no factory to handle configuration file.
     * @throws IllegalArgumentException
     *             if <code>fileName</code> is illegal
     * @see #ILLEGAL_FILE_NAMES
     */
    public static final AbstractConfigurationFactory createInstance(final String fileName) {

        if (ILLEGAL_FILE_NAMES.contains(fileName)) {

            throw new IllegalArgumentException(fileName);
        }

        Enumeration<AbstractConfigurationFactory> providers = Service.providers(AbstractConfigurationFactory.class);

        while (providers.hasMoreElements()) {

            AbstractConfigurationFactory configurationFactory = providers.nextElement();

            if (configurationFactory.acceptsFileType(getFileExtension(fileName))) {

                try {

                    // we need a configuration created using constructor with file name
                    final Constructor<?extends AbstractConfigurationFactory> constructor = configurationFactory.getClass().getConstructor(String.class);

                    return constructor.newInstance(fileName);
                } catch (Exception e) {

                    // just wrap a bunch of different exceptions into a single
                    throw new ArchitectureException(e);
                }
            }
        }

        return new DigesterConfigurationFactory(fileName);
    }


    private static String getFileExtension(final String fileName) {

        return (fileName.lastIndexOf(".") == -1) ? "" : fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }
}
