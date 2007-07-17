package com.nereson.architecturerules;


import junit.framework.TestCase;


/**
 * ArchitectureTestService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>07/15/2007</pre>
 */
public class ArchitectureRulesServiceTest extends TestCase {


    public ArchitectureRulesServiceTest(String name) {
        super(name);
    }


    public void setUp() throws Exception {
        super.setUp();
    }


    public void tearDown() throws Exception {
        super.tearDown();
    }


    public void test() throws Exception {

        ArchitectureTestService architectureTestService = new ArchitectureTestService();
        architectureTestService.checkArchitecture();
    }
}
