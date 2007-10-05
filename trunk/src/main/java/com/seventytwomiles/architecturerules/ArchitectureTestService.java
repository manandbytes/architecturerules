package com.seventytwomiles.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see AbstractArchitecturalRules
 */
public class ArchitectureTestService {


    /**
     * <p>todo: javadoc this</p>
     *
     * @parameter
     */
    private static final Log log = LogFactory.getLog(ArchitectureTestService.class);


    /**
     * <p></p>
     */
    public ArchitectureTestService() {
        log.info("instanciating new ArchitectureTestService");
    }


    /**
     * <p></p>
     *
     * @throws CyclicRedundencyException
     * @throws DependencyConstraintException
     * @throws SourceNotFoundException
     * @throws NoPackagesFoundException
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
