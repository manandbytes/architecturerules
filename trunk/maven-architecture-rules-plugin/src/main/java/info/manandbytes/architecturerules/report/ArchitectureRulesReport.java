package info.manandbytes.architecturerules.report;

// $Id$

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.sink.Sink;
import org.codehaus.doxia.site.renderer.SiteRenderer;

/**
 * <a
 * href="http://docs.codehaus.org/display/MAVENUSER/Write+your+own+report+plugin">
 * Write your own report plugin</a>
 * 
 * @author mn
 * @goal report
 * @phase site
 */
public class ArchitectureRulesReport extends AbstractMavenReport {
    /**
     * Directory where reports will go.
     * 
     * @parameter expression="${project.reporting.outputDirectory}"
     * @required
     * @readonly
     */
    private String outputDirectory;

    @Override
    protected void executeReport(final Locale locale)
            throws MavenReportException {
        // TODO Auto-generated method stub
        Sink sink = getSink();
        sink.head();
        sink.title();
        sink.text(getBundle(locale).getString("title"));
        sink.title_();
        sink.head_();

        sink.body();
        sink.body_();

        sink.flush();
        sink.close();
    }

    @Override
    protected String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    @Override
    protected MavenProject getProject() {
        return project;
    }

    /**
     * @component
     */
    private SiteRenderer siteRenderer;

    @Override
    protected SiteRenderer getSiteRenderer() {
        return siteRenderer;
    }

    public String getDescription(final Locale locale) {
        return getBundle(locale).getString("description");
    }

    public String getName(final Locale locale) {
        return getBundle(locale).getString("name");
    }

    /**
     * Name of a file with generated a-r report
     * 
     * @see org.apache.maven.reporting.AbstractMavenReport#getOutputName()
     */
    public String getOutputName() {
        return "architecture-rules";
    }

    private ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle("architecture-rules-report", locale,
                this.getClass().getClassLoader());
    }
}
