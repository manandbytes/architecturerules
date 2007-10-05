package com.seventytwomiles.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.Set;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see AbstractArchitecturalRules
 */
class ArchitecturalRulesService extends AbstractArchitecturalRules {


    /**
     * <p>todo: javadoc this</p>
     *
     * @parameter
     */
    private static final Log log = LogFactory.getLog(ArchitecturalRulesService.class);


    /**
     * <p></p>
     *
     * @throws SourceNotFoundException
     * @throws NoPackagesFoundException
     */
    public ArchitecturalRulesService() throws SourceNotFoundException, NoPackagesFoundException {

        super();

        log.info("instanciating new ArchitecturalRulesService");
    }


    /**
     * <p></p>
     *
     * @throws DependencyConstraintException
     * @throws CyclicRedundencyException
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
