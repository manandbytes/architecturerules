package com.seventytwomiles.architecturerules.configuration;


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


    public UnmodifiableConfigurationTest(String name) {
        super(name);
    }


    private Configuration configuration = new Configuration();


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


    public void testUnmodafiability() {

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


    public static Test suite() {
        return new TestSuite(UnmodifiableConfigurationTest.class);
    }
}
