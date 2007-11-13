package com.seventytwomiles.architecturerules.ant;


import junit.framework.TestCase;


/**
 * <p><code>AssertArchitectureTask</code> Tester.</p>
 *
 * @author mikenereson
 */
public class AssertArchitectureTaskTest extends TestCase {


    public AssertArchitectureTaskTest(String name) {
        super(name);
    }


    AssertArchitectureTask task;


    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        task = new AssertArchitectureTask();
    }


    public void testExectute() throws Exception {

        task.setConfigurationFileName("architecture-rules.xml");
        task.execute();
    }


    public void testExectute_invalidArguments() throws Exception {

        try {

            task.execute();
            fail("expected IllegalStateException");

        } catch (Exception e) {

            assertTrue(e instanceof IllegalStateException);
        }


        try {

            task.setConfigurationFileName(null);
            task.execute();
            fail("expected IllegalStateException");

        } catch (Exception e) {

            assertTrue(e instanceof IllegalStateException);
        }


        try {

            task.setConfigurationFileName("");
            task.execute();
            fail("expected IllegalStateException");

        } catch (Exception e) {

            assertTrue(e instanceof IllegalStateException);
        }
    }
}
