package com.seventytwomiles.architecturerules.configuration;

/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more information visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;



/**
 * <p>UnmodifiableConfiguration Tester.</p>
 *
 * @author mikenereson
 */
public class UnmodifiableConfigurationTest extends TestCase {


    private Configuration configuration = new Configuration();


    public static Test suite() {
        return new TestSuite(UnmodifiableConfigurationTest.class);
    }


    public UnmodifiableConfigurationTest(String name) {
        super(name);
    }


    public void setUp() throws Exception {
        configuration.addSource(new SourceDirectory("core/target/classes"));
        configuration.addSource(new SourceDirectory("dao/target/classes"));

        final Rule rule = new Rule("dao", "com.seventytwomiles.dao");
        rule.addViolation("com.seventymiles.services");
        configuration.addRule(rule);

        final Rule rule1 = new Rule("model", "com.seventytwomiles.model");
        rule1.addViolation("com.seventymiles.services");
        configuration.addRule(rule1);

        configuration.setDoCyclicDependencyTest(true);
        configuration.setThrowExceptionWhenNoPackages(true);

        super.setUp();
    }


    public void tearDown() throws Exception {
        configuration = null;

        super.tearDown();
    }


    public void testUnmodifiability() {
        Configuration unmodifiableConfiguration = new UnmodifiableConfiguration(configuration);


        try {
            unmodifiableConfiguration.getRules().add(new Rule("test"));
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {
            unmodifiableConfiguration.getSources().add("web/target/classes");
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException);
        }

        try {
            unmodifiableConfiguration.setDoCyclicDependencyTest(false);
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException);
        }


        try {
            unmodifiableConfiguration.setThrowExceptionWhenNoPackages(false);
            fail("expected UnsupportedOperationException");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException);
        }
    }
}
