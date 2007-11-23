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
* For more infomration visit
* http://architecturerules.googlecode.com/svn/docs/index.html
*/


import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;


/**
 * <p>Configuration Tester</p>
 *
 * @author mikenereson
 */
public class ConfigurationTest extends TestCase {


    /**
     * <p>Instanciates a new test with the given <tt>name</tt>.</p>
     *
     * @param name String name to give test
     */
    public ConfigurationTest(String name) {
        super(name);
    }


    /**
     * <p>Instance of <code>Configuration</code> to test against.</p>
     *
     * @parameter configuration Configuration
     */
    private Configuration configuration;


    /**
     * @see TestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        configuration = new Configuration();
    }


    /**
     * @see TestCase#tearDown()
     */
    public void tearDown() throws Exception {

        configuration = null;

        super.tearDown();
    }


    /**
     * <p>Test for {@link Configuration#getRules()} </p>
     *
     * @throws Exception when <code>Configuraiton</code> throws unexected
     * <code>Exception</code>
     */
    public void testGetRules() throws Exception {

        /* is empty by default */
        assertTrue(configuration.getRules().isEmpty());

        /* construct a new Rule and add to configuraiton */
        Rule rule = new Rule("dao", "com.seventytwomiles.dao");
        rule.addViolation("com.seventytwomiles.web.controllers");

        assertTrue(configuration.addRule(rule));

        /* assert increase in size and that it conatins the Rule */
        assertEquals(1, configuration.getRules().size());
        assertTrue(configuration.getRules().contains(rule));
    }


    /**
     * <p>Test for {@link Configuration#addRule(Rule)} </p>
     *
     * @throws Exception when <code>Configuraiton</code> throws unexpected
     * <code>Exception</code>
     */
    public void testAddRule() throws Exception {

        /* is empty by default */
        assertTrue(configuration.getRules().isEmpty());

        Rule rule;

        rule = new Rule("dao", "com.seventytwomiles.dao");
        rule.addViolation("com.seventytwomiles.web.controllers");

        configuration.addRule(rule);

        assertEquals(1, configuration.getRules().size());
        assertTrue(configuration.getRules().contains(rule));
    }


    /**
     * <p>Test for {@link Configuration#addRule(Rule)} when the Rule is
     * invalid</p>
     *
     * @throws Exception when <code>Configuraiton</code> throws unexected
     * <code>Exception</code>
     */
    public void testAddRule_illegalArguments() throws Exception {

        Rule rule = null;

        try {

            configuration.addRule(rule);
            fail("expected AssertionFailedError");

        } catch (AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("rule can not be null") > -1);
        }

        try {

            rule = new Rule();
            configuration.addRule(rule);
            fail("expected AssertionFailedError");

        } catch (AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("rule id can not be null") > -1);
        }

        try {

            rule.setId("");
            configuration.addRule(rule);
            fail("expected AssertionFailedError");

        } catch (AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("id can not be empty") > -1);
        }

        try {

            rule.setId("validId");
            configuration.addRule(rule);
            fail("expected AssertionFailedError");

        } catch (AssertionFailedError e) {

            assertTrue(e.getMessage().indexOf("rule packages must not be empty") > -1);
        }

        try {

            rule.addPackage("com.seventytwomiles.dao");
            configuration.addRule(rule);
            fail("expected AssertionFailedError");

        } catch (AssertionFailedError e) {

            final String message = e.getMessage();
            assertTrue(message.indexOf("rule violations must not be empty") > -1);
        }
    }


    public void addSource() {

        assertTrue(configuration.addSource(new SourceDirectory("core/target/classes")));
        assertEquals(1, configuration.getSources().size());
    }


    public void addSource_illegalArguments() {

        try {

            configuration.addSource(null);
            fail("expected IllegalArgumentException");

        } catch (Exception e) {

            assertTrue(e instanceof IllegalArgumentException);
        }

        try {

            configuration.addSource(new SourceDirectory(""));
            fail("expected AssertionFailedError");

        } catch (Exception e) {

            assertTrue(e instanceof IllegalArgumentException);
        }

    }


    /**
     * <p>Test for {@link Configuration#getSources()}</p>
     *
     * @throws Exception when <code>Configuraiton</code> throws unexected
     * <code>Exception</code>
     */
    public void testGetSources() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * <p>Test for {@link Configuration#setThrowExceptionWhenNoPackages(boolean)}
     * </p>
     *
     * @throws Exception when <code>Configuraiton</code> throws unexected
     * <code>Exception</code>
     */
    public void testSetThrowExceptionWhenNoPackages() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * <p>Test for {@link Configuration#setDoCyclicDependencyTest(boolean)}
     * </p>
     *
     * @throws Exception when <code>Configuraiton</code> throws unexected
     * <code>Exception</code>
     */
    public void testSetDoCyclicDependencyTest() throws Exception {
        //TODO: Test goes here...
    }


}
