package com.nereson.architecturerules;


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


    private static final Log log = LogFactory.getLog(ArchitecturalRulesService.class);


    public ArchitecturalRulesService() throws SourceNotFoundException, NoPackagesFoundException {
        super();

        log.info("instanciating new ArchitecturalRulesService");
    }


    public void performRulesTest() throws DependencyConstraintException {

        log.info("perform rules test required");

        log.debug("loading all rules");
        Set rules = ConfigurationFactory.getRules();

        StringBuffer ruleList = new StringBuffer();
        for (Iterator ruleIterator = rules.iterator(); ruleIterator.hasNext();) {

            Rule rule = (Rule) ruleIterator.next();
            ruleList.append("[").append(rule.getId()).append(" for ").append(rule.getPackageName()).append("] ");
        }

        log.debug("loaded " + rules.size() + " rules " + ruleList.toString());

        for (Iterator ruleIterator = rules.iterator(); ruleIterator.hasNext();) {
            Rule rule = (Rule) ruleIterator.next();

            log.info("checking rule " + rule.getId());
            log.debug("checking for dependency violations in " + rule.getPackageName());

            if (!isLayeringValid(rule.getPackageName(), rule.getViolations())) {

                log.warn("dependency violation in " + rule.getPackageName());
                throw new DependencyConstraintException(rule);

            } else {

                log.debug("no dependency violations in " + rule.getPackageName());
            }
        }

        log.info("Architecture rules service has completed its tests.");
    }
}
