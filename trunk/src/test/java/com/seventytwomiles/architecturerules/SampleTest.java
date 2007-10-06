package com.seventytwomiles.architecturerules;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.domain.Rule;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see AbstractArchitectureRulesConfigurationTest
 */
public class SampleTest extends AbstractArchitectureRulesConfigurationTest {


    public SampleTest() {

        final Configuration configuration = getConfiguration();

        /**
         * Get the configuraiton that already has teh architecure-rules.xml
         * configuraiton loaded.
         *
         * Changing a boolean like configuration.setDoCyclicDependencyTest(false)
         * would override the value in the configuraiton file, because the
         * configuration file is loaded first.
         */

        configuration.addRule(new Rule("test", "com.seventytwomiles.test"));
        configuration.setDoCyclicDependencyTest(false);
    }


    /**
     * @see AbstractArchitectureRulesConfigurationTest
     */
    String getConfigurationFileName() {
        return "architecture-rules.xml";
    }


    /**
     * @see AbstractArchitectureRulesConfigurationTest#testArchitecture()
     */
    public void testArchitecture() {
        assertTrue(doTests());
    }
}
