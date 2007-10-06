package com.seventytwomiles.architecturerules.configuration;


import com.seventytwomiles.architecturerules.domain.Rule;

import java.util.*;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
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

        /* todo: validate input */

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
     * @param source
     * @return boolean <tt>true</tt> if this list did not already contain the
     *         specified element.
     */
    public boolean addSource(final String source) {

        /* todo: validate input */

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
