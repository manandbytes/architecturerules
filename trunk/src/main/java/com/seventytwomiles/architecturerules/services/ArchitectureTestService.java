package com.seventytwomiles.architecturerules.services;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.seventytwomiles.architecturerules.exceptions.SourcesNotFoundException;
import com.seventytwomiles.architecturerules.exceptions.DependencyConstraintException;
import com.seventytwomiles.architecturerules.exceptions.NoPackagesFoundException;
import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;


/**
 * <p>Tests </p>
 *
 * @author mnereson
 * @see AbstractArchitecturalRules
 */
public class ArchitectureTestService {


    /**
     * <p>Log to log with</p>
     *
     * @parameter log Log
     */
    private static final Log log = LogFactory.getLog(ArchitectureTestService.class);


    /**
     * <p>Instanciates a new <code>ArchitectureTestService</code></p>
     */
    public ArchitectureTestService() {
        log.info("instanciating new ArchitectureTestService");
    }


    /**
     * <p>Check known architecture for any cyclical redundencies</p>
     *
     * @throws SourcesNotFoundException when an required source directory does
     * not exist and when <tt>exception</tt>=<tt>"true"</tt> in the source
     * configuration
     * @throws NoPackagesFoundException when none of the source directories
     * exist and <tt>no-packages</tt>="<tt>ignore</tt>" in the sources
     * configuraiton
     * @throws DependencyConstraintException when a rule is broken
     * @throws CyclicRedundancyException when cyclic redundency is found
     */
    public void checkArchitecture() throws CyclicRedundancyException, DependencyConstraintException, SourcesNotFoundException, NoPackagesFoundException {


    }
}
