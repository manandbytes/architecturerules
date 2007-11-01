package com.seventytwomiles.architecturerules.services;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;
import com.seventytwomiles.architecturerules.exceptions.NoPackagesFoundException;
import com.seventytwomiles.architecturerules.exceptions.SourceNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Checks for cyclic redundency among app packages in the source
 * folders.</p>
 *
 * @author mnereson
 * @see AbstractArchitecturalRules
 */
public class CyclicRedundencyService extends AbstractArchitecturalRules {


    /**
     * <p>Log to log with</p>
     *
     * @parameter log Log
     */
    private static final Log log = LogFactory.getLog(CyclicRedundencyService.class);


    /**
     * <p>Constructor instantiates a new <code>CyclicRedundencyService</code></p>
     *
     * @param configuraiton Configuration which contains the source directories
     * to inspect
     * @throws SourceNotFoundException when an required source directory does
     * not exist and when <tt>exception</tt>=<tt>"true"</tt> in the source
     * configuration
     * @throws NoPackagesFoundException when none of the source directories
     * exist and <tt>no-packages</tt>="<tt>ignore</tt>" in the sources
     * configuraiton
     */
    public CyclicRedundencyService(final Configuration configuraiton) throws SourceNotFoundException, NoPackagesFoundException {

        super(configuraiton);

        log.info("instanciating new CyclicRedundencyService");
    }


    /**
     * <p>Check all the packages in all of the source directories and search for
     * any cyclic redundenc/p>
     *
     * @throws CyclicRedundancyException when cyclic redundency is found
     */
    public void performCyclicRedundencyCheck() throws CyclicRedundancyException {

        log.info("cyclic reduendency check requested");

        log.debug("performing cyclic reduendency check");
        if (jdepend.containsCycles()) {

            log.warn("found cyclic redundendcies");
            throw new CyclicRedundancyException("cyclic redundency does exist");

        } else {

            log.info("found no cyclic redundencies");
        }

        log.info("cyclic redundency test completed");
    }
}
