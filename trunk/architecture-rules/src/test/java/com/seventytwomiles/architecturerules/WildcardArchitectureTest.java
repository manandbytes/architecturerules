package com.seventytwomiles.architecturerules;

/*
* Copyright (c) 2008, Your Corporation. All Rights Reserved.
*/


/**
 * <p>Architecture test example.</p>
 *
 * @author mikenereson
 * @see AbstractArchitectureRulesConfigurationTest
 */
public class WildcardArchitectureTest
        extends AbstractArchitectureRulesConfigurationTest {


    /**
     * @see AbstractArchitectureRulesConfigurationTest
     */
    public String getConfigurationFileName() {

        /**
         * Provide the name of the rules configuration file. File file is
         * loaded from the classpath.
         */
        return "architecture-rules-pass-wildcards.xml";
    }


    /**
     * @see AbstractArchitectureRulesConfigurationTest#testArchitecture()
     */
    public void testArchitecture() {

        /**
         * Run the test via doTest(). If any rules are broken, or if
         * the configuration can not be loaded properly, then the appropriate
         * Exception will be thrown.
         */
        try {

            assertFalse(doTests());
            fail("expected DependencyConstraintException");

        } catch (Exception DependencyConstraintException) {

            // expected
        }
    }
}