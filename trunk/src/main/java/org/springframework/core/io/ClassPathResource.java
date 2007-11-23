package org.springframework.core.io;

/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more infomration visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;


/**
 * <p>These classes are all extracted from the Spring Framework in order to
 * remove the dependency on the Spring library.</p>
 *
 * @author mikenereson
 * @noinspection SimplifiableIfStatement
 */
public class ClassPathResource {


    /**
     * <p>symbol that separates folders</p>
     *
     * @parameter FOLDER_SEPARATOR String
     */
    private static final String FOLDER_SEPARATOR = "/";

    /**
     * <p>symbol that separates folders on the Windows platform</p>
     *
     * @parameter WINDOWS_FOLDER_SEPARATOR String
     */
    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

    /**
     * <p>Parent directory path</p>
     *
     * @parameter TOP_PATH String
     */
    private static final String TOP_PATH = "..";

    /**
     * <p>Current path</p>
     *
     * @parameter CURRENT_PATH String
     */
    private static final String CURRENT_PATH = ".";

    /**
     * <p>the path that you are in</p>
     *
     * @parameter path String
     */
    private final String path;

    /**
     * <p>instance of ClassLoader</p>
     *
     * @parameter classLoader String
     */
    private ClassLoader classLoader;

    /**
     * <p>instance of Class</p>
     *
     * @parameter clazz String
     */
    private Class clazz;


    /**
     * <p>Create a new <code>ClassPathResource</code> for ClassLoader usage. A
     * leading slash will be removed, as the ClassLoader resource access methods
     * will not accept it. The thread context class loader will be used for
     * loading the resource.</p>
     *
     * @param path the absolute path within the class path
     * @see ClassLoader#getResourceAsStream(String)
     */
    public ClassPathResource(final String path) {
        this(path, (ClassLoader) null);
    }


    /**
     * <p>Create a new <code>ClassPathResource</code> for ClassLoader usage. A
     * leading slash will be removed, as the ClassLoader resource access methods
     * will not accept it.</p>
     *
     * @param path the absolute path within the classpath
     * @param classLoader the class loader to load the resource with, or
     * <code>null</code> for the thread context class loader
     * @see ClassLoader#getResourceAsStream(String)
     */
    public ClassPathResource(String path, final ClassLoader classLoader) {

        if (null == path || "".equals(path))
            throw new IllegalArgumentException("path can not be empty or null");

        if (path.startsWith("/"))
            path = path.substring(1);

        this.path = cleanPath(path);
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }


    /**
     * <p>Create a new <code>ClassPathResource</code> for Class usage. The path
     * can be relative to the given class, or absolute within the classpath via
     * a leading slash.</p>
     *
     * @param path relative or absolute path within the class path
     * @param clazz the class to load resources with
     * @see Class#getResourceAsStream
     */
    public ClassPathResource(final String path, final Class clazz) {

        if (null == path || "".equals(path))
            throw new IllegalArgumentException("path can not be empty or null");

        this.path = cleanPath(path);
        this.clazz = clazz;
    }


    /**
     * <p>Create a new <code>ClassPathResource</code> with optional ClassLoader
     * and Class. Only for internal usage.</p>
     *
     * @param path relative or absolute path within the classpath
     * @param classLoader the class loader to load the resource with, if any
     * @param clazz the class to load resources with, if any
     */
    protected ClassPathResource(final String path, final ClassLoader classLoader, final Class clazz) {
        this.path = path;
        this.classLoader = classLoader;
        this.clazz = clazz;
    }


    /**
     * <p>This implementation returns a description that includes the class path
     * location.</p>
     *
     * @return String description of File
     */
    private String getDescription() {
        return "class path resource [" + this.path + "]";
    }


    /**
     * <p>This implementation returns a File reference for the underlying class
     * path resource, provided that it refers to a file in the file system.</p>
     *
     * @return File
     * @throws IOException when file not found
     * @noinspection RedundantThrows
     */
    public File getFile() throws IOException {
        return ResourceUtils.getFile(getURL(), getDescription());
    }


    /**
     * <p>This implementation returns a URL for the underlying class path
     * resource.</p>
     *
     * @return URL
     * @throws FileNotFoundException when file not found
     * @see ClassLoader#getResource(String)
     * @see Class#getResource(String)
     */
    private URL getURL() throws FileNotFoundException {

        URL url = clazz != null ? clazz.getResource(path) : classLoader.getResource(path);

        if (url == null)
            throw new FileNotFoundException(getDescription() + " cannot be resolved to URL because it does not exist");

        return url;
    }


    /**
     * <p>This implementation compares the underlying class path locations.</p>
     */
    public boolean equals(final Object object) {

        if (object == this)
            return true;

        if (!(object instanceof ClassPathResource))
            return false;

        ClassPathResource that = (ClassPathResource) object;

        return (this.path.equals(that.path) &&
                nullSafeEquals(this.classLoader, that.classLoader) &&
                nullSafeEquals(this.clazz, that.clazz));
    }


    /**
     * <p>Determine if the given objects are equal, returning <code>true</code>
     * if both are <code>null</code> or <code>false</code> if only one is
     * <code>null</code>. <p>Compares arrays with <code>Arrays.equals</code>,
     * performing an equality check based on the array elements rather than the
     * array reference.</p>
     *
     * @param object1 first Object to compare
     * @param object2 second Object to compare
     * @return whether the given objects are equal
     * @see java.util.Arrays#equals
     */
    private boolean nullSafeEquals(final Object object1, final Object object2) {

        if (object1 == object2)
            return true;

        if (object1 == null || object2 == null)
            return false;

        if (object1.equals(object2))
            return true;

        if (object1 instanceof Object[] && object2 instanceof Object[])
            return Arrays.equals((Object[]) object1, (Object[]) object2);

        if (object1 instanceof boolean[] && object2 instanceof boolean[])
            return Arrays.equals((boolean[]) object1, (boolean[]) object2);

        if (object1 instanceof byte[] && object2 instanceof byte[])
            return Arrays.equals((byte[]) object1, (byte[]) object2);

        if (object1 instanceof char[] && object2 instanceof char[])
            return Arrays.equals((char[]) object1, (char[]) object2);

        if (object1 instanceof double[] && object2 instanceof double[])
            return Arrays.equals((double[]) object1, (double[]) object2);

        if (object1 instanceof float[] && object2 instanceof float[])
            return Arrays.equals((float[]) object1, (float[]) object2);

        if (object1 instanceof int[] && object2 instanceof int[])
            return Arrays.equals((int[]) object1, (int[]) object2);

        if (object1 instanceof long[] && object2 instanceof long[])
            return Arrays.equals((long[]) object1, (long[]) object2);

        if (object1 instanceof short[] && object2 instanceof short[])
            return Arrays.equals((short[]) object1, (short[]) object2);

        return false;
    }


    /**
     * <p>This implementation checks whether a File can be opened, falling back
     * to whether an InputStream can be opened. This will cover both directories
     * and content resources.</p>
     *
     * @return boolean
     */
    public boolean exists() {

        // Try file existence: can we find the file in the file system?
        try {

            return getFile().exists();

        } catch (final IOException ex) {

            // Fall back to stream existence: can we open the stream?
            try {

                final InputStream inputStream = getInputStream();
                inputStream.close();
                return true;

            } catch (final Throwable throwable) {

                return false;
            }
        }
    }


    /**
     * <p>This implementation opens an InputStream for the given class path
     * resource.</p>
     *
     * @return InputStream
     * @throws FileNotFoundException when file not found
     * @see ClassLoader#getResourceAsStream(String)
     * @see Class#getResourceAsStream(String)
     */
    private InputStream getInputStream() throws FileNotFoundException {


        final InputStream inputStream = clazz != null ? clazz.getResourceAsStream(path) : classLoader.getResourceAsStream(path);

        if (inputStream == null)
            throw new FileNotFoundException(getDescription() + " cannot be opened because it does not exist");

        return inputStream;
    }


    /**
     * <p>Normalize the path by suppressing sequences like "path/.." and inner
     * simple dots. <p>The result is convenient for path comparison. For other
     * uses, notice that Windows separators ("\") are replaced by simple
     * slashes.</p>
     *
     * @param path the original path
     * @return the normalized path
     */
    private String cleanPath(final String path) {

        String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);

        // Strip prefix from path to analyze, to not treat it as part of the
        // first path element. This is necessary to correctly parse paths like
        // "file:core/../core/io/Resource.class", where the ".." should just
        // strip the first "core" directory while keeping the "file:" prefix.
        int prefixIndex = pathToUse.indexOf(":");
        String prefix = "";

        if (prefixIndex != -1) {

            prefix = pathToUse.substring(0, prefixIndex + 1);
            pathToUse = pathToUse.substring(prefixIndex + 1);
        }

        String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);

        final List pathElements = new LinkedList();
        int tops = 0;

        for (int i = pathArray.length - 1; i >= 0; i--) {

            if (CURRENT_PATH.equals(pathArray[i])) {

                // Points to current directory - drop it.

            } else if (TOP_PATH.equals(pathArray[i])) {

                // Registering top path found.
                tops++;

            } else {

                if (tops > 0) {

                    // Merging path element with corresponding to top path.
                    tops--;

                } else {

                    // Normal path element found.
                    pathElements.add(0, pathArray[i]);
                }
            }
        }

        // Remaining top paths need to be retained.
        for (int i = 0; i < tops; i++)
            pathElements.add(0, TOP_PATH);

        return prefix + collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
    }


    /**
     * <p>Replace all occurences of a substring within a string with another
     * string.</p>
     *
     * @param inString String to examine
     * @param oldPattern String to replace
     * @param newPattern String to insert
     * @return a String with the replacements
     */
    private static String replace(final String inString, final String oldPattern, final String newPattern) {

        if (inString == null)
            return null;

        if (oldPattern == null || newPattern == null)
            return inString;

        final StringBuffer stringBuffer = new StringBuffer();

        // output StringBuffer we'll build up
        int pos = 0; // our position in the old string
        int index = inString.indexOf(oldPattern);

        // the index of an occurrence we've found, or -1
        final int patLen = oldPattern.length();

        while (index >= 0) {

            stringBuffer.append(inString.substring(pos, index));
            stringBuffer.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }

        stringBuffer.append(inString.substring(pos));

        // remember to append any characters to the right of a match
        return stringBuffer.toString();
    }


    /**
     * <p>Convenience method to return a Collection as a delimited (e.g. CSV)
     * String. E.g. useful for <code>toString()</code> implementations.</p>
     *
     * @param collection the Collection to display
     * @param deliminator the delimiter to use (probably a ",")
     * @param prefix the String to start each element with
     * @param suffix the String to end each element with
     * @return String
     */
    private static String collectionToDelimitedString(final Collection collection, final String deliminator, final String prefix, final String suffix) {

        if (collection.isEmpty())
            return "";

        final StringBuffer stringBuffer = new StringBuffer();
        final Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            stringBuffer.append(prefix).append(iterator.next()).append(suffix);

            if (iterator.hasNext())
                stringBuffer.append(deliminator);
        }

        return stringBuffer.toString();
    }


    /**
     * <p>Convenience method to return a Collection as a delimited (e.g. CSV)
     * String. E.g. useful for <code>toString()</code> implementations.</p>
     *
     * @param collection the Collection to display
     * @param delminator the delimiter to use (probably a ",")
     * @return String
     */
    private String collectionToDelimitedString(final Collection collection, final String delminator) {
        return collectionToDelimitedString(collection, delminator, "", "");
    }


    /**
     * Take a String which is a delimited list and convert it to a String array.
     * <p>A single delimiter can consists of more than one character: It will
     * still be considered as single delimiter string, rather than as bunch of
     * potential delimiter characters - in contrast to <code>tokenizeToStringArray</code>.
     *
     * @param string the input String
     * @param delimiter the delimiter between elements (this is a single
     * delimiter, rather than a bunch individual delimiter characters)
     * @return an array of the tokens in the list
     */
    private static String[] delimitedListToStringArray(final String string, final String delimiter) {

        if (string == null)
            return new String[0];

        if (delimiter == null)
            return new String[]{string};

        final List result = new ArrayList();

        if ("".equals(delimiter)) {

            for (int i = 0; i < string.length(); i++)
                result.add(string.substring(i, i + 1));

        } else {

            int position = 0;
            int deletePosition;

            while ((deletePosition = string.indexOf(delimiter, position)) != -1) {

                result.add(string.substring(position, deletePosition));
                position = deletePosition + delimiter.length();
            }

            // Add rest of String, but not in case of empty input.
            if (string.length() > 0 && position <= string.length())
                result.add(string.substring(position));
        }

        return toStringArray(result);
    }


    /**
     * <p>Copy the given Collection into a String array. The Collection must
     * contain String elements only.</p>
     *
     * @param collection the Collection to copy
     * @return the String array (<code>null</code> if the passed-in Collection
     *         was <code>null</code>)
     */
    private static String[] toStringArray(final Collection collection) {

        if (collection == null)
            return null;

        return (String[]) collection.toArray(new String[collection.size()]);
    }

}
