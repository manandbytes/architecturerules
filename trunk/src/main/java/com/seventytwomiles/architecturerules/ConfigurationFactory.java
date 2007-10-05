package com.seventytwomiles.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.ContentHandler;
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
 * <p>Reads the configuration file at {@link @configurationFileName} into
 * <code>Rules</code> and source paths.</p>
 *
 * @author mnereson
 */
class ConfigurationFactory {


    /**
     * <p>log to log with</p>
     *
     * @parameter log Log
     */
    private static final Log log = LogFactory.getLog(ConfigurationFactory.class);

    /**
     * <p>Name of the configuration file.</p>
     *
     * @parameter configurationFileName String
     */
    private static final String configurationFileName = "architecture-rules.xml";

    /**
     * <p>Set of Rules read from the configuraiton file at {@link
     * #configurationFileName}</p>
     *
     * @parameter rules Set
     */
    private static final Set rules = new HashSet(); // of Rules

    /**
     * <p>List of source paths read from configuraiton file at  {@link
     * #configurationFileName}</p>
     *
     * @parameter sources List
     */
    private static final List sources = new ArrayList(); // String[] sourcename, booleanvalue
    /**
     * <p>Weather or not to throw exception when no packages are found for a
     * given path.</p>
     *
     * @parameter throwExceptionWhenNoPackages boolean
     */
    private static boolean throwExceptionWhenNoPackages = false;

    /**
     * <p>Wehather or not to check for cyclic dependencies.</p>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    private static boolean doCyclicDependencyTest = true;

    /**
     * <p>Starts by reading the configuraiton file, then validates the content, and finally processess the
     * information.</p>
     *
     * @throws RuntimeException when configuration file can not be found, or when configuraiton
     * is not valid
     */
    static {

        try {

            final String configuration = getConfigurationAsXml();

            validateConfigruation(configuration);
            processConfiguration(configuration);

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
    private static void processConfiguration(final String configuration) throws SAXException, IOException {

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
    private static void validateConfigruation(final String configuration) {

        /* TODO: write this*/
    }


    /**
     * <p>Read Xml configuration file name {@link #configurationFileName} as
     * String</p>
     *
     * @return String returns the contents fo the configurationFile
     * @throws IOException if configuration file can not be loaded from
     * classpath
     */
    private static String getConfigurationAsXml() throws IOException {

        ClassPathResource resource = new ClassPathResource(configurationFileName);
        return FileUtils.readFileToString(resource.getFile(), null);
    }


    /**
     * <p>Getter for property {@link #sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    public static List getSources() {
        return sources;
    }


    /**
     * <p>Getter for property {@link #rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    public static Set getRules() {
        return rules;
    }


    /**
     * @return boolean <tt>true</tt> when <samp>&lt;sources
     *         no-packages="exception"> </samp>
     */
    public static boolean throwExceptionWhenNoPackages() {
        return throwExceptionWhenNoPackages;
    }


    /**
     * @return boolean <tt>true</tt> when <samp>&lt;cyclicalDependency
     *         test="true"/> </samp>
     */
    public static boolean doCyclicDependencyTest() {
        return doCyclicDependencyTest;
    }

}


/**
 * <p>FileUtils utility class extracted from the Spring Framework in order to
 * remove the dependency on Spring for this one class.  <a
 * href="http://code.google.com/p/architecturerules/issues/detail?id=2&can=1">
 * issue 2 (remove unneccessary dependencies)</a></p>
 *
 * @author unknown
 */
class FileUtils {


    /**
     * <p>The default buffer size to use.</p>
     *
     * @parameter DEFAULT_BUFFER_SIZE int
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;


    /**
     * <p> Reads the contents of a file into a String. There is no
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
     * <p>Get the contents of an <code>InputStream</code> as a String using the
     * specified character encoding. <p> Character encoding names can be found
     * at <a href="http://www.iana.org/assignments/character-sets">IANA</a>. <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.</p>
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
     * <p>Copy bytes from an <code>InputStream</code> to chars on a
     * <code>Writer</code> using the default character encoding of the platform.
     * <p> This method buffers the input internally, so there is no need to use
     * a <code>BufferedInputStream</code>. <p> This method uses {@link
     * InputStreamReader}.</p>
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
     * <p>Copy bytes from an <code>InputStream</code> to chars on a
     * <code>Writer</code> using the specified character encoding. <p> This
     * method buffers the inputStream internally, so there is no need to use a
     * <code>BufferedInputStream</code>. <p> Character encoding names can be
     * found at <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
     * <p> This method uses {@link InputStreamReader}.</p>
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
     * <p>Copy chars from a <code>Reader</code> to a <code>Writer</code>. <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedReader</code>.</p>
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
     * <code>Copy chars from a <code>Reader</code> to bytes on an
     * <code>OutputStream</code> using the default character encoding of the
     * platform, and calling flush. <p> This method buffers the input
     * internally, so there is no need to use a <code>BufferedReader</code>. <p>
     * Due to the implementation of OutputStreamWriter, this method performs a
     * flush. <p> This method uses {@link OutputStreamWriter}.</code>
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
     * <code>Unconditionally close an <code>InputStream</code>. <p> Equivalent
     * to {@link InputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.</code>
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
