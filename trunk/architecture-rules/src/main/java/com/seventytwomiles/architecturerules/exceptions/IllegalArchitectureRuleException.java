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
 *         http://architecturerules.googlecode.com/
 */
package com.seventytwomiles.architecturerules.exceptions;



/**
 * <p>Exception to be thrown when a Rule is illegal constructed. That is, an
 * illegal violation is created. One example of an illegal violation is when a
 * violation is added to a rule that matches one of the packages that the rule
 * constrins.</p>
 *
 * <pre>
 * &lt;rule id="dao"&gt;
 *   &lt;packages&gt;
 *     &lt;package&gt;com.seventytwomiles.pagerank.core.dao&lt;/package&gt;
 *   &lt;/packages&gt;
 *   &lt;violations&gt;
 *     &lt;violation&gt;com.seventytwomiles.pagerank.core.dao&lt;/violation&gt;
 *   &lt;/violations&gt;>
 * &lt;/rule&gt;
 * </pre>
 *
 * @author mikenereson
 * @see ArchitectureException
 */
public class IllegalArchitectureRuleException extends ArchitectureException {

    /**
     * @see RuntimeException#RuntimeException()
     */
    public IllegalArchitectureRuleException() {
        super("illegal architecture rule");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public IllegalArchitectureRuleException(final String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public IllegalArchitectureRuleException(final Throwable cause) {
        super("illegal architecture rule", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public IllegalArchitectureRuleException(final String message, final Throwable cause) {
        super(message, cause);
    }


    /**
     * <p>Instantiates a new IllegalArchitectureRuleException with the given
     * ruleId and rulePackages.</p>
     *
     * @param ruleId       String id of the Rule
     * @param rulePackages String some description of the package rules, such as
     *                     a delimited list
     */
    public IllegalArchitectureRuleException(final String ruleId, final String rulePackages) {
        this(ruleId, rulePackages, null);
    }


    /**
     * <p>Instantiates a new IllegalArchitectureRuleException with the given
     * ruleId and rulePackages, and passes on the cause.</p>
     *
     * @param ruleId       String id of the Rule
     * @param rulePackages String some description of the package rules, such as
     *                     a delimited list
     * @param cause        Throwable root cause
     */
    public IllegalArchitectureRuleException(final String ruleId, final String rulePackages, final Throwable cause) {
        super("rule '{id}' contains an invalid violation that refers to itself; remove violation '{violation}' or change package".replaceAll("\\{id}", ruleId).replaceAll("\\{violation}", rulePackages.trim()).replaceAll("\\[", "").replaceAll("\\]", ""), cause);
    }
}
