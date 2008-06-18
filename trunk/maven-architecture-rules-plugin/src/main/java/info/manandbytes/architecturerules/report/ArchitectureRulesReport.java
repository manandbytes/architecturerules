package info.manandbytes.architecturerules.report;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

import org.codehaus.doxia.sink.Sink;
import org.codehaus.doxia.site.renderer.SiteRenderer;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <a href="http://docs.codehaus.org/display/MAVENUSER/Write+your+own+report+plugin">
 * Write your own report plugin</a>
 *
 * @author mn
 * @goal report
 * @phase site
 */
public class ArchitectureRulesReport
    extends AbstractMavenReport
{
    /**
     * <p>Directory where reports will go.</p>
     *
     * @parameter expression="${project.reporting.outputDirectory}"
     * @required
     * @readonly
     */
    private String outputDirectory;

    /**
     * @see AbstractMavenReport#executeReport(Locale)
     */
    @Override
    protected void executeReport( final Locale locale )
                          throws MavenReportException
    {
        // TODO Auto-generated method stub
        final Sink sink = getSink(  );
        sink.head(  );
        sink.title(  );
        sink.text( getBundle( locale ).getString( "title" ) );
        sink.title_(  );
        sink.head_(  );

        sink.body(  );
        sink.body_(  );

        sink.flush(  );
        sink.close(  );
    }

    /**
     * @see AbstractMavenReport#getOutputDirectory()
     */
    @Override
    protected String getOutputDirectory(  )
    {
        return outputDirectory;
    }

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @see AbstractMavenReport#getProject()
     */
    @Override
    protected MavenProject getProject(  )
    {
        return project;
    }

    /**
     * @component
     */
    private SiteRenderer siteRenderer;

    /**
     * @see AbstractMavenReport#getSiteRenderer()
     */
    @Override
    protected SiteRenderer getSiteRenderer(  )
    {
        return siteRenderer;
    }

    /**
     * @see AbstractMavenReport#getDescription(Locale)
     */
    public String getDescription( final Locale locale )
    {
        return getBundle( locale ).getString( "description" );
    }

    /**
     * @see AbstractMavenReport#getName(Locale)
     */
    public String getName( final Locale locale )
    {
        return getBundle( locale ).getString( "name" );
    }

    /**
     * <p>Name of a file with generated architecture rules report</p>
     *
     * @see org.apache.maven.reporting.AbstractMavenReport#getOutputName()
     */
    public String getOutputName(  )
    {
        return "architecture-rules";
    }

    private ResourceBundle getBundle( final Locale locale )
    {
        final String name = "architecture-rules-report";
        final ClassLoader classLoader = this.getClass(  ).getClassLoader(  );

        return ResourceBundle.getBundle( name, locale, classLoader );
    }
}
