/**
 * Copyright 2007, 2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * For more information visit
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com/
 */

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
public class WildcardArchitectureTest extends AbstractArchitectureRulesConfigurationTest {

    /**
     * @see AbstractArchitectureRulesConfigurationTest
     */
    @Override
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
    @Override
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
