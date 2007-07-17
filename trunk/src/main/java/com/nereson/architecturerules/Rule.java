package com.nereson.architecturerules;


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


    private String id;
    private String packageName;
    private String comment;
    private List violations = new ArrayList();


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
     * Getter for property 'id'.
     *
     * @return Value for property 'id'.
     */
    public String getId() {
        return id;
    }


    /**
     * Getter for property 'comment'.
     *
     * @return Value for property 'comment'.
     */
    public String getComment() {
        return comment;
    }


    /**
     * Setter for property 'comment'.
     *
     * @param comment Value to set for property 'comment'.
     */
    public void setComment(final String comment) {
        this.comment = comment;
    }


    /**
     * Getter for property 'packageName'.
     *
     * @return Value for property 'packageName'.
     */
    public String getPackageName() {
        return packageName;
    }


    /**
     * @param vioation String a package this this Rule's package should not test on
     * @return boolean true if the violation is renived from the List of violoations
     */
    public boolean removeViolation(final String vioation) {
        return violations.remove(vioation);
    }


    /**
     * @param violation String a package this this Rule's package may NOT depend upon
     * @return boolean true if the violation is added to the List of violoatizons
     * @throws IllegalRuleException when the packageName is also a violation: this is a Illegal Rule because
     *                              it can not be tessted and its better to ask the developer to understand what
     *                              they are asking me to test, rather than just ignore the configuration entry
     */
    public boolean addViolation(final String violation) throws IllegalRuleException {

        if (violation.equalsIgnoreCase(packageName))
            throw new IllegalRuleException(id + " can not add architecture violation to own package: " +
                    "remove <violation>" + violation + "</violation> from rule " + id);

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
     * Setter for property 'id'.
     *
     * @param id Value to set for property 'id'.
     */
    public void setId(final String id) {
        this.id = id;
    }


    /**
     * Setter for property 'packageName'.
     *
     * @param packageName Value to set for property 'packageName'.
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


    public String describe(final boolean outputToConsole) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<rule>").append("\r\n");
        stringBuilder.append("\t").append("<id>").append(id).append("</id>").append("\r\n");
        stringBuilder.append("\t").append("<packageName>").append(packageName).append("</packageName>").append("\r\n");
        stringBuilder.append("\t").append("<comment>").append(comment).append("</comment>").append("\r\n");
        stringBuilder.append("\t").append("<violations>").append("\r\n");

        for (Iterator violationIterator = violations.iterator(); violationIterator.hasNext();) {
            String violation = (String) violationIterator.next();
            stringBuilder.append("\t\t").append("<violation>").append(violation).append("</violation>").append("\r\n");
        }

        stringBuilder.append("\t").append("</violations>").append("\r\n");
        stringBuilder.append("</rule>").append("\r\n");

        if (outputToConsole)
            System.out.println(stringBuilder.toString());

        return stringBuilder.toString();
    }


}
