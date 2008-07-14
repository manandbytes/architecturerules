/**
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com/svn/docs/index.html
 */
package com.seventytwomiles.architecturerules.configuration.xml;


/**
 * <p>Holds settings and paths for XML configuration.</p>
 *
 * @author mikenereson
 */
class XmlConfiguration
{
    /**
     * Cyclic dependency
     */
    public static final String cyclicalDependency = "architecture/configuration/cyclicalDependency";

    /**
     * Sources
     * <pre>
     * - configuration
     *   - sources
     *     - source
     * </pre>
     */
    public static final String configuration = "architecture/configuration";
    public static final String sources = "architecture/configuration/sources";
    public static final String source = "architecture/configuration/sources/source";

    /**
     * Rules
     * <pre>
     * - rules
     *   - rule
     *     - comment
     *     - packages
     *       - package
     *     - violations
     *       - violation
     * </pre>
     */
    public static final String rules = "architecture/rules";
    public static final String rule = "architecture/rules/rule";
    public static final String ruleComment = "architecture/rules/rule/comment";
    public static final String rulePackages = "architecture/rules/rule/packages";
    public static final String rulePackage = "architecture/rules/rule/packages/package";
    public static final String ruleViolations = "architecture/rules/rule/violations";
    public static final String ruleViolation = "architecture/rules/rule/violations/violation";
}
