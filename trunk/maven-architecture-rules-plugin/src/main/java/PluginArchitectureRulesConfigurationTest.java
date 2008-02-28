import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.configuration.ConfigurationFactory;
import com.seventytwomiles.architecturerules.configuration.xml.DigesterConfigurationFactory;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import com.seventytwomiles.architecturerules.services.CyclicRedundancyService;
import com.seventytwomiles.architecturerules.services.CyclicRedundancyServiceImpl;
import com.seventytwomiles.architecturerules.services.RulesService;
import com.seventytwomiles.architecturerules.services.RulesServiceImpl;
import junit.framework.TestCase;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.Collection;
import java.util.List;



/**
 * <p>todo: javadocs</p>
 *
 * @author mykola.nickishov
 * @author mnereson
 * @see TestCase
 */
public class PluginArchitectureRulesConfigurationTest extends TestCase {


    /**
     * <p>Holds configuration settings</p>
     *
     * @paramater configuration Configuration
     */
    final private Configuration configuration = new Configuration();


    /**
     * <p>todo: javadocs</p>
     *
     * @param file
     */
    public PluginArchitectureRulesConfigurationTest(final File file) {

        final ConfigurationFactory factory
                = new DigesterConfigurationFactory(file.getAbsolutePath());

        final boolean doCyclesTest = factory.doCyclicDependencyTest();

        configuration.getRules().addAll(factory.getRules());
        configuration.getSources().addAll(factory.getSources());
        configuration.setDoCyclicDependencyTest(doCyclesTest);
    }


    /**
     * <p>todo: javadocs</p>
     *
     * @param mavenProject
     * @param log
     */
    public void addSourcesFromThisProject(final MavenProject mavenProject,
                                          final Log log) {

        final List<String> compileSourceRoots
                = mavenProject.getCompileSourceRoots();

        final Collection<SourceDirectory> currentSources
                = configuration.getSources();

        for (final String sourceRoot : compileSourceRoots) {

            final SourceDirectory sourceDirectory
                    = new SourceDirectory(sourceRoot);

            if (!currentSources.contains(sourceDirectory)) {

                currentSources.add(sourceDirectory);

                if (log.isDebugEnabled()) {

                    final String path = sourceDirectory.getPath();
                    final String message = path + " added";
                    log.debug(message);
                }
            }
        }
    }


    /**
     * <p>todo: javadocs</p>
     */
    public void testArchitecture() {

        final RulesService rulesService
                = new RulesServiceImpl(configuration);

        rulesService.performRulesTest();

        if (configuration.shouldDoCyclicDependencyTest()) {

            final CyclicRedundancyService redundancyService
                    = new CyclicRedundancyServiceImpl(configuration);

            redundancyService.performCyclicRedundancyCheck();
        }
    }

}
