import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;



/**
 * <p></p>
 *
 * @author mykola.nickishov
 * @goal rules
 */
public class ArchitectureRulesMojo extends AbstractMojo {


    /**
     * <p>Defaults to the recommended <samp>architecture-rules.xml</samp></p>
     *
     * @parameter alias="config"
     * @required
     */
    private String configurationFileName = "architecture-rules.xml";


    /**
     * <p>Reference to the maven project that is being tested.</p>
     *
     * @parameter expression="${project}"
     */
    private MavenProject mavenProject;


    /**
     * <p>Entry point to this plugin. Finds the configuration files, constructs
     * the tests, and executes the tests.</p>
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     * @see AbstractMojo#execute()
     */
    public void execute()
            throws MojoExecutionException, MojoFailureException {

        final List<Resource> testResources
                = mavenProject.getTestResources();

        final Log log = getLog();

        includeConfigurationFile(testResources);

        final File configFile = findConfigurationFile(testResources, log);

        final PluginArchitectureRulesConfigurationTest architectureTest
                = new PluginArchitectureRulesConfigurationTest(configFile);

        architectureTest.addSourcesFromThisProject(mavenProject, log);
        architectureTest.testArchitecture();
    }


    /**
     * Find the file with given {@link #configurationFileName} in the
     * testResources.
     *
     * @param testResources List<Resource>
     * @param log maven log to log with
     * @return File which may or may not exist
     */
    private File findConfigurationFile(final List<Resource> testResources,
                                       final Log log) {

        File configFile = new File("");

        for (final Resource resource : testResources) {

            final String directory = resource.getDirectory();

            if (resource.getIncludes().contains(configurationFileName)
                    && directory != null) {

                final String fileName
                        = directory + File.separator + configurationFileName;

                configFile = new File(fileName);

                if (log.isDebugEnabled()) {

                    final StringBuffer message = new StringBuffer();
                    message.append(configurationFileName).append(" ");

                    if (configFile.canRead()) {

                        message.append("found in the directory ");
                        message.append(configFile.getParent());

                    } else {

                        message.append("not found");
                    }

                    log.debug(message.toString());
                }

                return configFile;
            }
        }

        return configFile;
    }


    /**
     * <p></p>
     *
     * @param testResources
     */
    private void includeConfigurationFile(
            final List<Resource> testResources) {

        for (final Resource rawResource : testResources) {

            if (rawResource.getDirectory() != null)
                rawResource.addInclude(configurationFileName);
        }
    }
}