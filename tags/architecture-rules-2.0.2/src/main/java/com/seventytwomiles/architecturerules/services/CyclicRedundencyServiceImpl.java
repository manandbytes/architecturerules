package com.seventytwomiles.architecturerules.services;

/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more infomration visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


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
 * @author mikenereson
 * @see AbstractArchitecturalRules
 */
public class CyclicRedundencyServiceImpl extends AbstractArchitecturalRules implements CyclicRedundencyService {


    /**
     * <p>Log to log with</p>
     *
     * @parameter log Log
     */
    private static final Log log = LogFactory.getLog(CyclicRedundencyServiceImpl.class);


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
    public CyclicRedundencyServiceImpl(final Configuration configuraiton) {

        super(configuraiton);

        log.info("instanciating new CyclicRedundencyService");
    }


    /**
     * <p>Check all the packages in all of the source directories and search for
     * any cyclic redundenc/p>
     */
    public void performCyclicRedundencyCheck() {

        log.info("cyclic reduendency check requested");

        final Collection packages = getPackages();

        /**
         *  TODO: report classes involved
         *  Report on which class is causing the cyclic redundancy.
         *  The information is all available from withing the JavaPackage
         *  class.
         */

        /**
         * Holds any cycles that are found. The structure of this Map is
         * <JavaPackage, Set> where the first String is the name of the package,
         * and the Set contains JavaPackge of packages that are in a cycle with
         * the named package.
         */
        final Map cycles = new HashMap();

        JavaPackage javaPackage; /* place holder to iterate over */

        for (final Iterator packageIterator = packages.iterator();
             packageIterator.hasNext();) {

            javaPackage = (JavaPackage) packageIterator.next();

            final Collection afferents = javaPackage.getAfferents();
            final Collection efferents = javaPackage.getEfferents();

            /**
             * afferents Collection is no longer a reference to the affernts,
             * but now a Collection of packages involved in a cyclic dependency
             * with the javaPackage. By calling retainAll the afferents
             * Collection now contains only those javaPackages that were in both
             * the efferents and afferents Collections. And by definition, when
             * a package is both uses the given package, and used by the given
             * package, a cyclic dependency exists.
             */
            afferents.retainAll(efferents);

            final boolean cyclesFound = afferents.size() > 0;

            if (cyclesFound)
                addCycle(cycles, javaPackage, afferents);
        }

        if (cycles.isEmpty()) {

            log.info("found no cyclic redundencies");

        } else {

            log.warn("found " + cycles.size() + " cyclic redundendcies");
            throw new CyclicRedundancyException(buildCyclicReduncencyMessage(cycles));
        }

        log.info("cyclic redundency test completed");
    }


    /**
     * <p>Updates a Map, or puts a new record into a Map of a JavaPackage and
     * its cyclic dependency packages.</p>
     *
     * @param cycles Map of cycles already discovered.
     * @param javaPackage JavaPackage involved in a clyclic dependency
     * @param dependencies Collection of JavaPackages involved in a cyclic
     * dependency with the given javaPackage argument.
     */
    private void addCycle(final Map cycles, final JavaPackage javaPackage, final Collection dependencies) {

        final boolean exists = cycles.containsKey(javaPackage);
        final Set cyclicPackages;

        if (exists) {

            /**
             * Get exisiting Set, this JavaPackage has already been
             * discovered for being in a cycle
             */
            final Object value = cycles.get(javaPackage);
            cyclicPackages = (HashSet) value;

        } else {

            /**
             * Build a new Set
             */
            cyclicPackages = new HashSet();
        }

        cyclicPackages.addAll(dependencies);
        cycles.put(javaPackage, cyclicPackages);
    }


    /**
     * <p>Builds a message detailing all of the java packages that are involved
     * in a cyclic dependency and the packages involved with.</p>
     *
     * @param cycles Map of cycles discovered.
     * @return String a complete message detailing all of the cyclic
     *         dependencies found.
     */
    private String buildCyclicReduncencyMessage(final Map cycles) {

        StringBuffer message = new StringBuffer();
        message.append("cylic depencencies found:").append("\r\n").append("\r\n\t");

        final Iterator entryIterator = cycles.entrySet().iterator();
        while (entryIterator.hasNext()) {

            final Map.Entry entry = (Map.Entry) entryIterator.next();

            final JavaPackage javaPackage = (JavaPackage) entry.getKey();
            final Set cyclicDependencies = (HashSet) entry.getValue();

            message.append("-- ").append(javaPackage.getName()).append("\r\n\t");

            for (Iterator dependencyIterator = cyclicDependencies.iterator();
                 dependencyIterator.hasNext();) {

                final JavaPackage dependency = (JavaPackage) dependencyIterator.next();

                message.append("¦  ¦").append("\r\n\t");
                message.append("¦  ¦-- ").append(dependency.getName()).append("\r\n\t");

                if (!dependencyIterator.hasNext())
                    message.append("¦").append("\r\n\t");
            }

            if (entryIterator.hasNext())
                message.append("¦").append("\r\n\t");
        }

        return message.toString();
    }
}
