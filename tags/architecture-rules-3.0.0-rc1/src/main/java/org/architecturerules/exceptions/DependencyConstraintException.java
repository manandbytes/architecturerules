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
package org.architecturerules.exceptions;


import java.util.Collection;

import org.architecturerules.domain.Rule;


/**
 * <p>Exception to be thrown when any <code>Rule</code> fails, that is to say, the rule is violoated.</p>
 *
 * @author mikenereson
 * @see ArchitectureException
 */
public class DependencyConstraintException extends ArchitectureException {

    private String analyzedPackage;
    private Collection<String> classes;
    private String ruleId;

    /**
     * <p>Reports which {@link Rule} was broken, by its <tt>id</tt>, and what packages that <code>Rule</code>
     * governs.</p>
     *
     * @param aRuleId String id of the <code>Rule</code> that was violated.
     * @param anAnalyzedPackage name of the package under govern
     * @param classes String listing each package constrained by the violated <code>Rule</code>
     * @param cause Throwable any exception that was thrown
     */
    public DependencyConstraintException(String aRuleId, String anAnalyzedPackage, Collection<String> classes, final Throwable cause) {
        super(cause);
        setRuleId(aRuleId);
        setAnalyzedPackage(anAnalyzedPackage);
        setClasses(classes);
    }

    @Override
    public String getMessage() {

        return String.format("dependency constraint failed in '%s' rule: package '%s' should not be imported by %s", getRuleId(), getAnalyzedPackage().trim(), getClasses());
    }


    private void setAnalyzedPackage(String analyzedPackage) {

        this.analyzedPackage = analyzedPackage;
    }


    public String getAnalyzedPackage() {

        return analyzedPackage;
    }


    private void setClasses(Collection<String> affectedClasses) {

        this.classes = affectedClasses;
    }


    public Collection<String> getClasses() {

        return classes;
    }


    private void setRuleId(String ruleId) {

        this.ruleId = ruleId;
    }


    private String getRuleId() {

        return ruleId;
    }
}
