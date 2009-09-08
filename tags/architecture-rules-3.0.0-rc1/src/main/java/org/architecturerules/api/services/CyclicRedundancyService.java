/**
 * Copyright 2007 - 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://wiki.architecturerules.org/ and
 *         http://blog.architecturerules.org/
 */
package org.architecturerules.api.services;


import org.architecturerules.exceptions.CyclicRedundancyException;


/**
 * <p>Interface for the CyclicRedundancyService to provide a contract for implementations to adhere to. This service
 * provides the functionality necessary to check for cyclic dependencies.</p>
 *
 * @author mikenereson
 */
public interface CyclicRedundancyService {

    /**
     * <p>Check all the packages in all of the source directories and search for any cyclic redundancy</p>
     *
     * @throws CyclicRedundancyException when cyclic redundancy is found
     */
    void performCyclicRedundancyCheck()
            throws CyclicRedundancyException;
}
