package com.seventytwomiles.architecturerules.domain;


import com.seventytwomiles.architecturerules.exceptions.SourceNotFoundException;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Representation of a source directory to search for packages and .class
 * files in.</p>
 *
 * @author mnereson
 */
public class SourceDirectory {


    private static final Log log = LogFactory.getLog(SourceDirectory.class);


    /**
     * <p>The value, which is set inside of the xml configuration file, which
     * indicates that when a source directory is not found, the source directory
     * should be ignored.</p>
     *
     * @parameter NOT_FOUND_IGNORE String
     */
    private final String NOT_FOUND_IGNORE = "ignore";

    /**
     * <p>The value, which is set inside of the xml configuration file, which
     * indicates that when a source directory is not found, that an exepction
     * should be thrown.</p>
     *
     * <p>When {@link #setNotFound(String)} is set to this value, a {@link
     * SourceNotFoundException} will be thrown.</p>
     *
     * @parameter NOT_FOUND_EXEPTION String
     */
    private final String NOT_FOUND_EXEPTION = "exception";


    /**
     * <p>When true, if this source {@link #path} is not found a
     * <code>SourceNotFoundException</code> will be thrown.</p>
     *
     * @parameter shouldThrowExceptionWhenNotFound boolean
     */
    private boolean shouldThrowExceptionWhenNotFound = false;

    /**
     * <p>Relative url to the path to search in for .class files.</p>
     *
     * @parameter path String
     */
    private String path;

    /**
     * <p>Holds the value in the xml configuraiton for the not-found
     * property.</p>
     *
     * @parameter notFound String
     * @noinspection UnusedDeclaration
     */
    private String notFound;


    /**
     * <p></p>
     */
    public SourceDirectory() {
        /* do nothing */
    }


    /**
     * <p></p>
     *
     * @param path String {@link #path}
     */
    public SourceDirectory(final String path) {
        this.path = path;
    }


    /**
     * <p></p>
     *
     * @param path String {@link #path}
     * @param shouldThrowExceptionWhenNotFound boolean {@link
     * #shouldThrowExceptionWhenNotFound}
     */
    public SourceDirectory(final String path, final boolean shouldThrowExceptionWhenNotFound) {

        setShouldThrowExceptionWhenNotFound(shouldThrowExceptionWhenNotFound);
        setPath(path);
    }


    /**
     * <p></p>
     *
     * @param path String {@link #path}
     * @param notFound boolean {@link @notFound}
     */
    public SourceDirectory(final String path, final String notFound) {
        setPath(path);
        setNotFound(notFound);
    }


    /**
     * Getter for property {@link #shouldThrowExceptionWhenNotFound}.
     *
     * @return Value for property <tt>shouldThrowExceptionWhenNotFound</tt>.
     */
    public boolean shouldThrowExceptionWhenNotFound() {
        return shouldThrowExceptionWhenNotFound;
    }


    /**
     * Setter for property {@link #shouldThrowExceptionWhenNotFound}.
     *
     * @param shouldThrowExceptionWhenNotFound Value to set for property
     * <tt>shouldThrowExceptionWhenNotFound</tt>.
     */
    public void setShouldThrowExceptionWhenNotFound(final boolean shouldThrowExceptionWhenNotFound) {

        /**
         * Update notFound property so that the String value and boolean
         * values coincide
         */
        notFound = shouldThrowExceptionWhenNotFound ? NOT_FOUND_EXEPTION : NOT_FOUND_IGNORE;

        this.shouldThrowExceptionWhenNotFound = shouldThrowExceptionWhenNotFound;
    }


    /**
     * <p>Getter for property {@link #path}.</p>
     *
     * @return Value for property <tt>path</tt>.
     */
    public String getPath() {
        return path;
    }


    /**
     * <p>Setter for property {@link #path}.</p>
     *
     * @param path Value to set for property <tt>path</tt>.
     */
    public void setPath(final String path) {

        Assert.assertNotNull(path);
        Assert.assertFalse("".equals(path));

        this.path = path;
    }


    /**
     * Setter for property 'notFound'.
     *
     * @param notFound Value to set for property 'notFound'.
     */
    public void setNotFound(String notFound) {
        /**
         * When null, set to "null" so that the exception message that is about
         * to be thrown is meaningfull.
         */
        if (notFound == null)
            notFound = "null";

        /**
         * Validate input.
         * Expect either 'ignore' or 'exception'
         */
        if (!(notFound.equalsIgnoreCase(NOT_FOUND_IGNORE) ||
              notFound.equalsIgnoreCase(NOT_FOUND_EXEPTION))) {

            throw new IllegalArgumentException("'not-found' property of " + notFound +
                                               " is invalid. valid values are " +
                                               NOT_FOUND_IGNORE + " and "
                                               + NOT_FOUND_EXEPTION);
        }

        this.notFound = notFound;

        /**
         * Update shouldThrowExceptionWhenNotFound property so that the
         * String value and boolean values coincide
         */
        final boolean shouldThrowException = notFound.equalsIgnoreCase(NOT_FOUND_EXEPTION);
        this.setShouldThrowExceptionWhenNotFound(shouldThrowException);

    }


    /**
     * @see Object#toString()
     */
    public String toString() {
        return path;
    }


    /**
     * @see Object#equals(Object)
     */
    public boolean equals(final Object object) {

        if (this == object)
            return true;

        if (object == null)
            return false;

        if (!(object instanceof SourceDirectory))
            return false;

        final SourceDirectory that = (SourceDirectory) object;

        if (path != null ? !path.equals(that.getPath()) : that.getPath() != null)
            return false;

        return true;
    }


    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        return (path != null ? path.hashCode() : 0);
    }
}
