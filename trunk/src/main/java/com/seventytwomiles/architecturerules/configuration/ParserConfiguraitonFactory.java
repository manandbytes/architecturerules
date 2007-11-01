package com.seventytwomiles.architecturerules.configuration;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * <p>Reads the configuration file at {@link @configurationFileName} into
 * <code>Rules</code> and source paths.</p>
 *
 * @author mnereson
 */
public class ParserConfiguraitonFactory extends AbstractConfigurationFactory {


    /**
     * <p>log to log with</p>
     *
     * @parameter log Log
     */
    private final Log log = LogFactory.getLog(ParserConfiguraitonFactory.class);


    /**
     * <p>TODO: javadocs </p>
     *
     * @param configurationFileName TODO: javadocs
     */
    public ParserConfiguraitonFactory(final String configurationFileName) {

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
         * TODO: instanciate new SourceDirectory rather than a String for each source
         * TODO: if configuration loading is small enough that it can fit into this file, delete ConfiguraitonHandler, which is the current xml parser that reads the xml file
         * TODO: if a new file is created, call it ConfigurationDigester ? 
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


}


