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
 *         http://72miles.com/ and
 *         http://architecturerules.googlecode.com/
 */

package org.architecturerules;


import org.architecturerules.configuration.Configuration;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;
import org.architecturerules.exceptions.CyclicRedundancyException;

import java.io.File;


/**
 * <p>Architecture test example.</p>
 *
 * @author mikenereson
 * @see AbstractArchitectureRulesConfigurationTest
 */
public class ArchitectureTest extends AbstractArchitectureRulesConfigurationTest {

    public ArchitectureTest() {

        final Configuration configuration = getConfiguration();

        /**
         * Get the configuration that already has the architecture-rules.xml
         * configuration loaded.
         */

        /**
         * Changing a boolean like configuration.setDoCyclicDependencyTest(false)
         * would override the value in the configuration file, because the
         * configuration file is loaded first.
         */
        final Rule rule = new Rule("services");
        rule.setComment("services may not depend on web layer.");
        rule.addPackage("com.company.app.core.services");
        rule.addViolation("com.company.app.web");
        rule.addViolation("com.company.app.web.spring");
        rule.addViolation("com.company.app.web.decorators");

        configuration.addRule(rule);
    }

    /**
     * @see AbstractArchitectureRulesConfigurationTest
     */
    @Override
    public String getConfigurationFileName() {

        /**
         * Provide the name of the rules configuration file. File file is
         * loaded from the classpath.
         */
        return "architecture-rules-fail-cyclic.xml";
    }


    /**
     * @see AbstractArchitectureRulesConfigurationTest#testArchitecture()
     */
    @Override
    public void testArchitecture() {

        /**
         * Finally, run the test via doTest(). If any rules are broken, or if
         * the configuration can not be loaded properly, then the appropriate
         * Exception will be thrown.
         */
        try {

            assertTrue(doTests());
            fail("Cycles have not been detected");
        } catch (final CyclicRedundancyException e) {

            final Stringer message = new Stringer(e.getMessage());

            assertTrue(message.has("test.com.seventytwomiles.services"));
            assertTrue(message.has("test.com.seventytwomiles.model"));
            assertTrue(message.has("test.com.seventytwomiles.dao.hibernate"));
        }

        final Configuration configuration = getConfiguration();
        configuration.setDoCyclicDependencyTest(false);
        assertTrue(doTests());

        configuration.getSources().clear();

        final String classes = "target" + File.separator + "classes";
        configuration.getSources().add(new SourceDirectory(classes, true));

        assertTrue(doTests());

        configuration.getSources().clear();

        final String tests = "target" + File.separator + "test-classes";
        final SourceDirectory directory = new SourceDirectory(tests, true);
        configuration.getSources().add(directory);

        try {

            assertTrue(doTests());
        } catch (final CyclicRedundancyException e) {

            final Stringer message = new Stringer(e.getMessage());

            assertTrue(message.has("test.com.seventytwomiles.services"));
            assertTrue(message.has("test.com.seventytwomiles.model"));
            assertTrue(message.has("test.com.seventytwomiles.dao.hibernate"));
        }
    }
}



/**
 * Silly little class that allows me to call .contains on a String. It was created simply to prevent
 * assertTrue(message.has("test.com.seventytwomiles.dao.hibernate")); from wrapping to a new line.
 */
class Stringer {

    private final String string;

    Stringer(final String string) {

        this.string = string;
    }

    public boolean has(String contains) {

        return this.string.indexOf(contains) > -1;
    }
}
