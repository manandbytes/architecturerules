package com.seventytwomiles.architecturerules.domain;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>todo: javadocs</p>
 *
 * @author mikenereson
 */
public class CyclicDependencyConfiguration {


    private static final Log log = LogFactory.getLog(CyclicDependencyConfiguration.class);

    private String test = "true";


    /**
     * Getter for property 'test'.
     *
     * @return Value for property 'test'.
     */
    public String getTest() {
        return test;
    }


    /**
     * Setter for property 'test'.
     *
     * @param test Value to set for property 'test'.
     */
    public void setTest(final String test) {

        if (test != null && !test.equalsIgnoreCase("null"))
            this.test = test;
    }
}
