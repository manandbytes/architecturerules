package com.seventytwomiles.architecturerules;


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


    public void test() {

        ArchitectureTestService architectureTestService = new ArchitectureTestService();

        try {

            architectureTestService.checkArchitecture();

        } catch (CyclicRedundencyException e) {

            fail(e.getMessage());

        } catch (DependencyConstraintException e) {

            fail(e.getMessage());

        } catch (SourceNotFoundException e) {

            fail(e.getMessage());

        } catch (NoPackagesFoundException e) {

            fail(e.getMessage());
        }
    }
}
