package com.seventytwomiles.architecturerules.configuration.xml;

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
* For more information visit
* http://architecturerules.googlecode.com/svn/docs/index.html
*/


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
* For more information visit
* http://architecturerules.googlecode.com/svn/docs/index.html
*/
/**
 * <p>Holds settings and paths for XML configuration.</p>
 *
 * @author mikenereson
 */
class XmlConfiguration {


    /**
     * Cyclic dependency
     */
    public static final String cyclicalDependency
            = "architecture/configuration/cyclicalDependency";

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

    public static final String source
            = "architecture/configuration/sources/source";

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

    public static final String rulePackages
            = "architecture/rules/rule/packages";

    public static final String rulePackage
            = "architecture/rules/rule/packages/package";

    public static final String ruleViolations
            = "architecture/rules/rule/violations";

    public static final String ruleViolation
            = "architecture/rules/rule/violations/violation";
}
