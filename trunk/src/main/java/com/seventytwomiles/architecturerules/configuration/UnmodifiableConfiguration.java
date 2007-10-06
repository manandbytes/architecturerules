package com.seventytwomiles.architecturerules.configuration;


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 */
public final class UnmodifiableConfiguration extends Configuration {


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
     * @parameter shouldThrowExceptionWhenNoPackages boolean
     */
    private boolean shouldThrowExceptionWhenNoPackages;

    /**
     * <p>sets to true when <samp>&lt;cyclicalDependency test="true"/> </samp>,
     * false when <samp>&lt;cyclicalDependency test="false"/> </samp></p>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    private boolean doCyclicDependencyTest;


    /**
     * <p>Instanciates a new unmodifiable configuration class.</p>
     *
     * @param configuration Configuration to offer as unmodifiable
     */
    public UnmodifiableConfiguration(final Configuration configuration) {

        this.rules.addAll(configuration.getRules());
        this.sources.addAll(configuration.getSources());
        this.doCyclicDependencyTest = configuration.shouldDoCyclicDependencyTest();
        this.shouldThrowExceptionWhenNoPackages = configuration.shouldThrowExceptionWhenNoPackages();
    }


    /**
     * <p>Getter for property {@link #rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    public Collection getRules() {
        return Collections.unmodifiableCollection(rules);
    }


    /**
     * <p>Getter for property {@link #sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    public Collection getSources() {
        return Collections.unmodifiableCollection(sources);
    }


    /**
     * <p> Getter for property {@link #shouldThrowExceptionWhenNoPackages}.</p>
     *
     * @return Value for property <tt>shouldThrowExceptionWhenNoPackages</tt>.
     */
    public boolean shouldThrowExceptionWhenNoPackages() {
        return shouldThrowExceptionWhenNoPackages;
    }


    /**
     * <p>Getter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @return Value for property <tt>doCyclicDependencyTest</tt>.
     */
    public boolean shouldDoCyclicDependencyTest() {
        return doCyclicDependencyTest;
    }
}
