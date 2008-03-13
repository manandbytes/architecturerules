package info.manandbytes.architecturerules.mojo;


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

import javassist.ClassPool;
import javassist.NotFoundException;

import javax.management.ServiceNotFoundException;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;



/**
 * <p>todo: javadocs</p>
 *
 * @author mykola.nickishov
 * @author mnereson
 * @see TestCase
 */
public class MojoArchitectureRulesConfigurationTest extends TestCase {


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
    public MojoArchitectureRulesConfigurationTest(final File file) {

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

        final Collection<SourceDirectory> currentSources
                = new LinkedList<SourceDirectory>();

        appendBaseDir(mavenProject.getBasedir());

        SourceDirectory outputDirectory = new SourceDirectory(mavenProject
                .getBuild().getOutputDirectory());
        try {
            currentSources.add(outputDirectory);
            ClassPool.getDefault().appendPathList(outputDirectory.getPath());
            if (log.isDebugEnabled()) {
                String message = outputDirectory.getPath() + " added";
                log.debug(message);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * <p> Add the current project's base directory to all paths from the
     * configuration file </p>
     *
     * <p> Configuration file uses relative paths from the root of the project.
     * Invoking build not from the project's root directory (i.e. <code>mvn -f
     * /some/pom.xml</code>) causes {@link ServiceNotFoundException}} </p>
     *
     * @param baseDir File
     */
    private void appendBaseDir(final File baseDir) {

        Collection<SourceDirectory>
                sourcesFromConfig = configuration.getSources();

        /**
         * TODO: this loop setPath on baseDir +. What is it calling on
         * baseDir? toString? lets be explicit in whatever it is...
         */
        for (SourceDirectory directory : sourcesFromConfig) {
            directory.setPath(baseDir + File.separator + directory.getPath());
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
