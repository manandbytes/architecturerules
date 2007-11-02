package com.seventytwomiles.architecturerules;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.configuration.UnmodifiableConfiguration;
import com.seventytwomiles.architecturerules.configuration.xml.ConfigurationFactory;
import com.seventytwomiles.architecturerules.configuration.xml.DigesterConfigurationFactory;
import com.seventytwomiles.architecturerules.services.ArchitecturalRulesService;
import com.seventytwomiles.architecturerules.services.CyclicRedundencyService;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
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
    final Configuration configuration = new Configuration();


    /**
     * Getter for property {@link #configuration}.
     *
     * @return Value for property <tt>configuration</tt>.
     */
    public Configuration getConfiguration() {

        return configuration;
    }

    /* --------------------------------------------------------- constructors */


    protected AbstractArchitectureRulesConfigurationTest() {

        /* 1. load configuration */

        final String configurationFileName = getConfigurationFileName();

        final ConfigurationFactory configurationFactory = new DigesterConfigurationFactory(configurationFileName);

        configuration.getRules().addAll(configurationFactory.getRules());
        configuration.getSources().addAll(configurationFactory.getSources());
    }


    public boolean doTests() {

        final ArchitecturalRulesService rulesService;
        rulesService = new ArchitecturalRulesService(new UnmodifiableConfiguration(configuration));

        final boolean rulesResults = rulesService.performRulesTest();

        if (configuration.shouldDoCyclicDependencyTest()) {

            final CyclicRedundencyService redundencyService = new CyclicRedundencyService(new UnmodifiableConfiguration(configuration));
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
    abstract String getConfigurationFileName();


    /**
     * <p>Implement this method and call {@link #doTests}</p>
     */
    public abstract void testArchitecture();

}
