package com.seventytwomiles.architecturerules.services;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.exceptions.SourcesNotFoundException;
import com.seventytwomiles.architecturerules.exceptions.DependencyConstraintException;
import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;
import com.seventytwomiles.architecturerules.exceptions.NoPackagesFoundException;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;


/**
 * <p>Provides support for any type of rule that can be asserted using the
 * jdepend library</p>
 *
 * @author mnereson
 * @see ArchitecturalRulesService
 * @see CyclicRedundencyService
 */
abstract class AbstractArchitecturalRules {


    protected final Configuration configuration;

    /**
     * <p>log to debug with.</p>
     *
     * @parameter log Log
     */
    private static final Log log = LogFactory.getLog(AbstractArchitecturalRules.class);

    /**
     * <p>instance of jdepend to assert architecture with</p>
     *
     * @parameter jdepend JDepend
     */
    protected final JDepend jdepend;

    /**
     * <p>all packages that will be analyized</p>
     *
     * @parameter packages Collection
     */
    private Collection packages;

    /**
     * <p>Location in a String array where the source path is located</p>
     *
     * @parameter SOURCE_POSITION int
     */
    private static final int SOURCE_POSITION = 0;

    /**
     * <p>Location in a String array where the source-not-found rule is
     * located</p>
     *
     * @parameter SOURCE_NOT_FOUND_POSITION int
     */
    private static final int SOURCE_NOT_FOUND_POSITION = 1;


    /**
     * <p>Constructor that loads up the configuration and loads all the packages
     * in the source paths</p>
     *
     * @param configuration Configuration
     * @throws SourcesNotFoundException when an required source directory does
     * not exist and when <tt>exception</tt>=<tt>"true"</tt> in the source
     * configuration
     * @throws NoPackagesFoundException when none of the source directories
     * exist and <tt>no-packages</tt>="<tt>ignore</tt>" in the sources
     * configuraiton
     */
    protected AbstractArchitecturalRules(final Configuration configuration) throws SourcesNotFoundException, NoPackagesFoundException {

        log.info("instanciating new AbstractArchitecturalRules");

        this.configuration = configuration;

        /* instanciate JDepend */
        jdepend = new JDepend();

        /* read source paths from configuration file */
        final Collection sources = this.configuration.getSources();

        /* add each source to jdepend */
        for (Iterator sourceIterator = sources.iterator();
             sourceIterator.hasNext();) {

            /* read the next source directory */
            final String[] source = (String[]) sourceIterator.next();

            /* read the source path */
            final String sourcePath = source[SOURCE_POSITION];

            /* read not-found instruction */
            final String sourcesNotFoundRule = source[SOURCE_NOT_FOUND_POSITION];
            
            final boolean isConfiguredToThrowExceptionWhenSourceNotFound =
                    Boolean.valueOf(sourcesNotFoundRule).booleanValue();

            final StringBuffer message = new StringBuffer();

            try {

                jdepend.addDirectory(sourcePath);

                message.append("loaded ");
                message.append(isConfiguredToThrowExceptionWhenSourceNotFound ? "required " : "");
                message.append("source ");
                message.append(new File("").getAbsolutePath());
                message.append("\\");
                message.append(sourcePath);

                log.debug(message.toString());

            } catch (final IOException e) {

                /* source not found */

                message.append(isConfiguredToThrowExceptionWhenSourceNotFound ? "required " : "");
                message.append("source ");
                message.append(new File("").getAbsolutePath());
                message.append("\\");
                message.append(sourcePath);
                message.append(" does not exist");

                log.warn(message.toString());

                if (isConfiguredToThrowExceptionWhenSourceNotFound) {

                    log.error(sourcePath + " was not found", e);
                    throw new SourcesNotFoundException(sourcePath + " was not found", e);
                }
            }
        }

        /**
         * ask jdepened to analyze each package in each of the source
         * directories that were added above
         */
        log.debug("fetching packages");
        packages = jdepend.analyze();

        log.debug("checking how many packages were found by JDepend");
        if (packages.isEmpty()) {

            log.warn("no packages were found with the given configuraiton. check your <sources />");

            final boolean isConfiguredToThrowExceptionWhenNoPackagesFound = this.configuration.shouldThrowExceptionWhenNoPackages();
            log.debug("throw excpetion when no packages? " + isConfiguredToThrowExceptionWhenNoPackagesFound);

            if (isConfiguredToThrowExceptionWhenNoPackagesFound) {

                log.debug("throwing RuntimeException because no packages were found");
                throw new NoPackagesFoundException("no packages were found with the given configuraiton. check your <sources />");
            }

        } else {

            log.debug("jdepend found " + packages.size() + " to analyze for cyclic redundencies");
        }
    }


    public Collection getPackages() {
        return this.packages;
    }


    /**
     * <p>Test the given layer (a package, but package is java keyword) against
     * the given Rules</p>
     *
     * @param layer String the package to test
     * @param rules Colleciton of rules defining which packages the layer may
     * not utilize
     * @throws DependencyConstraintException when a rule is broken
     * @throws CyclicRedundancyException when cyclic redundency is found
     */
    protected void testLayeringValid(final String layer, final Collection rules) throws DependencyConstraintException, CyclicRedundancyException {

        final Collection packages = jdepend.analyze();

        log.debug("checking how many packages were found by JDepend");

        if (packages.isEmpty()) {

            log.warn("no packages were found with the given configuraiton. check your <sources />");

            final boolean isConfiguredToThrowExceptionWhenNoPackagesFound = this.configuration.shouldThrowExceptionWhenNoPackages();
            log.debug("throw excpetion when no packages? " + isConfiguredToThrowExceptionWhenNoPackagesFound);

            if (isConfiguredToThrowExceptionWhenNoPackagesFound) {

                log.debug("throwing CyclicRedundancyException");
                throw new CyclicRedundancyException("cyclic redundency does exist");
            }

        } else {

            log.debug("jdepend found " + packages.size() + " to analyze for dependency architecture");
        }

        for (Iterator packageIterator = packages.iterator();
             packageIterator.hasNext();) {

            final JavaPackage javaPackage = (JavaPackage) packageIterator.next();

            log.debug("checking dependencies on package " + javaPackage.getName());
            testEfferentsValid(layer, rules, javaPackage, javaPackage.getName());
        }
    }


    /**
     * <p>Test a given layer (java package) against a Collection of
     * <code>Rules</code></p>
     *
     * @param layer String name of the package to test
     * @param rules Colleciton of rules defining which packages the given
     * package may not depend upon
     * @param jPackage JavaPackage
     * @param analyzedPackageName String full name
     * @throws DependencyConstraintException when a rule is broken
     */
    protected void testEfferentsValid(final String layer, final Collection rules,
                                      final JavaPackage jPackage,
                                      final String analyzedPackageName) throws DependencyConstraintException {

        final Collection efferents = jPackage.getEfferents();

        JavaPackage efferentPackage;

        for (Iterator packageIterator = efferents.iterator();
             packageIterator.hasNext();) {

            efferentPackage = (JavaPackage) packageIterator.next();

            if (rules.contains(efferentPackage.getName()) && analyzedPackageName.equals(layer)) {

                final String message = analyzedPackageName
                        + " is not allowed to depend upon "
                        + efferentPackage.getName();

                log.error(message);

                throw new DependencyConstraintException(message);
            }
        }
    }
}
