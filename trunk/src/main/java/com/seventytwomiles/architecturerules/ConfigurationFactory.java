package com.seventytwomiles.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
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
     * @throws IOException when input stream can not be opened or closed
     * @throws SAXException when configuration can not be parsed
     */
    private static void processConfiguration(final String configuration) throws SAXException, IOException {

        // Read the response XML document
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

        /**
         * There's a name conflict with java.net.ContentHandler
         * so we have to use the fully package qualified name.
         */
        org.xml.sax.ContentHandler handler = new ArchitectureRulesConfigurationHandler();
        parser.setContentHandler(handler);

        InputStream inputStream = new ByteArrayInputStream(configuration.getBytes());
        InputSource source = new InputSource(inputStream);

        parser.parse(source);
        inputStream.close();

        doCyclicDependencyTest = ((ArchitectureRulesConfigurationHandler) handler).isDoCyclicDependencyTest();
        throwExceptionWhenNoPackages = ((ArchitectureRulesConfigurationHandler) handler).isThrowExceptionWhenNoPackages();
        sources.addAll(((ArchitectureRulesConfigurationHandler) handler).getSources());
        rules.addAll(((ArchitectureRulesConfigurationHandler) handler).getRules());
    }


    /**
     * @param configuration String xml content to validate
     * @see "architecture-rules.dtd"
     */
    private static void validateConfigruation(final String configuration) {

    }


    /**
     * @return String returns the contents fo the configurationFile
     * @throws IOException if configuraiton file can not be loaded from
     * classpath
     */
    private static String getConfigurationAsXml() throws IOException {

        ClassPathResource resource = new ClassPathResource(configurationFileName);
        return FileUtils.readFileToString(resource.getFile(), null);
    }


    /**
     * @return List of String[]{source, boolean }. The first element in the
     *         array is the source, the second is a boolean true or false and
     *         indicates how to handle the situation when the source is empty or
     *         does not exist.
     */
    public static List getSources() {
        return sources;
    }


    /**
     * @return Map
     */
    public static Set getRules() {
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


class FileUtils {


    /**
     * The default buffer size to use.
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;


    /**
     * <p> Reads the contents of a file into a String. </p> <p> There is no
     * readFileToString method without encoding parameter because the default
     * encoding can differ between platforms and therefore results in
     * inconsistent results. </p>
     *
     * @param file the file to read
     * @param encoding the encoding to use, null means platform default
     * @return The file contents or null if read failed.
     * @throws IOException in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by
     * the VM
     */
    public static String readFileToString(final File file, final String encoding) throws IOException {

        final InputStream inputStream = new FileInputStream(file);

        try {

            return toString(inputStream, encoding);

        } finally {

            closeQuietly(inputStream);
        }
    }


    /**
     * Get the contents of an <code>InputStream</code> as a String using the
     * specified character encoding. <p> Character encoding names can be found
     * at <a href="http://www.iana.org/assignments/character-sets">IANA</a>. <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     *
     * @param input the <code>InputStream</code> to read from
     * @param encoding the encoding to use, null means platform default
     * @return the requested String
     * @throws NullPointerException if the input is null
     * @throws IOException if an I/O error occurs
     */
    public static String toString(final InputStream input, final String encoding) throws IOException {

        final StringWriter stringWriter = new StringWriter();

        copy(input, stringWriter, encoding);

        return stringWriter.toString();
    }


    /**
     * Copy bytes from an <code>InputStream</code> to chars on a
     * <code>Writer</code> using the default character encoding of the platform.
     * <p> This method buffers the input internally, so there is no need to use
     * a <code>BufferedInputStream</code>. <p> This method uses {@link
     * InputStreamReader}.
     *
     * @param input the <code>InputStream</code> to read from
     * @param output the <code>Writer</code> to write to
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static void copy(final InputStream input, final Writer output) throws IOException {

        final InputStreamReader inputStreamReader = new InputStreamReader(input);
        copy(inputStreamReader, output);
    }


    /**
     * Copy bytes from an <code>InputStream</code> to chars on a
     * <code>Writer</code> using the specified character encoding. <p> This
     * method buffers the inputStream internally, so there is no need to use a
     * <code>BufferedInputStream</code>. <p> Character encoding names can be
     * found at <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
     * <p> This method uses {@link InputStreamReader}.
     *
     * @param inputStream the <code>InputStream</code> to read from
     * @param outputStream the <code>Writer</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException if the inputStream or outputStream is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static void copy(final InputStream inputStream, final Writer outputStream, final String encoding) throws IOException {

        if (encoding == null) {

            copy(inputStream, outputStream);

        } else {

            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encoding);
            copy(inputStreamReader, outputStream);
        }
    }


    /**
     * Copy chars from a <code>Reader</code> to a <code>Writer</code>. <p> This
     * method buffers the input internally, so there is no need to use a
     * <code>BufferedReader</code>.
     *
     * @param input the <code>Reader</code> to read from
     * @param output the <code>Writer</code> to write to
     * @return the number of characters copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static int copy(Reader input, Writer output) throws IOException {

        final char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        int count = 0;
        int n;

        while (-1 != (n = input.read(buffer))) {

            output.write(buffer, 0, n);
            count += n;
        }

        return count;
    }


    /**
     * Copy chars from a <code>Reader</code> to bytes on an
     * <code>OutputStream</code> using the default character encoding of the
     * platform, and calling flush. <p> This method buffers the input
     * internally, so there is no need to use a <code>BufferedReader</code>. <p>
     * Due to the implementation of OutputStreamWriter, this method performs a
     * flush. <p> This method uses {@link OutputStreamWriter}.
     *
     * @param input the <code>Reader</code> to read from
     * @param output the <code>OutputStream</code> to write to
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static void copy(final Reader input, final OutputStream output) throws IOException {

        final OutputStreamWriter out = new OutputStreamWriter(output);

        copy(input, out);
        out.flush();
    }


    /**
     * Unconditionally close an <code>InputStream</code>. <p> Equivalent to
     * {@link InputStream#close()}, except any exceptions will be ignored. This
     * is typically used in finally blocks.
     *
     * @param input the InputStream to close, may be null or already closed
     */
    public static void closeQuietly(final InputStream input) {

        try {

            if (input != null)
                input.close();

        } catch (IOException ioe) {

            // ignore
        }
    }
}
