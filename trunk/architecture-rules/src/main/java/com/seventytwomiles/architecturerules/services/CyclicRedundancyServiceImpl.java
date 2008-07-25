/**
 * Copyright 2007, 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com
 */
package com.seventytwomiles.architecturerules.services;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;
import com.seventytwomiles.architecturerules.exceptions.NoPackagesFoundException;
import com.seventytwomiles.architecturerules.exceptions.SourceNotFoundException;
import javassist.ClassPool;
import javassist.NotFoundException;
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
public class CyclicRedundancyServiceImpl extends AbstractArchitecturalRules implements CyclicRedundancyService {

    /**
     * <p>Log to log with</p>
     *
     * @parameter log Log
     */
    protected static final Log log = LogFactory.getLog(CyclicRedundancyServiceImpl.class);


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


    /**
     * <p>Check all the packages in all of the source directories and search for
     * any cyclic redundancy</p>
     */
    public void performCyclicRedundancyCheck() {

        log.info("cyclic redundancy check requested");

        final Collection<JavaPackage> packages = getPackages();


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
        final Map<JavaPackage, Set<JavaPackage>> cycles = new HashMap<JavaPackage, Set<JavaPackage>>();

        for (final JavaPackage javaPackage : packages) {

            final Collection<JavaPackage> afferents = javaPackage.getAfferents();
            final Collection<JavaPackage> efferents = javaPackage.getEfferents();

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

            if (cyclesFound) {

                addCycle(cycles, javaPackage, afferents);
            }
        }

        if (cycles.isEmpty()) {

            log.info("found no cyclic redundancies");
        } else {

            log.warn("found " + cycles.size() + " cyclic redundancies");

            final CyclicRedundancyException cyclicRedundancyException = buildCyclicRedundancyException(cycles);

            throw cyclicRedundancyException;
        }

        log.info("cyclic redundancy test completed");
    }


    private CyclicRedundancyException buildCyclicRedundancyException(final Map<JavaPackage, Set<JavaPackage>> cycles) {

        final String message = buildCyclicRedundancyMessage(cycles);
        final CyclicRedundancyException exception = new CyclicRedundancyException(message);
        final Map<String, Set<String>> cycleStrings = exception.getCycles();

        for (final Map.Entry<JavaPackage, Set<JavaPackage>> cycle : cycles.entrySet()) {

            final String packageName = cycle.getKey().getName();
            final Set<JavaPackage> packageCycles = cycle.getValue();

            final Set<String> cyclicPackages;

            if (cycleStrings.containsKey(packageName)) {

                cyclicPackages = cycleStrings.get(packageName);
            } else {

                cyclicPackages = new HashSet<String>();
            }

            for (final JavaPackage packageCycle : packageCycles) {

                final String cycleName = packageCycle.getName();
                cyclicPackages.add(cycleName);
            }

            cycleStrings.put(packageName, cyclicPackages);
        }

        exception.getCycles().putAll(cycleStrings);

        return exception;
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
    private void addCycle(final Map cycles, final JavaPackage javaPackage, final Collection dependencies) {

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
    private String buildCyclicRedundancyMessage(final Map<JavaPackage, Set<JavaPackage>> cycles) {

        final StringBuffer message = new StringBuffer();

        message.append(cycles.size()).append(" cyclic dependencies found:").append("\r\n").append("\r\n\t");

        for (final Map.Entry<JavaPackage, Set<JavaPackage>> entry : cycles.entrySet()) {

            final JavaPackage javaPackage = entry.getKey();
            final Set<JavaPackage> cyclicDependencies = entry.getValue();

            message.append("-- ").append(javaPackage.getName()).append("\r\n\t");

            message.append("|  |").append(" (depends on one or more of)").append("\r\n\t");

            for (Iterator dependencyIterator = cyclicDependencies.iterator();
                     dependencyIterator.hasNext();) {

                final JavaPackage dependency = (JavaPackage) dependencyIterator.next();

                final String listOfClasses = buildListOfClasses(javaPackage, dependency);

                message.append("|  |");
                message.append("\r\n\t");
                message.append("|  |-- ");
                message.append(dependency.getName());
                message.append("\n").append(listOfClasses);
                message.append("\r\n\t");

                if (!dependencyIterator.hasNext()) {

                    message.append("|").append("\r\n\t");
                }
            }
        }

        return message.toString();
    }


    /**
     * <p></p>
     *
     * @param javaPackage <code>JavaPackage</code> package to describe
     * @param dependency <code>JavaPackage</code> that the javaPackage argument
     * depends on
     * @return String that can be output to the console that describes the given
     *         javaPackages's dependency.
     */
    private String buildListOfClasses(final JavaPackage javaPackage, final JavaPackage dependency) {

        final StringBuffer listOfClasses = new StringBuffer();
        final Collection<JavaClass> classes = dependency.getClasses();

        for (final JavaClass javaClass : classes) {

            final Collection<JavaPackage> importedPackages = javaClass.getImportedPackages();

            final Collection<JavaClass> referencedClassNames = buildListOfImports(javaPackage, javaClass);

            if (importedPackages.contains(javaPackage)) {

                final String referencesToPrint;

                if (referencedClassNames.isEmpty()) {

                    referencesToPrint = "referenced classes not found";
                } else {

                    referencesToPrint = referencedClassNames.toString();
                }

                listOfClasses.append("\t|\t").append(javaClass.getName()).append(": ").append(referencesToPrint).append("\n");
            }
        }

        return listOfClasses.toString();
    }


    /**
     * <p>Builds a List of class names that the given classWithImports argument
     * are involved with cycle with</p>
     *
     * @param packageInCycle JavaPackage involved in cyclic dependency
     * @param classWithImports JavaClass that is involved in the cyclic
     * dependency
     * @return List of class names which this class imports from the package
     *         involved in the cycle
     */
    private List buildListOfImports(final JavaPackage packageInCycle, final JavaClass classWithImports) {

        final List<String> referencedClassNames = new LinkedList<String>();

        try {

            final ClassPool classPool = ClassPool.getDefault();
            final String name = classWithImports.getName();
            final Collection<String> refClasses = classPool.get(name).getRefClasses();

            for (final String importedClass : refClasses) {

                final boolean notSelfClass = !name.equals(importedClass);

                try {

                    final String packageName = classPool.get(importedClass).getPackageName();

                    final boolean importFromCycle = packageName.equals(packageInCycle.getName());

                    if (notSelfClass && importFromCycle) {

                        referencedClassNames.add(importedClass);
                    }
                } catch (NotFoundException e) {

                    log.error("could not load reference class", e);
                }
            }
        } catch (NotFoundException e) {

            log.error("could not load reference class", e);
        }

        return referencedClassNames;
    }
}
