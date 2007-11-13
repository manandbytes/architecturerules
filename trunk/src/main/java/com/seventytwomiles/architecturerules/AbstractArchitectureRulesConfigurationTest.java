package com.seventytwomiles.architecturerules;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.configuration.UnmodifiableConfiguration;
import com.seventytwomiles.architecturerules.configuration.xml.ConfigurationFactory;
import com.seventytwomiles.architecturerules.configuration.xml.DigesterConfigurationFactory;
import com.seventytwomiles.architecturerules.services.CyclicRedundencyService;
import com.seventytwomiles.architecturerules.services.CyclicRedundencyServiceImpl;
import com.seventytwomiles.architecturerules.services.RulesService;
import com.seventytwomiles.architecturerules.services.RulesServiceImpl;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>todo: javadocs</p>
 *
 * @author mikenereson
 * @noinspection PointlessBooleanExpression
 */
public abstract class AbstractArchitectureRulesConfigurationTest extends TestCase {


    private static final Log log = LogFactory.getLog(AbstractArchitectureRulesConfigurationTest.class);

    /* -------------------------------------------------- fields and mutators */

    /**
     * <p></p>
     *
     * @parameter configuration Configuration
     */
    final private Configuration configuration = new Configuration();


    /**
     * Getter for property {@link #configuration}.
     *
     * @return Value for property <tt>configuration</tt>.
     */
    Configuration getConfiguration() {

        return configuration;
    }

    /* --------------------------------------------------------- constructors */


    protected AbstractArchitectureRulesConfigurationTest() {

        /* 1. load configuration if a configuration file name was provided */

        final String configurationFileName = getConfigurationFileName();

        final ConfigurationFactory configurationFactory;

        if (configurationFileName != null && configurationFileName.length() > 0) {

            configurationFactory = new DigesterConfigurationFactory(configurationFileName);

            configuration.getRules().addAll(configurationFactory.getRules());
            configuration.getSources().addAll(configurationFactory.getSources());
        }
    }


    boolean doTests() {

        final RulesService rulesService;
        rulesService = new RulesServiceImpl(new UnmodifiableConfiguration(configuration));

        final boolean rulesResults = rulesService.performRulesTest();

        if (configuration.shouldDoCyclicDependencyTest()) {

            final CyclicRedundencyService redundencyService = new CyclicRedundencyServiceImpl(new UnmodifiableConfiguration(configuration));
            redundencyService.performCyclicRedundencyCheck();
        }

        return rulesResults;
    }

    /* ----------------------------------------------------- abstract methods */


    /**
     * <p>Get the name of the xml configuration file that is located in the
     * classpath.</p>
     *
     * <p>Recommend <samp>architecture-rules.xml</samp></p>
     *
     * @return String name of the xml file including <samp>.xml</smmp>
     */
    protected String getConfigurationFileName() {
        return "";
    }


    /**
     * <p>Implement this method and call {@link #doTests}</p>
     */
    abstract void testArchitecture();

}
