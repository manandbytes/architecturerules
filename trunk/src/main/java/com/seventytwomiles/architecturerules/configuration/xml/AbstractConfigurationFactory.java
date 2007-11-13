package com.seventytwomiles.architecturerules.configuration.xml;


import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;


/**
 * <p>Abstract Factory that provides common functionality for
 * <code>ConfigurationFactory</code> implementations.</p>
 *
 * @author mnereson
 * @see ConfigurationFactory
 */
public abstract class AbstractConfigurationFactory implements ConfigurationFactory {


    private static final Log log = LogFactory.getLog(AbstractConfigurationFactory.class);

    /**
     * <p>Set of Rules read from the configuration file</p>
     *
     * @parameter rules Set
     */
    final Collection rules = new HashSet();

    /**
     * <p>Set of  <code>Source</code> read from configuration file</p>
     *
     * @parameter sources Set
     */
    final Collection sources = new HashSet();

    /**
     * <p>Weather or not to throw exception when no packages are found for a
     * given path.</p>
     *
     * @parameter throwExceptionWhenNoPackages boolean
     */
    boolean throwExceptionWhenNoPackages = false;

    /**
     * <p>Weather or not to check for cyclic dependencies.</p>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    boolean doCyclicDependencyTest = true;


    /**
     * <p>Validate the configuration.</p>
     *
     * @param configuration String xml content to validate
     * @see "architecture-rules.dtd"
     */
    abstract void validateConfigruation(final String configuration);


    /**
     * <p>Read Xml configuration file to String.</p>
     *
     * @param configurationFileName String name of the XML file in teh classpath
     * to load and read
     * @return String returns the contentsofo the configurationFile
     */
    String getConfigurationAsXml(final String configurationFileName) {

        /**
         * This code kinda sucks. First, an exeption is thrown if the resources
         * does not exist, then an exception could be thrown if the resource
         * could not be read.
         */
        final ClassLoader classLoader = getClass().getClassLoader();
        final ClassPathResource resource = new ClassPathResource(configurationFileName, classLoader);

        if (!resource.exists())
            throw new IllegalArgumentException("could not load resource "
                                               + configurationFileName
                                               + " from classpath. File not found.");

        final String xml;

        try {

            xml = FileUtils.readFileToString(resource.getFile(), null);

        } catch (final IOException e) {

            throw new IllegalArgumentException("could not load resource "
                                               + configurationFileName
                                               + " from classpath. File not found.", e);
        }

        return xml;
    }


    /**
     * <p>Getter for property {@link #sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    public Collection getSources() {
        return sources;
    }


    /**
     * <p>Getter for property {@link #rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    public Collection getRules() {
        return rules;
    }


    /**
     * @return boolean <tt>true</tt> when <samp>&lt;sources
     *         no-packages="exception"> </samp>
     */
    public boolean throwExceptionWhenNoPackages() {
        return throwExceptionWhenNoPackages;
    }


    /**
     * @return boolean <tt>true</tt> when <samp>&lt;cyclicalDependency
     *         test="true"/> </samp>
     */
    public boolean doCyclicDependencyTest() {
        return doCyclicDependencyTest;
    }
}
