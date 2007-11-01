package com.seventytwomiles.architecturerules.domain;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 */
public class SourcesConfiguration {


    private String noPackages = "ignore";


    /**
     * Getter for property 'noPackages'.
     *
     * @return Value for property 'noPackages'.
     */
    public String getNoPackages() {
        return noPackages;
    }


    /**
     * Setter for property 'noPackages'.
     *
     * @param noPackages Value to set for property 'noPackages'.
     */
    public void setNoPackages(final String noPackages) {
        this.noPackages = noPackages;
    }
}
