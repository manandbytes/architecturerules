package com.seventytwomiles.architecturerules.domain;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 */
public class CyclicDependencyConfiguration {


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
