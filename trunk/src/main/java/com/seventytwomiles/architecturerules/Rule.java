package com.seventytwomiles.architecturerules;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


/**
 * <p>Represents a <code>Rule</code> that may not be violoated.</p>
 *
 * @author mnereson
 */
class Rule {


    /**
     * <p>Unique id of this Rule as defined. Used to refer to this Rule in
     * messages.</p>
     *
     * @parameter id String
     */
    private String id;

    /**
     * <p>Name of the package that this rule applies to</p>
     *
     * @parameter packageName String
     */
    private String packageName;

    /**
     * <p>Comment about this rule that could be used in messages or just to make
     * the configuration file more readable.</p>
     *
     * @parameter comment String
     */
    private String comment;

    /**
     * <p>Collection of Strings. These Strings are package names. The names of
     * the packages that the {@link #packageName} may NOT depend upon</p>
     *
     * @parameter violations Collection
     */
    private Collection violations = new ArrayList();


    /**
     * <p>Instanciates a new <code>Rule</code> with the given <tt>id</tt> and
     * <tt>packageName</tt>. Probobly only used by tests.</p>
     *
     * @param id String id of this Rule to refer to it by
     * @param packageName String full name of the package to inspect
     */
    public Rule(final String id, final String packageName) {

        this.id = id;
        this.packageName = packageName;
    }


    /**
     * <p>Constructs a new Rule.</p>
     */
    public Rule() {

    }


    /**
     * <p>Getter for property {@link #id}.</p>
     *
     * @return Value for property <tt>id</tt>.
     */
    public String getId() {
        return id;
    }


    /**
     * <p>Getter for property {@link #comment}.</p>
     *
     * @return Value for property <tt>comment</tt>.
     */
    public String getComment() {
        return comment;
    }


    /**
     * <p>Setter for property {@link #comment}.</p>
     *
     * @param comment Value to set for property <tt>comment</tt>.
     */
    public void setComment(final String comment) {
        this.comment = comment;
    }


    /**
     * <p>Getter for property {@link #packageName}.</p>
     *
     * @return Value for property <tt>packageName</tt>.
     */
    public String getPackageName() {
        return packageName;
    }


    /**
     * <p>Remove a violation from this Rule.</p>
     *
     * @param vioation String a package this this Rule's package should not test
     * on
     * @return boolean <tt>true</tt> if the violation is renived from the List
     *         of violoations
     */
    public boolean removeViolation(final String vioation) {
        return violations.remove(vioation);
    }


    /**
     * <p>Add a new violation to this <code>Rule</code>.</p>
     *
     * @param violation String a package this this Rule's package may NOT depend
     * upon
     * @return boolean <tt>true</tt> if the violation is added to the List of
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
     * <p>Get all of the violations.</p>
     *
     * @return List unmodifiable
     */
    public Collection getViolations() {
        return Collections.unmodifiableCollection(violations);
    }


    /**
     * <p>Setter for property {@link #id}.</p>
     *
     * @param id Value to set for property <tt>id</tt>.
     */
    public void setId(final String id) {
        this.id = id;
    }


    /**
     * <p>Setter for property {@link #packageName}.</p>
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
