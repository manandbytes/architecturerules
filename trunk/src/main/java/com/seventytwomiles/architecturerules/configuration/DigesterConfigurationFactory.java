package com.seventytwomiles.architecturerules.configuration;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see AbstractConfigurationFactory
 */
public class DigesterConfigurationFactory extends AbstractConfigurationFactory {

    /**
     * TODO: javadocs
     * TODO: process Rules and SourceDirectories
     * TODO: delete ConfigurationHandler and ParserConfiguraitonFactory
     */


    /**
     * <p>TODO: javadocs </p>
     *
     * @param configurationFileName
     */
    public DigesterConfigurationFactory(final String configurationFileName) {

        try {

            final String configurationXml;

            configurationXml = getConfigurationAsXml(configurationFileName);

            validateConfigruation(configurationXml);
            processConfiguration(configurationXml);

        } catch (final Exception e) {

            throw new RuntimeException(e);
        }
    }


    private void processConfiguration(final String configurationXml) {

        /* todo: process configuraiton */
    }


}
