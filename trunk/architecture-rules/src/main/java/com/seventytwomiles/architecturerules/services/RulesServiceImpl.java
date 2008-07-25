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
import com.seventytwomiles.architecturerules.domain.JPackage;
import com.seventytwomiles.architecturerules.domain.Rule;
import com.seventytwomiles.architecturerules.exceptions.DependencyConstraintException;
import com.seventytwomiles.architecturerules.exceptions.NoPackagesFoundException;
import com.seventytwomiles.architecturerules.exceptions.SourceNotFoundException;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Drives the tests by reading the configuration then asserting each defined
 * <code>Rule</code>.</p>
 *
 * @author mikenereson
 * @see AbstractArchitecturalRules
 */
public class RulesServiceImpl extends AbstractArchitecturalRules implements RulesService {

    /**
     * <p>Log to log with.</p>
     *
     * @parameter log Log
     */
    protected static final Log log = LogFactory.getLog(RulesServiceImpl.class);

    /**
     * <p>Instantiates a new <code>RulesService</code> which will begin reading
     * all the configured sources</p>
     *
     * @param configuration Configuration
     * @throws SourceNotFoundException when an required source directory does
     * not exist and when <tt>exception</tt>=<tt>"true"</tt> in the source
     * configuration
     * @throws NoPackagesFoundException when none of the source directories
     * exist and <tt>no-packages</tt>="<tt>ignore</tt>" in the sources
     * configuration
     */
    public RulesServiceImpl(final Configuration configuration)
            throws SourceNotFoundException, NoPackagesFoundException {
        super(configuration);

        log.info("instantiating new RulesService");
    }

    /**
     * <p>Assert that no <code>Rule</code> in the given <code>Configuration</code>
     * has been violated.</p>
     *
     * @return boolean <tt>true</tt> when tests pass
     */
    public boolean performRulesTest() {

        log.info("perform rules test required");

        final Collection<Rule> rules = configuration.getRules();

        /**
         * If logging is enabled, describe each rule that is going to be
         * validated.
         */
        if (log.isDebugEnabled()) {

            for (final Rule rule : rules) {

                log.debug(rule.getDescriptionOfRule());
            }
        }

        for (final Rule rule : rules) {

            log.info("checking rule " + rule.getId());

            final String description = rule.describePackages();
            log.debug("checking for dependency violations in " + description);

            try {

                final Collection<JPackage> packages = rule.getPackages();

                for (final JPackage packageName : packages) {

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
