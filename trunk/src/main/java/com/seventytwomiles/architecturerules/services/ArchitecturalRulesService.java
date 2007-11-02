package com.seventytwomiles.architecturerules.services;


import com.seventytwomiles.architecturerules.configuration.Configuration;
import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.exceptions.DependencyConstraintException;
import com.seventytwomiles.architecturerules.exceptions.NoPackagesFoundException;
import com.seventytwomiles.architecturerules.exceptions.SourceNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Iterator;


/**
 * <p>Drives the tests by reading the configuraiton then asserting each defined
 * <code>Rule</code>.</p>
 *
 * @author mnereson
 * @see AbstractArchitecturalRules
 */
public class ArchitecturalRulesService extends AbstractArchitecturalRules {


    /**
     * <p>Log to log with.</p>
     *
     * @parameter log Log
     */
    private static final Log log = LogFactory.getLog(ArchitecturalRulesService.class);


    /**
     * <p>Instantiates a new <code>ArchitecturalRulesService</code> which will
     * begin reading all the configured sources</p>
     *
     * @param configuration Configuration
     * @throws SourceNotFoundException when an required source directory does
     * not exist and when <tt>exception</tt>=<tt>"true"</tt> in the source
     * configuration
     * @throws NoPackagesFoundException when none of the source directories
     * exist and <tt>no-packages</tt>="<tt>ignore</tt>" in the sources
     * configuraiton
     */
    public ArchitecturalRulesService(final Configuration configuration) throws SourceNotFoundException, NoPackagesFoundException {

        super(configuration);

        log.info("instanciating new ArchitecturalRulesService");
    }


    /**
     * <p>Assert that no <code>Rule</code> in the given <code>Configuraiton</code>
     * has been violated.</p>
     *
     * @return boolean <tt>true</tt> when tests pass
     */
    public boolean performRulesTest() {

        log.info("perform rules test required");

        final Collection rules = configuration.getRules();

        Rule rule;

        /**
         * If logging is enabled, describe each rule that is going to be
         * validated.
         */
        if (log.isDebugEnabled()) {

            for (Iterator ruleIterator = rules.iterator();
                 ruleIterator.hasNext();) {

                rule = (Rule) ruleIterator.next();
                log.debug(rule.getDescriptionOfRule());
            }
        }

        for (Iterator ruleIterator = rules.iterator();
             ruleIterator.hasNext();) {

            rule = (Rule) ruleIterator.next();

            log.info("checking rule " + rule.getId());
            log.debug("checking for dependency violations in " + rule.describePackges());

            try {

                final Collection packages = rule.getPackages();

                for (Iterator packageIterator = packages.iterator();
                     packageIterator.hasNext();) {

                    final String packageName = (String) packageIterator.next();
                    testLayeringValid(packageName, rule.getViolations());
                }

            } catch (final DependencyConstraintException e) {

                /* just creates a more descriptive message which identifies the rule by its id */
                throw new DependencyConstraintException("rule " + rule.getId() + " failed: " + e.getMessage());
            }

            log.debug("no dependency violations in " + rule.getPackages());
        }

        log.info("architecture rules service has completed its tests.");

        return true;
    }
}
