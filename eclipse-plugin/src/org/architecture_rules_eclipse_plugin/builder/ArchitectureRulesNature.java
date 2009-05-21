package org.architecture_rules_eclipse_plugin.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class ArchitectureRulesNature
    implements IProjectNature
{
    /**
     * ID of this project nature
     */
    public static final String NATURE_ID = "org.architecture-rules.architectureRulesNature";
    private IProject project;

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure(  )
                   throws CoreException
    {
        // TODO implement this 
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure(  )
                     throws CoreException
    {
        // TODO implement this 
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    public IProject getProject(  )
    {
        return project;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    public void setProject( IProject project )
    {
        this.project = project;
    }
}
