package com.nereson.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see AbstractArchitecturalRules
 */
class CyclicRedundencyService extends AbstractArchitecturalRules {


    private static final Log log = LogFactory.getLog(CyclicRedundencyService.class);


    /**
     * Constructor instanciates a new CyclicRedundencyService
     * @throws SourceNotFoundException when a required source is not found
     */
    public CyclicRedundencyService() throws SourceNotFoundException {
        super();

        log.info("instanciating new CyclicRedundencyService");
    }


    /**
     * @throws CyclicRedundencyException when cyclic reduendency is found
     * @throws RuntimeException          when no packages were found with the given configuraiton if
     *                                   an exception is requested by the configuration
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
