package com.seventytwomiles.architecturerules.domain;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 */
public class SourceDirectory {


    /**
     * <p></p>
     *
     * @parameter shouldThrowExceptionWhenNotFound boolean
     */
    private boolean shouldThrowExceptionWhenNotFound = false;

    /**
     * <p></p>
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


    public SourceDirectory() {
    }


    public SourceDirectory(final String path) {
        this.path = path;
    }


    public SourceDirectory(final String path, final boolean shouldThrowExceptionWhenNotFound) {
        this.shouldThrowExceptionWhenNotFound = shouldThrowExceptionWhenNotFound;
        this.path = path;
    }


    /**
     * Getter for property {@link #path}.
     *
     * @return Value for property <tt>path</tt>.
     */
    public String getPath() {
        return path;
    }


    /**
     * Setter for property {@link #path}.
     *
     * @param path Value to set for property <tt>path</tt>.
     */
    public void setPath(final String path) {
        this.path = path;
    }


    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that "textually represents"
     * this object. The result should be a concise but informative
     * representation that is easy for a person to read. It is recommended that
     * all subclasses override this method. <p> The <code>toString</code> method
     * for class <code>Object</code> returns a string consisting of the name of
     * the class of which the object is an instance, the at-sign character
     * `<code>@</code>', and the unsigned hexadecimal representation of the hash
     * code of the object. In other words, this method returns a string equal to
     * the value of: <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    public String toString() {
        return path;
    }
}
