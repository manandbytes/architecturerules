package com.seventytwomiles.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see AbstractArchitecturalRules
 */
class CyclicRedundencyService extends AbstractArchitecturalRules {


    /**
     * <p>todo: javadoc this</p>
     *
     * @parameter
     */
    private static final Log log = LogFactory.getLog(CyclicRedundencyService.class);


    /**
     * <p>Constructor instanciates a new <code>CyclicRedundencyService</code></p>
     *
     * @throws SourceNotFoundException when a required source is not found
     * @throws NoPackagesFoundException when no packages are found in the given
     * configuraiton for sources
     */
    public CyclicRedundencyService() throws SourceNotFoundException, NoPackagesFoundException {

        super();

        log.info("instanciating new CyclicRedundencyService");
    }


    /**
     * <p></p>
     *
     * @throws CyclicRedundencyException when cyclic reduendency is found
     * @throws RuntimeException when no packages were found with the given
     * configuraiton if an exception is requested by the configuration
     */
    public void performCyclicRedundencyCheck() throws CyclicRedundencyException {

        log.info("cyclic reduendency check requested");

        log.debug("prforming cyclic reduendency check");
        if (jdepend.containsCycles()) {

            log.warn("found cyclic redundendcies");
            throw new CyclicRedundencyException("cyclic redundency does exist");

        } else {

            log.info("found no cyclic redundencies");
        }

        log.info("cyclic redundency test completed");
    }
}
