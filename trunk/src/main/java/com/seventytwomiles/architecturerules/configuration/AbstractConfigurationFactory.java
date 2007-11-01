package com.seventytwomiles.architecturerules.configuration;


import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see ConfigurationFactory
 */
public abstract class AbstractConfigurationFactory implements ConfigurationFactory {


    /**
     * <p>Set of Rules read from the configuration file</p>
     *
     * @parameter rules Set
     */
    protected final Collection rules = new HashSet();

    /**
     * <p>Set of  <code>Source</code> read from configuration file</p>
     *
     * @parameter sources Set
     */
    protected final Collection sources = new HashSet();

    /**
     * <p>Weather or not to throw exception when no packages are found for a
     * given path.</p>
     *
     * @parameter throwExceptionWhenNoPackages boolean
     */
    protected boolean throwExceptionWhenNoPackages = false;

    /**
     * <p>Weather or not to check for cyclic dependencies.</p>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    protected boolean doCyclicDependencyTest = true;


    /**
     * <p>Validate the configuration.</p>
     *
     * @param configuration String xml content to validate
     * @see "architecture-rules.dtd"
     */
    protected void validateConfigruation(final String configuration) {
        /* todo: validate configuraiton */
    }


    /**
     * <p>Read Xml configuration file to String.</p>
     *
     * @param configurationFileName String name of the XML file in teh classpath
     * to load and read
     * @return String returns the contentsofo the configurationFile
     * @throws IOException if configuration file can not be loaded from
     * classpath
     */
    protected String getConfigurationAsXml(final String configurationFileName) throws IOException {

        final ClassPathResource resource = new ClassPathResource(configurationFileName);

        if (!resource.exists())
            throw new IllegalArgumentException("could not load resource "
                                               + configurationFileName
                                               + " from classpath. File not found.");

        final String xml = FileUtils.readFileToString(resource.getFile(), null);

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
