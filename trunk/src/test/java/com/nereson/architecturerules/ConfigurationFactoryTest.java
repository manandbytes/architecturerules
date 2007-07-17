package com.nereson.architecturerules;


import junit.framework.TestCase;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * ConfigurationFactory Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>07/15/2007</pre>
 */
public class ConfigurationFactoryTest extends TestCase {


    public ConfigurationFactoryTest(String name) {
        super(name);
    }


    public void setUp() throws Exception {
        super.setUp();
    }


    public void tearDown() throws Exception {
        super.tearDown();
    }


    public void testGetSources() throws Exception {

        List sources = ConfigurationFactory.getSources();

        for (Iterator sourceIterator = sources.iterator(); sourceIterator.hasNext();) {

            String[] source = (String[]) sourceIterator.next();

            System.out.println(source[0]);
            System.out.println("\t\texception when not found: " + source[1]);
        }
    }


    public void testGetRules() throws Exception {

        Set rules = ConfigurationFactory.getRules();

        for (Iterator ruleIterator = rules.iterator(); ruleIterator.hasNext();) {

            Rule rule = (Rule) ruleIterator.next();

            rule.describe(true);
        }
    }

}
