package com.seventytwomiles.architecturerules.configuration;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Collections;


/**
 * <p>todo: javadocs</p>
 *
 * @author mikenereson
 */
public final class UnmodifiableConfiguration extends Configuration {


    private static final Log log = LogFactory.getLog(UnmodifiableConfiguration.class);


    /**
     * <p>Instanciates a new unmodifiable configuration class.</p>
     *
     * @param configuration Configuration to offer as unmodifiable
     */
    public UnmodifiableConfiguration(final Configuration configuration) {

        this.rules.addAll(configuration.getRules());
        this.sources.addAll(configuration.getSources());
        this.doCyclicDependencyTest = configuration.shouldDoCyclicDependencyTest();
        this.throwExceptionWhenNoPackages = configuration.shouldThrowExceptionWhenNoPackages();
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
     * <p>Setter for property {@link #throwExceptionWhenNoPackages}.</p>
     *
     * @param throwExceptionWhenNoPackages Value to set for property
     * <tt>throwExceptionWhenNoPackages</tt>.
     */
    public void setThrowExceptionWhenNoPackages(final boolean throwExceptionWhenNoPackages) {
        throw new UnsupportedOperationException("");
    }


    /**
     * <p>Setter for property {@link #doCyclicDependencyTest}.</p>
     *
     * @param doCyclicDependencyTest Value to set for property
     * <tt>doCyclicDependencyTest</tt>.
     */
    public void setDoCyclicDependencyTest(final boolean doCyclicDependencyTest) {
        throw new UnsupportedOperationException("");

    }
}
