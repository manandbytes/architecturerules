package com.seventytwomiles.architecturerules.services;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;
import junit.framework.TestCase;


/**
 * <p><code>CyclicRedundencyService</code> Tester.</p>
 *
 * @author mnereson
 */
public class CyclicRedundencyServiceTest extends TestCase {


    public CyclicRedundencyServiceTest(String name) {
        super(name);
    }


    private CyclicRedundencyService cyclicRedundencyService;
    private Configuration configuration = new Configuration();
    private SourceDirectory testClassesSourceDirectory = new SourceDirectory("target\\test-classes", true);
    private Rule controllerRule = new Rule();
    private Rule modelRule = new Rule();


    public void setUp() throws Exception {

        super.setUp();

        configuration.addSource(testClassesSourceDirectory);

        /**
         * Expect that ..web.spring depends on both ..dao and ..dao.hibernate
         */
        controllerRule.setId("controllers");
        controllerRule.addPackage("test.com.seventytwomiles.web.spring");
        controllerRule.addViolation("test.com.seventytwomiles.dao");
        controllerRule.addViolation("test.com.seventytwomiles.dao.hibernate");
        configuration.addRule(controllerRule);

        /**
         * Expect model to have cyclical dependency with services
         */
        modelRule.setId("model");
        modelRule.addPackage("test.com.seventytwomiles.model");
        modelRule.addViolation("test.com.seventytwomiles.services");
        configuration.addRule(modelRule);

        configuration.setThrowExceptionWhenNoPackages(true);

        cyclicRedundencyService = new CyclicRedundencyService(configuration);
    }


    public void tearDown() throws Exception {

        cyclicRedundencyService = null;
        configuration = null;

        super.tearDown();
    }


    public void test() throws Exception {

        try {

            cyclicRedundencyService.performCyclicRedundencyCheck();
            fail("expected CyclicRedundancyException");

        } catch (final CyclicRedundancyException e) {

            final String message = e.getMessage();

            /**
             * ..services depends on ..model and ..dao.hibernate.
             */
            assertTrue(message.contains("test.com.seventytwomiles.services"));
            assertTrue(message.contains("test.com.seventytwomiles.model"));
            assertTrue(message.contains("test.com.seventytwomiles.dao.hibernate"));
        }
    }
}
