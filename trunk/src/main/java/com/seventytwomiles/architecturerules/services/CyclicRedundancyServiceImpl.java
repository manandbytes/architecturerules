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
 * For more information visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;
import com.seventytwomiles.architecturerules.exceptions.NoPackagesFoundException;
import com.seventytwomiles.architecturerules.exceptions.SourceNotFoundException;
import jdepend.framework.JavaClass;
import jdepend.framework.JavaPackage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;



/**
 * <p>Checks for cyclic redundancy among application packages in the source
 * folders.</p>
 *
 * @author mikenereson
 * @see AbstractArchitecturalRules
 */
public class CyclicRedundancyServiceImpl extends AbstractArchitecturalRules
        implements CyclicRedundancyService {


    /**
     * <p>Log to log with</p>
     *
     * @parameter log Log
     */
    private static final Log log = LogFactory.getLog(
            CyclicRedundancyServiceImpl.class);


    /**
     * <p>Constructor instantiates a new <code>CyclicRedundancyService</code></p>
     *
     * @param configuration Configuration which contains the source directories
     * to inspect
     * @throws SourceNotFoundException when an required source directory does
     * not exist and when <tt>exception</tt>=<tt>"true"</tt> in the source
     * configuration
     * @throws NoPackagesFoundException when none of the source directories
     * exist and <tt>no-packages</tt>="<tt>ignore</tt>" in the sources
     * configuration
     */
    public CyclicRedundancyServiceImpl(final Configuration configuration) {

        super(configuration);

        log.info("instantiating new CyclicRedundancyService");
    }

    // --------------------- Interface CyclicRedundancyService ---------------------


    /**
     * <p>Check all the packages in all of the source directories and search for
     * any cyclic redundancy</p>
     */
    public void performCyclicRedundancyCheck() {

        log.info("cyclic redundancy check requested");

        final Collection packages = getPackages();

        /**
         *  TODO: report classes involved
         *  Report on which class is causing the cyclic redundancy.
         *  The information is all available from within the JavaPackage
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
             * afferents Collection is no longer a reference to the afferents,
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

            log.info("found no cyclic redundancies");

        } else {

            log.warn("found " + cycles.size() + " cyclic redundancies");

            throw new CyclicRedundancyException(
                    buildCyclicRedundancyMessage(cycles));
        }

        log.info("cyclic redundancy test completed");
    }


    /**
     * <p>Updates a Map, or puts a new record into a Map of a JavaPackage and
     * its cyclic dependency packages.</p>
     *
     * @param cycles Map of cycles already discovered.
     * @param javaPackage JavaPackage involved in a cyclic dependency
     * @param dependencies Collection of JavaPackages involved in a cyclic
     * dependency with the given javaPackage argument.
     */
    private void addCycle(final Map cycles, final JavaPackage javaPackage,
                          final Collection dependencies) {

        final boolean exists = cycles.containsKey(javaPackage);
        final Set cyclicPackages;

        if (exists) {
            /**
             * Get existing Set, this JavaPackage has already been
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
    private String buildCyclicRedundancyMessage(final Map cycles) {

        final StringBuffer message = new StringBuffer();
        message.append(cycles.size())
                .append(" cyclic dependencies found:")
                .append("\r\n")
                .append("\r\n\t");

        final Iterator entryIterator = cycles.entrySet().iterator();

        while (entryIterator.hasNext()) {

            final Map.Entry entry = (Map.Entry) entryIterator.next();

            final JavaPackage javaPackage = (JavaPackage) entry.getKey();
            final Set cyclicDependencies = (HashSet) entry.getValue();

            message.append("-- ")
                    .append(javaPackage.getName())
                    .append("\r\n\t");

            message.append("|  |")
                    .append(" (depends on one or more of)")
                    .append("\r\n\t");

            for (Iterator dependencyIterator = cyclicDependencies.iterator();
                 dependencyIterator.hasNext();) {

                final JavaPackage dependency
                        = (JavaPackage) dependencyIterator.next();

                final String listOfClasses = buildListOfClasses(javaPackage,
                        dependency);

                message.append("|  |");
                message.append("\r\n\t");
                message.append("|  |-- ");
                message.append(dependency.getName());
                message.append("\n" + listOfClasses);
                message.append("\r\n\t");

                if (!dependencyIterator.hasNext())
                    message.append("|").append("\r\n\t");
            }
        }

        return message.toString();
    }


    private String buildListOfClasses(
            final JavaPackage javaPackage, final JavaPackage dependency) {

        final StringBuffer listOfClasses = new StringBuffer();

        for (Iterator classesIterator = dependency.getClasses().iterator();
             classesIterator.hasNext();) {

            final JavaClass javaClass = (JavaClass) classesIterator.next();
            final Collection importedPackages = javaClass.getImportedPackages();

            if (importedPackages.contains(javaPackage))
                listOfClasses.append("\t|\t" + javaClass.getName() + "\n");
        }

        return listOfClasses.toString();
    }
}
