package com.nereson.architecturerules;


import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 */
class ConfigurationFactory {


    private static final Log log = LogFactory.getLog(ConfigurationFactory.class);

    private static final String configurationFileName = "architecture-rules.xml";

    private static Set rules = new HashSet(); // of Rules
    private static List sources = new ArrayList(); // String[] sourcename, booleanvalue
    private static boolean throwExceptionWhenNoPackages = false;
    private static boolean doCyclicDependencyTest = true;

    /**
     * Starts by reading the configuraiton file, then validates the content, and finally processess the
     * information.
     *
     * @throws RuntimeException when configuration file can not be found, or when configuraiton
     * is not valid
     */
    static {

        try {

            String configuration = getConfigurationAsXml();

            validateConfigruation(configuration);

            processConfiguration(configuration);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }


    /**
     * @param configuration String xml content to process
     * @throws IOException  when input stream can not be opened or closed
     * @throws SAXException when configuration can not be parsed
     */
    private static void processConfiguration(final String configuration) throws SAXException, IOException {

        // Read the response XML document
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

        // There's a name conflict with java.net.ContentHandler
        // so we have to use the fully package qualified name.
        org.xml.sax.ContentHandler handler = new ArchitectureRulesConfigurationHandler(rules, sources, throwExceptionWhenNoPackages, doCyclicDependencyTest);
        parser.setContentHandler(handler);

        InputStream inputStream = new ByteArrayInputStream(configuration.getBytes());
        InputSource source = new InputSource(inputStream);

        parser.parse(source);

        inputStream.close();
    }


    /**
     * @param configuration String xml content to validate
     * @see "architecture-rules.dtd"
     */
    private static void validateConfigruation(final String configuration) {

    }


    /**
     * @return String returns the contents fo the configurationFile
     * @throws IOException if configuraiton file can not be loaded from classpath
     */
    private static String getConfigurationAsXml() throws IOException {

        ClassPathResource resource = new ClassPathResource(configurationFileName);
        return FileUtils.readFileToString(resource.getFile(), null);
    }


    /**
     * @return List of String[]{source, boolean }. The first element in the array is the source, the second
     *         is a boolean true or false and indicates how to handle the situation when the source is empty
     *         or does not exist.
     */
    public static List getSources() {

        return sources;
    }


    /**
     * @return Map
     */
    public static Set getRules() {

        /*Set rules = new HashSet();

        Rule daoRule = new Rule("dao", "com.nereson.pagerank.core.dao");
        daoRule.addViolation("com.nereson.pagerank.core.services");
        daoRule.addViolation("com.nereson.pagerank.core.builder");
        daoRule.addViolation("com.nereson.pagerank.util");
        daoRule.setComment("The dao interface package should rely on nothing");
        rules.add(daoRule);

        Rule modelRule = new Rule("model", "com.nereson.pagerank.core.model");
        modelRule.addViolation("com.nereson.pagerank.core.dao");
        modelRule.addViolation("com.nereson.pagerank.core.dao.hibernate");
        modelRule.addViolation("com.nereson.pagerank.core.services");
        modelRule.addViolation("com.nereson.pagerank.core.strategy");
        modelRule.addViolation("com.nereson.pagerank.core.builder");
        modelRule.addViolation("com.nereson.pagerank.util");
        modelRule.setComment("model should remain competely isolated");
        rules.add(modelRule);

        return rules;*/
        return rules;
    }


    /**
     * @return boolean
     */
    public static boolean throwExceptionWhenNoPackages() {
        return throwExceptionWhenNoPackages;
    }


    /**
     * @return boolean
     */
    public static boolean doCyclicDependencyTest() {
        return doCyclicDependencyTest;
    }

}
