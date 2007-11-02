package com.seventytwomiles.architecturerules;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.domain.Rule;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see AbstractArchitectureRulesConfigurationTest
 */
public class ArchitectureTest extends AbstractArchitectureRulesConfigurationTest {


    public void ArchitectureTest() {

        final Configuration configuration = getConfiguration();

        /**
         * Get the configuraiton that already has the architecure-rules.xml
         * configuraiton loaded.
         *
         * Changing a boolean like configuration.setDoCyclicDependencyTest(false)
         * would override the value in the configuraiton file, because the
         * configuration file is loaded first.
         */

        final Rule rule = new Rule("test", "com.seventytwomiles.test");
        configuration.addRule(rule);
        configuration.setDoCyclicDependencyTest(false);
    }


    /**
     * @see AbstractArchitectureRulesConfigurationTest
     */
    String getConfigurationFileName() {

        /**
         * Provide the name of the rules configuraiton file. File file is
         * loaded from the classpath.
         */
        return "architecture-rules.xml";
    }


    /**
     * @see AbstractArchitectureRulesConfigurationTest#testArchitecture()
     */
    public void testArchitecture() {

        /**
         * Finally, run the test via doTest(). If any rules are broken, or if
         * the configuraiton can not be loaded properly, then the appropriate
         * Exception will be thrown.
         */
        /*assertTrue(doTests());*/
    }
}
