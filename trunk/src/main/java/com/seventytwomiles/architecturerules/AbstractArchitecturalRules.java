package com.seventytwomiles.architecturerules;


import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 */
abstract class AbstractArchitecturalRules {


    /**
     * <p></p>
     *
     * @parameter
     */
    private static final Log log = LogFactory.getLog(AbstractArchitecturalRules.class);

    /**
     * <p></p>
     *
     * @parameter
     */
    protected final JDepend jdepend;

    /**
     * <p></p>
     *
     * @parameter
     */
    private Collection packages;


    /**
     * <p></p>
     *
     * @throws SourceNotFoundException
     * @throws NoPackagesFoundException
     */
    protected AbstractArchitecturalRules() throws SourceNotFoundException, NoPackagesFoundException {

        log.info("instanciating new AbstractArchitecturalRules");

        jdepend = new JDepend();

        final List sources = ConfigurationFactory.getSources();

        for (Iterator sourceIterator = sources.iterator();
             sourceIterator.hasNext();) {

            final String[] source = (String[]) sourceIterator.next();

            final String sourcePath = source[0];
            final boolean throwExceptionIfNotFound = Boolean.valueOf(source[1]).booleanValue();

            final StringBuffer message = new StringBuffer();

            try {

                jdepend.addDirectory(sourcePath);

                message.append("loaded ");
                message.append(throwExceptionIfNotFound ? "required " : "");
                message.append("source ");
                message.append(new File("").getAbsolutePath());
                message.append("\\");
                message.append(sourcePath);

                log.debug(message.toString());

            } catch (final IOException e) {

                message.append(throwExceptionIfNotFound ? "required " : "");
                message.append("source ");
                message.append(new File("").getAbsolutePath());
                message.append("\\");
                message.append(sourcePath);
                message.append(" does not exist");

                log.warn(message.toString());

                if (throwExceptionIfNotFound) {

                    log.error(sourcePath + " was not found", e);
                    throw new SourceNotFoundException(sourcePath + " was not found", e);
                }
            }
        }

        log.debug("fetching packages");
        this.packages = jdepend.analyze();

        log.debug("checking how many packages were found by JDepend");
        if (packages.isEmpty()) {

            log.warn("no packages were found with the given configuraiton. check your <sources />");

            final boolean throwExceptionWhenNoPackages = ConfigurationFactory.throwExceptionWhenNoPackages();
            log.debug("throw excpetion when no packages? " + throwExceptionWhenNoPackages);

            if (throwExceptionWhenNoPackages) {

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
     * <p></p>
     *
     * @param layer
     * @param rules
     * @return
     * @throws DependencyConstraintException when a rule is broken
     * @throws CyclicRedundencyException
     */
    protected void testLayeringValid(final String layer, final Collection rules) throws DependencyConstraintException, CyclicRedundencyException {

        final Collection packages = jdepend.analyze();

        log.debug("checking how many packages were found by JDepend");

        if (packages.isEmpty()) {

            log.warn("no packages were found with the given configuraiton. check your <sources />");

            final boolean throwExceptionWhenNoPackages = ConfigurationFactory.throwExceptionWhenNoPackages();
            log.debug("throw excpetion when no packages? " + throwExceptionWhenNoPackages);

            if (throwExceptionWhenNoPackages) {

                log.debug("throwing CyclicRedundencyException");
                throw new CyclicRedundencyException("cyclic redundency does exist");
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
     * <p></p>
     *
     * @param layer
     * @param rules
     * @param jPackage
     * @param analyzedPackageName
     * @return
     * @throws DependencyConstraintException
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
