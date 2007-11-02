package com.seventytwomiles.architecturerules.configuration.xml;


import java.util.Collection;


/**
 * <p>Interface for <code>ConfigurationFactory</code> implementations to adhere
 * to.</p>
 *
 * @author mnereson
 */
public interface ConfigurationFactory {


    /**
     * <p>Getter for property {@link //sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    Collection getSources();


    /**
     * <p>Getter for property {@link /rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    Collection getRules();


    /**
     * @return boolean <tt>true</tt> when <samp>&lt;sources
     *         no-packages="exception"> </samp>
     */
    boolean throwExceptionWhenNoPackages();


    /**
     * @return boolean <tt>true</tt> when <samp>&lt;cyclicalDependency
     *         test="true"/> </samp>
     */
    boolean doCyclicDependencyTest();
}
