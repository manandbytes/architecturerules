package com.seventytwomiles.architecturerules.services;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
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
