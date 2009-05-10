/**
 * Copyright 2007, 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com/ and
 *         http://architecturerules.googlecode.com/
 */
package org.architecturerules.exceptions;



/**
 * <p>Exception to be thrown when any <code>Rule</code> fails, that is to say, the rule is violoated.</p>
 *
 * @author mikenereson
 * @see ArchitectureException
 */
public class DependencyConstraintException extends ArchitectureException {

    private String analyzedPackage;
    private String packages;
    private String ruleId;

    /**
     * <p>Reports which <code>Rule</code> was broken, by its <tt>id</tt>, and what packages that <code>Rule</code>
     * governs.</p>
     *
     * @param aRuleId String id of the <code>Rule</code> that was violated.
     * @param packages String listing each package constrained by the violated <code>Rule</code>
     * @param cause Throwable any exception that was thrown
     * @param anAnalyzedPackage name of the package under govern
     */
    public DependencyConstraintException(String aRuleId, String anAnalyzedPackage, String packages, final Throwable cause) {
        super(cause);
        setRuleId(aRuleId);
        setAnalyzedPackage(anAnalyzedPackage);
        setPackages(packages);
    }

    @Override
    public String getMessage() {

        return String.format("dependency constraint failed in '%s' rule which constrains packages '%s'", getRuleId(), getPackages().trim());
    }


    private void setAnalyzedPackage(String analyzedPackage) {

        this.analyzedPackage = analyzedPackage;
    }


    public String getAnalyzedPackage() {

        return analyzedPackage;
    }


    private void setPackages(String dependendentPackage) {

        this.packages = dependendentPackage;
    }


    public String getPackages() {

        return packages;
    }


    private void setRuleId(String ruleId) {

        this.ruleId = ruleId;
    }


    private String getRuleId() {

        return ruleId;
    }
}
