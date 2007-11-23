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
 * For more infomration visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


/**
 * <p>Interface for RuleService implementations to adhere to. This service
 * provices the methods neccessary to assert that <code>Rules</code> are not
 * violated.</p>
 *
 * @author mikenereson
 */
public interface RulesService {


    /**
     * <p>Assert that no <code>Rule</code> in the given <code>Configuraiton</code>
     * has been violated.</p>
     *
     * @return boolean <tt>true</tt> when tests pass
     */
    boolean performRulesTest();
}
