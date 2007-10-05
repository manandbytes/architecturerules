package com.seventytwomiles.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


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
     * @throws SourceNotFoundException when an required source directory does
     * not exist and when <tt>exception</tt>=<tt>"true"</tt> in the source
     * configuration
     * @throws NoPackagesFoundException when none of the source directories
     * exist and <tt>no-packages</tt>="<tt>ignore</tt>" in the sources
     * configuraiton
     * @throws DependencyConstraintException when a rule is broken
     * @throws CyclicRedundencyException when cyclic redundency is found
     */
    public void checkArchitecture() throws CyclicRedundencyException, DependencyConstraintException, SourceNotFoundException, NoPackagesFoundException {

        log.debug("checking to see if cyclic dependency test is requested by configuration");

        /**
         * Check for cyclical reduendencies if confiruation calls for it
         * @throws CyclicRedundencyException when cyclic redundency does exist
         */
        if (ConfigurationFactory.doCyclicDependencyTest()) {

            log.debug("cyclic reduendency test is requested");

            final CyclicRedundencyService redundencyService = new CyclicRedundencyService();
            redundencyService.performCyclicRedundencyCheck();

        } else {

            log.debug("cyclic reduendency test is not requested. ");
            log.debug("to enable this feature, set <configuration> ... <cyclicalDependency test=\"true\"/> ... </configuration>");
        }


        final ArchitecturalRulesService architecturalRulesService = new ArchitecturalRulesService();
        architecturalRulesService.performRulesTest();
    }
}
