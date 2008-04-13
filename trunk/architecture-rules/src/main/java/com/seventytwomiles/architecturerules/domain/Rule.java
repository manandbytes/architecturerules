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

package com.seventytwomiles.architecturerules.domain;


import com.seventytwomiles.architecturerules.exceptions.IllegalArchitectureRuleException;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;


/**
 * <p>Represents a <code>Rule</code> that may not be violoated.</p>
 *
 * @author mikenereson
 */
public class Rule {


    private static final Log log = LogFactory.getLog(Rule.class);

    /**
     * <p>Unique id of this Rule as defined. Used to refer to this Rule in
     * messages.</p>
     *
     * @parameter id String
     */
    private String id;

    /**
     * <p>Collection of Strings. These Strings are package names. The names of
     * the packages that will be check against the {@link #violations}. These
     * packages may NOT depend upon the packages listed in violations.</p>
     *
     * @parameter violations Collection
     */
    private final Collection<String> packages = new HashSet<String>();

    /**
     * <p>Comment about this rule that could be used in messages or just to make
     * the configuration file more readable.</p>
     *
     * @parameter comment String
     */
    private String comment;

    /**
     * <p>Collection of Strings. These Strings are package names. The names of
     * the packages that the {@link #packages} may NOT depend upon</p>
     *
     * @parameter violations Collection
     */
    private final Collection<String> violations = new HashSet<String>();


    /**
     * <p>Constructs a new Rule.</p>
     */
    public Rule() {
        // do nothing
    }


    /**
     * <p>Instantiates a new Rule with the given <tt>id</tt>.</p>
     *
     * @param id sets the {@link #id}
     */
    public Rule(final String id) {
        this.id = id;
    }


    /**
     * <p>Instantiates a new Rule with the given <tt>id</tt>.</p>
     *
     * <p>This constructor is to provide some sense of backwards compatibility
     * with the releases in series 1 (1.0 and 1.1)</p>
     *
     * @param id          sets the {@link #id}
     * @param packageName a {@link @packages} to assert on.
     */
    public Rule(final String id, final String packageName) {

        setId(id);
        addPackage(packageName);
    }


    /**
     * <p>Adds a package to the Packages collection.</p>
     *
     * @param packageName String
     * @return boolean <tt>true</tt> when the package is actually added to the
     *         Collection. <tt>false</tt> would be returned if the package was
     *         already in the Collection of packages.
     */
    public boolean addPackage(final String packageName) {

        Assert.assertNotNull("null packageName can not be added", packageName);
        Assert.assertFalse("empty packageName can not be added", packageName.equals(""));

        if (violations.contains(packageName))
            throw new IllegalArchitectureRuleException(
                    "Could not add " + packageName + " package because there " +
                            "is already a violation with the same package name " +
                            "for rule " + id);

        return packages.add(packageName);
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
     * @return Rule this <code>Rule</code> to allow for method chaining.
     */
    public Rule setComment(final String comment) {

        Assert.assertNotNull("comment can not be null", comment);
        this.comment = comment;

        return this;
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
     * <p>Setter for property {@link #id}.</p>
     *
     * @param id Value to set for property <tt>id</tt>.
     * @return Rule this <code>Rule</code> to allow for method chaining.
     */
    public Rule setId(final String id) {

        Assert.assertNotNull("id can not be null", id);
        Assert.assertFalse("id can not be empty", "".equals(id));

        this.id = id;

        return this;
    }

    /**
     * <p>Same as {@link #setId(String)}. The <code>DigesterConfiguraitonFactory</code>
     * that builds the <code>Configuration<code> class can invoke void setter.
     * When we added method chaining at version 2.1.0, we made <tt>setId</tt>
     * return <code>Rule</code>, and the configuration factory broke. So this
     * method was created to allow for the ConfigurationFactory to work, and for
     * method chaining to be supported.</p>
     *
     * @param id Value to set for property <tt>id</tt>.
     */
    public void setIdString(final String id) {

        Assert.assertNotNull("id can not be null", id);
        Assert.assertFalse("id can not be empty", "".equals(id));

        this.id = id;
    }


    /**
     * <p>Getter for property {@link #packages}</p>
     *
     * @return Value for property <tt>packages</tt>.
     */
    public Collection<String> getPackages() {
        return this.packages;
    }


    /**
     * @see Object#equals(Object)
     */
    @SuppressWarnings({"RedundantIfStatement"})
    public boolean equals(final Object object) {

        if (this == object)
            return true;

        if (object == null)
            return false;

        if (!(object instanceof Rule))
            return false;

        final Rule that = (Rule) object;

        if (id != null ? !id.equals(that.getId()) : that.getId() != null)
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
     * <p>Add a new violation to this <code>Rule</code>.</p>
     *
     * @param violation String a package this this Rule's package may NOT depend
     *                  upon
     * @return Rule this <code>Rule</code> to allow for method chaining.
     * @throws IllegalArchitectureRuleException
     *          a RuntimeException when the violation could not be added because
     *          the violation is one of the packages being checked.
     */
    public Rule addViolation(final String violation) {

        Assert.assertNotNull("null violation can not be added", violation);

        Assert.assertFalse("empty violation can not be added",
                violation.equals(""));

        if (packages.contains(violation))
            throw new IllegalArchitectureRuleException(
                    "Could not add architecture rule violation that creates rule " +
                            "that says a package can not use itself. Remove " +
                            "<violation>" + violation + "</violation> " +
                            "from rule " + id);

        boolean added = violations.add(violation);


        if (added) {

            log.debug(String.format("added violation %s to Rule %s",
                    violation, id));

        } else {

            log.warn(String.format("failed to add violation %s to Rule %s",
                    violation, id));
        }

        return this;
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
     *                        the console
     * @return String of xml that describes this <code>Rule</code>.
     */
    private String describe(final boolean outputToConsole) {

        final StringBuffer builder = new StringBuffer();

        builder.append("<rule>").append("\r\n");

        builder.append("\t")
                .append("<id>")
                .append(id)
                .append("</id>")
                .append("\r\n");

        builder.append("\t")
                .append("<packages>")
                .append(packages)
                .append("</packages>")
                .append("\r\n");

        builder.append("\t")
                .append("<comment>")
                .append(comment)
                .append("</comment>")
                .append("\r\n");

        builder.append("\t").append("<violations>").append("\r\n");

        for (String violation : violations) {

            builder.append("\t\t").append("<violation>");
            builder.append(violation);
            builder.append("</violation>");
            builder.append("\r\n");
        }

        builder.append("\t").append("</violations>").append("\r\n");
        builder.append("</rule>").append("\r\n");

        if (outputToConsole)
            System.out.println(builder.toString());

        return builder.toString();
    }


    /**
     * <p>Creates a String representation of this <code>Rule</code>. Useful for
     * debugging and logging.</p>
     *
     * @return String describes this rule at its current state. Such as
     *         <samp>['dao' for 'com.company.dao, com.company.dao.hibernate']</samp>
     */
    public String getDescriptionOfRule() {

        final String ruleDescription = "['{0}' for {1}] "
                .replaceAll("\\{0}", getId())
                .replaceAll("\\{1}", describePackages());

        return ruleDescription;
    }


    /**
     * <p>Creates a String representation of this <tt>packages</tt>. Useful for
     * debugging and logging.</p>
     *
     * @return String describes this Rule's packages. Such as
     *         <samp>com.company.dao, com.company.dao.hibernate</samp>
     */
    public String describePackages() {

        final StringBuffer packagesDescription = new StringBuffer();

        final Object[] packagesArray = packages.toArray();
        final int totalPackages = packagesArray.length;

        for (int i = 0; i < totalPackages; i++) {

            final String packageName = (String) packagesArray[i];

            packagesDescription
                    .append(packageName.trim())
                    .append(" ");

            if (i + 1 < totalPackages)
                packagesDescription.append(",");
        }

        final String description = packagesDescription.toString()
                .replaceAll("\\[", "")
                .replaceAll("\\]", "");

        return description;
    }


    /**
     * <p>Get all of the <tt>violations</tt>.</p>
     *
     * <p>Note: this Collection is unmodifiable, use {@link #addViolation} and
     * {@link #removeViolation}</p>
     *
     * @return Collection unmodifiable
     * @throws UnsupportedOperationException when <code>getViolations.add(Object)</code>
     *                                       or <code>getViolations.remove(Object)</code>
     *                                       is called. Use {@link #addViolation}
     *                                       and {@link #removeViolation}.
     */
    public Collection<String> getViolations() {
        return Collections.unmodifiableCollection(violations);
    }


    /**
     * <p>Remove a violation from this Rule.</p>
     *
     * @param violation String a package this this Rule's package should not
     *                  test on
     * @return Rule this <code>Rule</code> to allow for method chaining.
     */
    public Rule removeViolation(final String violation) {

        Assert.assertNotNull("null violation can not be removed", violation);
        Assert.assertFalse("empty violation can not be removed", "".equals(violation));

        boolean removed = violations.remove(violation);

        if (removed) {

            log.debug(String.format("removed violation %s from Rule %s",
                    violation, this.id));

        } else {

            log.warn(String.format("failed to remove violation %s from Rule %s ",
                    violation, this.id));
        }

        return this;
    }

    /**
     * <p>Remove a package from this Rule.</p>
     *
     * @param packageName String a package this this Rule's package should not
     *                    test on
     * @return Rule this <code>Rule</code> to allow for method chaining.
     */
    public Rule removePackage(final String packageName) {

        Assert.assertNotNull("null packageName can not be removed", packageName);
        Assert.assertFalse("empty packageName can not be removed", "".equals(packageName));

        boolean removed = packages.remove(packageName);

        if (removed) {

            log.debug(String.format("removed package %s from Rule %s",
                    packageName, this.id));

        } else {

            log.warn(String.format("failed to remove package %s from Rule %s ",
                    packageName, this.id));
        }

        return this;
    }
}
