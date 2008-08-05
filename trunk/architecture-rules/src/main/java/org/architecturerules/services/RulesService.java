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
package org.architecturerules.services;



/**
 * <p>Interface for RuleService implementations to adhere to. This service
 * provides the methods necessary to assert that <code>Rules</code> are not
 * violated.</p>
 *
 * @author mikenereson
 */
public interface RulesService {

    /**
     * <p>Assert that no <code>Rule</code> in the given <code>Configuration</code>
     * has been violated.</p>
     *
     * @return boolean <tt>true</tt> when tests pass
     */
    boolean performRulesTest();
}
