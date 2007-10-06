package com.seventytwomiles.architecturerules;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see AbstractArchitectureRulesConfigurationTest
 */
public class SampleTest extends AbstractArchitectureRulesConfigurationTest {


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
