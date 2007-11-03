package com.seventytwomiles.architecturerules.services;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;
import com.seventytwomiles.architecturerules.exceptions.NoPackagesFoundException;
import com.seventytwomiles.architecturerules.exceptions.SourceNotFoundException;
import jdepend.framework.JavaPackage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * <p>Checks for cyclic redundency among app packages in the source
 * folders.</p>
 *
 * @author mnereson
 * @see AbstractArchitecturalRules
 */
public class CyclicRedendencyServiceImpl extends AbstractArchitecturalRules implements CyclicRedundencyService {


    /**
     * <p>Log to log with</p>
     *
     * @parameter log Log
     */
    private static final Log log = LogFactory.getLog(CyclicRedendencyServiceImpl.class);


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
    public CyclicRedendencyServiceImpl(final Configuration configuraiton) throws SourceNotFoundException, NoPackagesFoundException {

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


        final Collection packages = getPackages();

        final Map efferentMap = getEfferentMap(packages);

        Map cycles = processEfferntMapForCycles(efferentMap);

        if (cycles.isEmpty()) {

            log.info("found no cyclic redundencies");

        } else {

            log.warn("found cyclic redundendcies");

            final String message = buildCyclicReduncencyMessage(cycles);
            throw new CyclicRedundancyException(message);
        }

        log.info("cyclic redundency test completed");
    }


    private String buildCyclicReduncencyMessage(final Map cyclesMap) {

        StringBuffer message = new StringBuffer();
        message.append("cylic depencencies found:").append("\r\n").append("\r\n\t");

        final Iterator entryIterator = cyclesMap.entrySet().iterator();
        while (entryIterator.hasNext()) {

            final Map.Entry entry = (Map.Entry) entryIterator.next();

            final String packageName = entry.getKey().toString();
            final List cyclicDependencies = (ArrayList) entry.getValue();

            message.append("-- ").append(packageName).append("\r\n\t");

            for (Iterator dependencyIterator = cyclicDependencies.iterator();
                 dependencyIterator.hasNext();) {

                final String dependency = (String) dependencyIterator.next();

                message.append("¦  ¦").append("\r\n\t");
                message.append("¦  ¦-- ").append(dependency).append("\r\n\t");

                if (!dependencyIterator.hasNext())
                    message.append("¦").append("\r\n\t");
            }

            if (entryIterator.hasNext())
                message.append("¦").append("\r\n\t");
        }

        return message.toString();
    }


    private Map processEfferntMapForCycles(final Map efferentMap) {

        final Map cycles = new HashMap();

        final Iterator entryIterator = efferentMap.entrySet().iterator();
        while (entryIterator.hasNext()) {

            final Map.Entry entry = (Map.Entry) entryIterator.next();
            final String packageName = entry.getKey().toString();
            final List efferents = (ArrayList) entry.getValue();

            final List cyclicDependencies = new ArrayList();
            for (Iterator efferentIterator = efferents.iterator();
                 efferentIterator.hasNext();) {

                final String efferent = (String) efferentIterator.next();

                if (efferentMap.containsKey(efferent)) {
                    final List dependencies = (ArrayList) efferentMap.get(efferent);
                    if (dependencies.contains(packageName))
                        cyclicDependencies.add(efferent);
                }
            }

            if (!cyclicDependencies.isEmpty())
                cycles.put(packageName, cyclicDependencies);
        }

        return cycles;
    }


    /**
     * <p>Associates a package to all of its dependencies.</p>
     *
     * @param packages Collection of <code>JavaPackages</code>
     * @return Map<String, List<String>> name of package a list of names of
     *         packages that it depends on.
     */
    private Map getEfferentMap(final Collection packages) {

        /**
         * Map<String, List<String>>
         */
        final Map efferentMap = new HashMap(); // package name, list of dpendency package names
        JavaPackage javaPackage; // Reusable JavaPackge to use inside of interation

        log.debug("performing cyclic reduendency check");
        for (final Iterator packageIterator = packages.iterator();
             packageIterator.hasNext();) {

            javaPackage = (JavaPackage) packageIterator.next();

            final List efferents = new ArrayList();
            JavaPackage efferent;

            /**
             * When cycles exist, build a Map of [package, List of cycles]
             */
            for (final Iterator efferentIterator = javaPackage.getEfferents().iterator();
                 efferentIterator.hasNext();) {

                efferent = (JavaPackage) efferentIterator.next();
                efferents.add(efferent.getName());
            }

            /* only added when there are efferents */
            if (!efferents.isEmpty())
                efferentMap.put(javaPackage.getName(), efferents);
        }

        return efferentMap;
    }
}
