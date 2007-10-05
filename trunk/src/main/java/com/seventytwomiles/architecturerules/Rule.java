package com.seventytwomiles.architecturerules;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 */
class Rule {


    /**
     * <p></p>
     *
     * @parameter
     */
    private String id;

    /**
     * <p></p>
     *
     * @parameter
     */
    private String packageName;

    /**
     * <p></p>
     *
     * @parameter
     */
    private String comment;

    /**
     * <p></p>
     *
     * @parameter
     */
    private List violations = new ArrayList();


    /**
     * <p></p>
     *
     * @param id
     * @param packageName
     */
    public Rule(final String id, final String packageName) {

        this.id = id;
        this.packageName = packageName;
    }


    /**
     * Constructs a new Rule.
     */
    public Rule() {

    }


    /**
     * Getter for property {@link #id}.
     *
     * @return Value for property <tt>id</tt>.
     */
    public String getId() {
        return id;
    }


    /**
     * Getter for property {@link #comment}.
     *
     * @return Value for property <tt>comment</tt>.
     */
    public String getComment() {
        return comment;
    }


    /**
     * Setter for property {@link #comment}.
     *
     * @param comment Value to set for property <tt>comment</tt>.
     */
    public void setComment(final String comment) {
        this.comment = comment;
    }


    /**
     * Getter for property {@link #packageName}.
     *
     * @return Value for property <tt>packageName</tt>.
     */
    public String getPackageName() {
        return packageName;
    }


    /**
     * @param vioation String a package this this Rule's package should not test
     * on
     * @return boolean true if the violation is renived from the List of
     *         violoations
     */
    public boolean removeViolation(final String vioation) {
        return violations.remove(vioation);
    }


    /**
     * @param violation String a package this this Rule's package may NOT depend
     * upon
     * @return boolean true if the violation is added to the List of
     *         violoatizons
     * @throws IllegalArchitectureRuleException when the packageName is also a
     * violation: this is a Illegal Rule because it can not be tessted and its
     * better to ask the developer to understand what they are asking me to
     * test, rather than just ignore the configuration entry
     */
    public boolean addViolation(final String violation) throws IllegalArchitectureRuleException {

        if (violation.equalsIgnoreCase(packageName))
            throw new IllegalArchitectureRuleException(
                    "Could not add architecture rule violation that creates rule " +
                            "that says a package can not use itself. Remove " +
                            "<violation>" + violation + "</violation> " +
                            "from rule " + id);

        return violations.add(violation);
    }


    /**
     * Get all of the violations.
     *
     * @return List unmodifiable
     */
    public List getViolations() {
        return Collections.unmodifiableList(this.violations);
    }


    /**
     * Setter for property {@link #id}.
     *
     * @param id Value to set for property <tt>id</tt>.
     */
    public void setId(final String id) {
        this.id = id;
    }


    /**
     * Setter for property {@link #packageName}.
     *
     * @param packageName Value to set for property <tt>packageName</tt>.
     */
    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }


    /**
     * @see Object#equals(Object)
     */
    public boolean equals(final Object object) {

        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        final Rule that = (Rule) object;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;

        return true;
    }


    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }


    /**
     * <p>Describes the properties of this rule in an xml-like format.</p>
     *
     * @return String of xml that describes this <code>Rule</code>.
     */
    public String describe() {
        return describe(false);
    }


    /**
     * <p>Describes the properties of this rule in an xml-like format.</p>
     *
     * @param outputToConsole boolean <tt>true</tt> to output the description to
     * the console
     * @return String of xml that describes this <code>Rule</code>.
     */
    public String describe(final boolean outputToConsole) {

        final StringBuffer stringBuilder = new StringBuffer();
        stringBuilder.append("<rule>").append("\r\n");
        stringBuilder.append("\t").append("<id>").append(id).append("</id>").append("\r\n");
        stringBuilder.append("\t").append("<packageName>").append(packageName).append("</packageName>").append("\r\n");
        stringBuilder.append("\t").append("<comment>").append(comment).append("</comment>").append("\r\n");
        stringBuilder.append("\t").append("<violations>").append("\r\n");

        for (Iterator violationIterator = violations.iterator();
             violationIterator.hasNext();) {

            final String violation = (String) violationIterator.next();
            stringBuilder.append("\t\t").append("<violation>").append(violation).append("</violation>").append("\r\n");
        }

        stringBuilder.append("\t").append("</violations>").append("\r\n");
        stringBuilder.append("</rule>").append("\r\n");

        if (outputToConsole)
            System.out.println(stringBuilder.toString());

        return stringBuilder.toString();
    }
}
