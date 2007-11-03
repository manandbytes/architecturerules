package com.seventytwomiles.architecturerules.services;


import com.seventytwomiles.architecturerules.exceptions.CyclicRedundancyException;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 */
public interface CyclicRedundencyService {


    /**
     * <p>Check all the packages in all of the source directories and search for
     * any cyclic redundenc/p>
     *
     * @throws CyclicRedundancyException when cyclic redundency is found
     */
    void performCyclicRedundencyCheck() throws CyclicRedundancyException;
}
