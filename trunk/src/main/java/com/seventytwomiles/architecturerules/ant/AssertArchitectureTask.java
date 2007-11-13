package com.seventytwomiles.architecturerules.ant;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.configuration.UnmodifiableConfiguration;
import com.seventytwomiles.architecturerules.configuration.xml.ConfigurationFactory;
import com.seventytwomiles.architecturerules.configuration.xml.DigesterConfigurationFactory;
import com.seventytwomiles.architecturerules.services.CyclicRedundencyService;
import com.seventytwomiles.architecturerules.services.CyclicRedundencyServiceImpl;
import com.seventytwomiles.architecturerules.services.RulesService;
import com.seventytwomiles.architecturerules.services.RulesServiceImpl;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;


/**
 * <p>todo: javadocs</p>
 *
 * @author mikenereson
 */
public class AssertArchitectureTask extends Task {


    /**
     * <p></p>
     *
     * @parameter configurationFileName String
     */
    private String configurationFileName;


    /**
     * <p></p>
     *
     * @parameter configuration Configuration
     */
    final private Configuration configuration = new Configuration();


    /**
     * @throws BuildException
     * @see Task#execute()
     */
    public void execute() throws BuildException {
        super.execute();

        if (null == configurationFileName || "".equals(configurationFileName))
            throw new IllegalStateException("set configurationFileName property");

        /**
         * 1. load configuration
         */

        final ConfigurationFactory configurationFactory = new DigesterConfigurationFactory(configurationFileName);

        configuration.getRules().addAll(configurationFactory.getRules());
        configuration.getSources().addAll(configurationFactory.getSources());
        configuration.setDoCyclicDependencyTest(configurationFactory.doCyclicDependencyTest());
        configuration.setThrowExceptionWhenNoPackages(configurationFactory.throwExceptionWhenNoPackages());

        /**
         * 2. assert configuration rules
         */
        final RulesService rulesService;
        rulesService = new RulesServiceImpl(new UnmodifiableConfiguration(configuration));
        rulesService.performRulesTest();

        /**
         * 3. check for cyclic dependency, if requested
         */
        if (configuration.shouldDoCyclicDependencyTest()) {

            final CyclicRedundencyService redundencyService = new CyclicRedundencyServiceImpl(new UnmodifiableConfiguration(configuration));
            redundencyService.performCyclicRedundencyCheck();
        }
    }


    /**
     * Setter for property 'configurationFileName'.
     *
     * @param configurationFileName Value to set for property
     * 'configurationFileName'.
     */
    public void setConfigurationFileName(final String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }
}
