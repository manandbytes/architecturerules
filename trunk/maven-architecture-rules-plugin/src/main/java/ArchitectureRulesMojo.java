import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.configuration.ConfigurationFactory;
import com.seventytwomiles.architecturerules.configuration.UnmodifiableConfiguration;
import com.seventytwomiles.architecturerules.configuration.xml.DigesterConfigurationFactory;
import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import com.seventytwomiles.architecturerules.services.CyclicRedundancyService;
import com.seventytwomiles.architecturerules.services.CyclicRedundancyServiceImpl;
import com.seventytwomiles.architecturerules.services.RulesService;
import com.seventytwomiles.architecturerules.services.RulesServiceImpl;

/**
 * @goal rules
 */
public class ArchitectureRulesMojo extends AbstractMojo {
    private final class MyArchitectureRulesConfigurationTest extends TestCase {
        final private Configuration configuration = new Configuration();

        @SuppressWarnings("unchecked")
        private MyArchitectureRulesConfigurationTest(final File url) {
            ConfigurationFactory configurationFactory = new DigesterConfigurationFactory(
                    "") {
                @Override
                protected String getConfigurationAsXml(
                        String configurationFileName) {
                    try {
                        String content = FileUtils.readFileToString(url,
                                "UTF-8");
                        getLog().debug("content = " + content);
                        return content;
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }

                @Override
                public List getSources() {
                    List<SourceDirectory> sourcesToReturn = super.getSources();
                    List<String> compileSourceRoots = getMavenProject()
                            .getCompileSourceRoots();
                    for (String sourceRoot : compileSourceRoots) {
                        SourceDirectory sourceDirectory = new SourceDirectory(
                                sourceRoot);
                        if (!sourcesToReturn.contains(sourceDirectory)) {
                            sourcesToReturn.add(sourceDirectory);
                            if (getLog().isDebugEnabled())
                                getLog().debug(
                                        sourceDirectory.getPath()
                                                + " source directory added");
                        }
                    }
                    return sourcesToReturn;
                }
            };
            configuration.getRules().addAll(configurationFactory.getRules());
            configuration.getSources()
                    .addAll(configurationFactory.getSources());
            configuration.setDoCyclicDependencyTest(configurationFactory
                    .doCyclicDependencyTest());
        }

        public void testArchitecture() {
            final RulesService rulesService = new RulesServiceImpl(
                    new UnmodifiableConfiguration(configuration));
            rulesService.performRulesTest();
            if (configuration.shouldDoCyclicDependencyTest()) {
                final UnmodifiableConfiguration unmodifiableConfiguration = new UnmodifiableConfiguration(
                        configuration);
                final CyclicRedundancyService redundancyService = new CyclicRedundancyServiceImpl(
                        unmodifiableConfiguration);
                redundancyService.performCyclicRedundancyCheck();
            }
        }
    }

    /**
     * @parameter alias="config"
     * @required
     */
    String configurationFileName = null;

    public void execute() throws MojoExecutionException, MojoFailureException {
        List<Resource> testResources = getMavenProject().getTestResources();
        includeConfigurationFile(testResources);
        File configFile = new File("");
        for (Resource r : testResources) {
            String directory = r.getDirectory();
            if (r.getIncludes().contains(configurationFileName)
                    && directory != null)
                configFile = new File(directory + File.separator
                        + configurationFileName);
        }
        if (getLog().isDebugEnabled()) {
            getLog().debug(
                    configurationFileName
                            + " "
                            + (configFile.canRead() ? "found in the directory "
                                    + configFile.getParent() : "not found"));
        }
        new MyArchitectureRulesConfigurationTest(configFile).testArchitecture();
    }

    private void includeConfigurationFile(final List<Resource> testResources) {
        for (Resource rawResource : testResources) {
            if (rawResource.getDirectory() != null)
                rawResource.addInclude(configurationFileName);
        }
    }

    /**
     * @parameter expression="${project}"
     */
    private MavenProject mavenProject;

    public MavenProject getMavenProject() {
        return mavenProject;
    }
}
