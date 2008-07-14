/**
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com/svn/docs/index.html
 */
package org.seventytwomiles.springframework.io;

import java.io.*;

/**
 * <p>FileUtils utility class extracted from the Spring Framework in order to
 * remove the dependency on Spring for this one class.  <a
 * href="http://code.google.com/p/architecturerules/issues/detail?id=2&can=1">
 * issue 2 (remove unnecessary dependencies)</a></p>
 *
 * @author <a href="mailto:burton@relativity.yi.org">Kevin A. Burton</A>
 * @author <a href="mailto:sanders@apache.org">Scott Sanders</a>
 * @author <a href="mailto:dlr@finemaltcoding.com">Daniel Rall</a>
 * @author <a href="mailto:Christoph.Reck@dlr.de">Christoph.Reck</a>
 * @author <a href="mailto:peter@apache.org">Peter Donald</a>
 * @author <a href="mailto:jefft@apache.org">Jeff Turner</a>
 * @author Matthew Hawthorne
 * @author <a href="mailto:jeremias@apache.org">Jeremias Maerki</a>
 * @author Stephen Colebourne
 * @author Ian Springer
 * @author Chris Eldredge
 * @author Jim Harrington
 * @author Niall Pemberton
 * @author Sandy McArthur
 */
public class FileUtils
{
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
    public static String readFileToString( final File file, final String encoding )
                                   throws IOException
    {
        final InputStream inputStream = new FileInputStream( file );

        try
        {
            return toString( inputStream, encoding );
        } finally
        {
            closeQuietly( inputStream );
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
    public static String toString( final InputStream input, final String encoding )
                           throws IOException
    {
        final StringWriter stringWriter = new StringWriter(  );

        copy( input, stringWriter, encoding );

        return stringWriter.toString(  );
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
    public static void copy( final InputStream input, final Writer output )
                     throws IOException
    {
        final InputStreamReader inputStreamReader = new InputStreamReader( input );
        copy( inputStreamReader, output );
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
    public static void copy( final InputStream inputStream, final Writer outputStream, final String encoding )
                     throws IOException
    {
        if ( encoding == null )
        {
            copy( inputStream, outputStream );
        } else
        {
            final InputStreamReader inputStreamReader = new InputStreamReader( inputStream, encoding );
            copy( inputStreamReader, outputStream );
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
    public static int copy( final Reader input, final Writer output )
                    throws IOException
    {
        final char[] buffer = new char[DEFAULT_BUFFER_SIZE];

        int count = 0;
        int n;

        while ( -1 != ( n = input.read( buffer ) ) )
        {
            output.write( buffer, 0, n );
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
    public static void copy( final Reader input, final OutputStream output )
                     throws IOException
    {
        final OutputStreamWriter out = new OutputStreamWriter( output );

        copy( input, out );
        out.flush(  );
    }

    /**
     * <code>Unconditionally close an <code>InputStream</code>. <p> Equivalent
     * to {@link InputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.</code>
     *
     * @param input the InputStream to close, may be null or already closed
     */
    public static void closeQuietly( final InputStream input )
    {
        try
        {
            if ( input != null )
            {
                input.close(  );
            }
        } catch ( IOException ioe )
        {
            // ignore
        }
    }
}
