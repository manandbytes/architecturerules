package info.manandbytes.architecturerules.mojo;

import com.seventytwomiles.architecturerules.configuration.ConfigurationFactory;
import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>Assert your architecture</p>
 *
 * @author mykola.nickishov
 * @goal assert
 * @phase test
 * @aggregator
 * @execute phase = "compile"
 * @requiresDependencyResolution compile
 */
public class ArchitectureRulesMojo
    extends AbstractMojo
{
    /**
     * <p>Name of the configuration file used by Architecture Rules.</p>
     *
     * @todo explain how search for configuration file works
     * @parameter alias="config" default-value="architecture-rules.xml"
     */
    private String configurationFileName = ConfigurationFactory.DEFAULT_CONFIGURATION_FILE_NAME;

    /**
     * For <a href="http://maven.apache.org/pom.html#Aggregation">Aggregation
     * (or Multi-Module) project</a> we don't run Architecture Rules assertions
     * by default. To change this behavior use <code>-Darchitecture-rules.skipRoot=false</code>
     *
     * @parameter default-value="true" expression="${architecture-rules.skipRoot}"
     */
    private boolean skipRoot;

    /**
     * Skip current project (i.e. in <a href="http://maven.apache.org/pom.html#Aggregation">Aggregation
     * (or Multi-Module) project</a>) and don't run Architecture Rules
     * assertions against it.
     *
     * @parameter default-value="false" expression="${architecture-rules.skip}"
     */
    private boolean skip;

    /**
     * <p> Reference to the Maven project that is being tested. </p>
     *
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject mavenProject;

    /**
     * <p> Reference to the Maven's reactor that is being tested. </p>
     *
     * @parameter expression="${reactorProjects}"
     * @readonly
     */
    private Collection<MavenProject> reactorProjects;

    /**
     * <p>Entry point to this plugin. Finds the configuration files, constructs
     * the tests, and executes the tests.</p>
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     * @see AbstractMojo#execute()
     */
    public void execute(  )
                 throws MojoExecutionException, MojoFailureException
    {
        final Collection<CyclicRedundancyException> rulesExceptions = new LinkedList<CyclicRedundancyException>(  );

        final Log log = getLog(  );

        List<Resource> testResources;
        MojoArchitectureRulesConfigurationTest test;

        for ( final MavenProject project : reactorProjects )
        {
            if ( log.isDebugEnabled(  ) )
            {
                log.debug( "process " + project );
            }

            /**
             * Skip the project resources, if the project is the parent
             * project and the parent project should be skipped.
             **/
            final boolean isAggregated =
                ! project.getModules(  ).isEmpty(  ) || "pom".equals( project.getPackaging(  ) );

            if ( ( isAggregated && skipRoot ) || ( ! isAggregated && skip ) )
            {
                if ( log.isDebugEnabled(  ) )
                {
                    log.debug( "aggregated = " + isAggregated + "; skip " + project );
                }

                continue;
            }

            testResources = project.getTestResources(  );
            includeConfigurationFile( testResources );

            File configFile;

            try
            {
                configFile = findConfigurationFile( testResources, log );
            } catch ( FileNotFoundException e1 )
            {
                log.warn( e1.getMessage(  ) );
                log.warn( "fallback to use default config" );

                configFile = new File( ConfigurationFactory.DEFAULT_CONFIGURATION_FILE_NAME );
            }

            test = new MojoArchitectureRulesConfigurationTest( configFile );
            test.addSourcesFromThisProject( project, log );

            try
            {
                test.testArchitecture(  );
            } catch ( CyclicRedundancyException e )
            {
                rulesExceptions.add( e );
            }
        }

        if ( ! rulesExceptions.isEmpty(  ) )
        {
            throw new MojoExecutionException( rulesExceptions, "", "" );
        }
    }

    /**
     * Find the file with given {@link #configurationFileName} in the
     * testResources.
     *
     * @param testResources List<Resource>
     * @param log maven log to log with
     * @return File which may exist
     * @throws FileNotFoundException
     */
    private File findConfigurationFile( final List<Resource> testResources, final Log log )
                                throws FileNotFoundException
    {
        for ( final Resource resource : testResources )
        {
            final String directory = resource.getDirectory(  );
            // @todo I'm not sure if a resource's directory may be null
            assert( directory != null );

            if ( log.isDebugEnabled(  ) )
            {
                log.debug( "try to find configuration in " + directory );
            }

            final boolean resourceContainsConfigFile = resource.getIncludes(  ).contains( configurationFileName );

            final String fileName = directory + File.separator + configurationFileName;

            final File configFile = new File( fileName );

            final StringBuffer message = new StringBuffer(  );
            message.append( configurationFileName ).append( " " );

            if ( resourceContainsConfigFile && configFile.exists(  ) && configFile.isFile(  ) &&
                     configFile.canRead(  ) )
            {
                if ( log.isDebugEnabled(  ) )
                {
                    message.append( "found in the directory " );
                    message.append( configFile.getParent(  ) );

                    log.debug( message.toString(  ) );
                }

                return configFile;
            } else if ( log.isDebugEnabled(  ) )
            {
                message.append( "not found" );
                log.debug( message.toString(  ) );
            }
        }

        throw new FileNotFoundException( configurationFileName + " not found in " + testResources );
    }

    /**
     * <p>todo: javadocs</p>
     *
     * @param testResources
     */
    private void includeConfigurationFile( final List<Resource> testResources )
    {
        for ( final Resource rawResource : testResources )
        {
            if ( rawResource.getDirectory(  ) != null )
            {
                rawResource.addInclude( configurationFileName );
            }
        }
    }

    String getConfigurationFileName(  )
    {
        return configurationFileName;
    }
}
