package com.seventytwomiles.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.Set;


/**
 * <p>Drives the tests by reading the configuraiton then asserting each defined
 * <code>Rule</code>.</p>
 *
 * @author mnereson
 * @see AbstractArchitecturalRules
 */
class ArchitecturalRulesService extends AbstractArchitecturalRules {


    /**
     * <p>Log to log with</p>
     *
     * @parameter
     */
    private static final Log log = LogFactory.getLog(ArchitecturalRulesService.class);


    /**
     * <p>Instancates a new ArchitecturalRulesService which will begin reading
     * all the configured sources</p>
     *
     * @throws SourceNotFoundException when an required source directory does
     * not exist and when <tt>exception</tt>=<tt>"true"</tt> in the source
     * configuration
     * @throws NoPackagesFoundException when none of the source directories
     * exist and <tt>no-packages</tt>="<tt>ignore</tt>" in the sources
     * configuraiton
     */
    public ArchitecturalRulesService() throws SourceNotFoundException, NoPackagesFoundException {

        super();

        log.info("instanciating new ArchitecturalRulesService");
    }


    /**
     * <p></p>
     *
     * @throws DependencyConstraintException when a rule is broken
     * @throws CyclicRedundencyException when cyclic redundency is found
     */
    public void performRulesTest() throws DependencyConstraintException, CyclicRedundencyException {

        log.info("perform rules test required");

        log.debug("loading all rules");
        final Set rules = ConfigurationFactory.getRules();

        final StringBuffer ruleList = new StringBuffer();

        Rule rule;

        for (Iterator ruleIterator = rules.iterator();
             ruleIterator.hasNext();) {

            rule = (Rule) ruleIterator.next();
            ruleList.append("[").append(rule.getId()).append(" for ").append(rule.getPackageName()).append("] ");
        }

        log.debug("loaded " + rules.size() + " rules " + ruleList.toString());

        for (Iterator ruleIterator = rules.iterator();
             ruleIterator.hasNext();) {

            rule = (Rule) ruleIterator.next();

            log.info("checking rule " + rule.getId());
            log.debug("checking for dependency violations in " + rule.getPackageName());

            try {

                testLayeringValid(rule.getPackageName(), rule.getViolations());

            } catch (DependencyConstraintException e) {

                /* just creates a more descriptive message which identifies the rule by its id */
                throw new DependencyConstraintException("rule " + rule.getId() + " failed: " + e.getMessage());
            }

            log.debug("no dependency violations in " + rule.getPackageName());
        }

        log.info("Architecture rules service has completed its tests.");
    }
}
