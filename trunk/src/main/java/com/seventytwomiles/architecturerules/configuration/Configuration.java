package com.seventytwomiles.architecturerules.configuration;


import com.seventytwomiles.architecturerules.domain.Rule;
import junit.framework.Assert;

import java.util.Collection;
import java.util.HashSet;


/**
 * <p>An instance of <code>Configuration</code> allows the applicaiton to
 * specifiy where the source directories are, what rules to test against and
 * under what conditions should an <code>Exception</code> be thrown.</p>
 *
 * <p>This <code>Configuraiton</code> may be loaded by configraiton from an XML
 * file in the classpath, through programatic configuraiton, or both.</p>
 *
 * @author mnereson
 * @see ConfigurationFactory
 * @see ConfigurationHandler
 * @see UnmodifiableConfiguration
 */
public class Configuration {


    /**
     * <p><code>Rules</code> that are read from the configuration file or added
     * programatically.</p>
     *
     * @parameter rules Set
     */
    private final Collection rules = new HashSet();

    /**
     * <p>List of <code>SourceDirectory</code> that are read from the
     * configuration file and or added programatically.</p>
     *
     * @parameter sources List
     */
    private final Collection sources = new HashSet();

    /**
     * <p>sets to true when <samp>&lt;sources no-packages="exception"&gt;</samp>,
     * false when <samp>&lt;sources no-packages="ignore"&gt;</samp></p>
     *
     * @parameter throwExceptionWhenNoPackages boolean
     */
    private boolean throwExceptionWhenNoPackages;

    /**
     * <p>sets to true when <samp>&lt;cyclicalDependency test="true"/> </samp>,
     * false when <samp>&lt;cyclicalDependency test="false"/> </samp></p>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    private boolean doCyclicDependencyTest;


    /**
     * <p>Getter for property {@link #rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    public Collection getRules() {
        return rules;
    }


    /**
     * <p>Add a new <code>Rule</code> to {@link #rules}</p>
     *
     * @param rule Rule to add
     * @return boolean <tt>true</tt> if this set did not already contain the
     *         specified element.
     */
    public boolean addRule(final Rule rule) {

        /* validate input */
        Assert.assertNotNull("rule can not be null", rule);

        Assert.assertNotNull("rule id can not be null", rule.getId());
        Assert.assertFalse("rule id must not be empty", rule.getId().equals(""));

        Assert.assertNotNull("rule package can not be null", rule.getPackageName());
        Assert.assertFalse("rule package must not be empty", rule.getPackageName().equals(""));

        Assert.assertFalse("rule violations must not be empty", rule.getViolations().isEmpty());

        return rules.add(rule);
    }


    /**
     * <p>Getter for property {@link #sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    public Collection getSources() {
        return sources;
    }


    /**
     * <p>Add a new source directory</p>
     *
     * @param source String relative path to source directory
     * @return boolean <tt>true</tt> if this list did not already contain the
     *         specified element.
     */
    public boolean addSource(final String source) {

        /* TODO: replace argument with SourceDirectory and update the validation below */

        /* validate input */
        Assert.assertNotNull("source can not be null", source);
        Assert.assertFalse("source must not be empty", source.equals(""));

        return sources.add(source);
    }


    /**
     * <p> Getter for property {@link #throwExceptionWhenNoPackages}.</p>
     *
     * @return Value for property <tt>throwExceptionWhenNoPackages</tt>.
     */
    public boolean shouldThrowExceptionWhenNoPackages() {
        return throwExceptionWhenNoPackages;
    }


    /**
     * <p>Getter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @return Value for property <tt>doCyclicDependencyTest</tt>.
     */
    public boolean shouldDoCyclicDependencyTest() {
        return doCyclicDependencyTest;
    }


    /**
     * <p>Setter for property {@link #throwExceptionWhenNoPackages}.</p>
     *
     * @param throwExceptionWhenNoPackages Value to set for property
     * <tt>throwExceptionWhenNoPackages</tt>.
     */
    public void setThrowExceptionWhenNoPackages(final boolean throwExceptionWhenNoPackages) {
        this.throwExceptionWhenNoPackages = throwExceptionWhenNoPackages;
    }


    /**
     * <p>Setter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @param doCyclicDependencyTest Value to set for property
     * <tt>doCyclicDependencyTest</tt>.
     */
    public void setDoCyclicDependencyTest(final boolean doCyclicDependencyTest) {
        this.doCyclicDependencyTest = doCyclicDependencyTest;
    }
}
