package com.seventytwomiles.architecturerules.domain;


import junit.framework.Assert;


/**
 * <p>Representation of a source directory to search for packages and .class
 * files in.</p>
 *
 * @author mnereson
 */
public class SourceDirectory {


    /**
     * <p>When true, if this source {@link #path} is not found a
     * <code>SourcesNotFoundException</code> will be thrown.</p>
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
        this.shouldThrowExceptionWhenNotFound = shouldThrowExceptionWhenNotFound;
    }


    /**
     * <p></p>
     */
    public SourceDirectory() {
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
        this.shouldThrowExceptionWhenNotFound = shouldThrowExceptionWhenNotFound;
        this.path = path;
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


    public int hashCode() {
        return (path != null ? path.hashCode() : 0);
    }
}
