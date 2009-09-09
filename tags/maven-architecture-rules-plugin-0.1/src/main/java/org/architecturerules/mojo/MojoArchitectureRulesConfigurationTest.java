package org.architecturerules.mojo;


import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javassist.ClassPool;
import javassist.NotFoundException;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import org.architecturerules.AbstractArchitectureRulesConfigurationTest;
import org.architecturerules.api.configuration.ConfigurationFactory;
import org.architecturerules.configuration.xml.DigesterConfigurationFactory;
import org.architecturerules.domain.SourceDirectory;


/**
 * <p>todo: javadocs</p>
 *
 * @author mykola.nickishov
 * @author mnereson
 * @see TestCase
 */
public class MojoArchitectureRulesConfigurationTest extends AbstractArchitectureRulesConfigurationTest {

    /**
     * @author mn
     * @todo read configuration file from jars in classpath in {@link DigesterConfigurationFactory} and remove this
     * workaround
     */
    private final class FallbackDigesterConfigurationFactory extends DigesterConfigurationFactory {

        /**
         */
        public FallbackDigesterConfigurationFactory() {
            super("");
        }

        @Override
        protected String getConfigurationContent(final String configurationFileName) {

            InputStream configStream = getClass().getClassLoader().getResourceAsStream(ConfigurationFactory.DEFAULT_CONFIGURATION_FILE_NAME);
            String configAsString;

            try {

                configAsString = IOUtils.toString(configStream);

                return configAsString;
            } catch (IOException e) {

                /**
                 * just die if something goes wrong with our default
                 * configuration file
                 */
                throw new IllegalArgumentException(e);
            }
        }
    }

    /**
     * <p>todo: javadocs</p>
     *
     * @param file
     */
    public MojoArchitectureRulesConfigurationTest(final File file) {

        ConfigurationFactory factory;

        try {

            factory = new DigesterConfigurationFactory(file.getAbsolutePath());
        } catch (IllegalArgumentException e) {

            factory = new FallbackDigesterConfigurationFactory();
        }

        final boolean doCyclesTest = factory.doCyclicDependencyTest();

        getConfiguration().getRules().addAll(factory.getRules());
        getConfiguration().getSources().addAll(factory.getSources());
        getConfiguration().setDoCyclicDependencyTest(doCyclesTest);
    }

    /**
     * <p>todo: javadocs</p>
     *
     * @param mavenProject
     * @param log
     */
    public void addSourcesFromThisProject(final MavenProject mavenProject, final Log log) {

        SourceDirectory outputDirectory = new SourceDirectory(mavenProject.getBuild().getOutputDirectory());

        try {

            getConfiguration().getSources().add(outputDirectory);
            ClassPool.getDefault().appendPathList(outputDirectory.getPath());

            if (log.isDebugEnabled()) {

                String message = outputDirectory.getPath() + " added";
                log.debug(message);
            }
        } catch (NotFoundException e) {

            log.error(e);
        }
    }


    /**
     * <p>todo: javadocs</p>
     */
    @Override
    public void testArchitecture() {

        doTests();
    }
}
