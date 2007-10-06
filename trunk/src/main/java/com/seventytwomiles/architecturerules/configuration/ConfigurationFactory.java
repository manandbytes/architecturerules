package com.seventytwomiles.architecturerules.configuration;


import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * <p>Reads the configuration file at {@link @configurationFileName} into
 * <code>Rules</code> and source paths.</p>
 *
 * @author mnereson
 */
public class ConfigurationFactory {


    /**
     * <p>log to log with</p>
     *
     * @parameter log Log
     */
    private final Log log = LogFactory.getLog(ConfigurationFactory.class);

    /**
     * <p>Set of Rules read from the configuration file</p>
     *
     * @parameter rules Set
     */
    private final Collection rules = new HashSet();

    /**
     * <p>Set of  <code>Source</code> read from configuration file</p>
     *
     * @parameter sources Set
     */
    private final Collection sources = new HashSet();

    /**
     * <p>Weather or not to throw exception when no packages are found for a
     * given path.</p>
     *
     * @parameter throwExceptionWhenNoPackages boolean
     */
    private boolean throwExceptionWhenNoPackages = false;

    /**
     * <p>Weather or not to check for cyclic dependencies.</p>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    private boolean doCyclicDependencyTest = true;


    /**
     * <p>TODO: javadocs </p>
     *
     * @param configurationFileName TODO: javadocs
     */
    public ConfigurationFactory(final String configurationFileName) {

        try {

            final String configurationXml;

            configurationXml = getConfigurationAsXml(configurationFileName);

            validateConfigruation(configurationXml);
            processConfiguration(configurationXml);

        } catch (final Exception e) {

            throw new RuntimeException(e);
        }
    }


    /**
     * <p>Process the xml configuration file. Read all the source directories
     * and Rules.</p>
     *
     * @param configuration String xml content to process
     * @throws IOException when input stream can not be opened or closed
     * @throws SAXException when configuration can not be parsed
     */
    private void processConfiguration(final String configuration) throws SAXException, IOException {

        /**
         *
         * TODO: luminary do rewrite this method
         *
         */

        // Read the response XML document
        final XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

        /**
         * There's a name conflict with java.net.ContentHandler
         * so we have to use the fully package qualified name.
         */
        final ContentHandler handler = new ConfigurationHandler();
        parser.setContentHandler(handler);

        final InputStream inputStream = new ByteArrayInputStream(configuration.getBytes());
        final InputSource source = new InputSource(inputStream);

        parser.parse(source);
        inputStream.close();

        doCyclicDependencyTest = ((ConfigurationHandler) handler).isDoCyclicDependencyTest();
        throwExceptionWhenNoPackages = ((ConfigurationHandler) handler).isThrowExceptionWhenNoPackages();
        sources.addAll(((ConfigurationHandler) handler).getSources());
        rules.addAll(((ConfigurationHandler) handler).getRules());
    }


    /**
     * <p>Validate the configuration file.</p>
     *
     * @param configuration String xml content to validate
     * @see "architecture-rules.dtd"
     */
    private void validateConfigruation(final String configuration) {

        /* TODO: write this*/
    }


    /**
     * <p>Read Xml configuration file to String.</p>
     *
     * @return String returns the contentsofo the configurationFile
     * @throws IOException if configuration file can not be loaded from
     * classpath
     */
    private String getConfigurationAsXml(final String configurationFileName) throws IOException {

        final ClassPathResource resource = new ClassPathResource(configurationFileName);
        return FileUtils.readFileToString(resource.getFile(), null);
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


