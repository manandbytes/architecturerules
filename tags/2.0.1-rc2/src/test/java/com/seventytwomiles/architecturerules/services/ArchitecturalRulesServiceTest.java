package com.seventytwomiles.architecturerules.services;

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


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import com.seventytwomiles.architecturerules.exceptions.DependencyConstraintException;
import junit.framework.TestCase;


/**
 * <p><code>RulesService</code> Tester.</p>
 *
 * @author mikenereson
 */
public class ArchitecturalRulesServiceTest extends TestCase {


    public ArchitecturalRulesServiceTest(String name) {
        super(name);
    }


    private RulesService rulesService;

    private Configuration configuration = new Configuration();

    private final SourceDirectory testClassesSourceDirectory = new SourceDirectory("target\\test-classes", true);

    private Rule goodModelRule;
    private Rule badControllerRule;


    public void setUp() throws Exception {

        configuration.addSource(testClassesSourceDirectory);

        badControllerRule = new Rule("controller", "test.com.seventytwomiles.web.spring");
        badControllerRule.addViolation("test.com.seventytwomiles.dao");

        goodModelRule = new Rule("model", "test.com.seventytwomiles.model");
        goodModelRule.addViolation("test.com.seventytwomiles.dao");
        goodModelRule.addViolation("test.com.seventytwomiles.dao.hibernate");

        super.setUp();
    }


    public void tearDown() throws Exception {

        rulesService = null;
        configuration = null;

        super.tearDown();
    }


    public void testPerformRulesTest() {

        /* setup good configuration */
        configuration.addRule(goodModelRule);

        rulesService = new RulesServiceImpl(configuration);

        assertTrue(rulesService.performRulesTest());
    }


    public void testPerformRulesTest_violations() {

        /* setup bad configuration */
        configuration.addRule(badControllerRule);

        rulesService = new RulesServiceImpl(configuration);

        try {

            assertTrue(rulesService.performRulesTest());

        } catch (final Exception e) {

            assertTrue(e instanceof DependencyConstraintException);
            assertEquals("rule controller failed: test.com.seventytwomiles.web.spring is not allowed to depend upon test.com.seventytwomiles.dao", e.getMessage());
        }
    }
}
