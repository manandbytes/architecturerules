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
package org.architecturerules.configuration.xml;


import junit.framework.TestCase;


/**
 * <p>Abstract class to provide common fields and methods to the various test classes responsible for testing the
 * DigesterConfiguraitonFactory implementation.</p>
 *
 * @author mikenereson
 * @see TestCase
 */
public class AbstractDigesterTest extends TestCase {

    final String doCyclicDependencyTestConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"ignore\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"ignore\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "        </sources>\n" + "\n" + "        <cyclicalDependency test=\"true\"/>\n" + "\n" + "    </configuration>\n" + "    \n" + "</architecture>";
    final String skipCyclicDependencyTestConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"ignore\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"ignore\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "        </sources>\n" + "\n" + "        <cyclicalDependency test=\"false\"/>\n" + "\n" + "    </configuration>\n" + "    \n" + "</architecture>";
    final String blankCyclicDependencyTestConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"ignore\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"ignore\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "        </sources>\n" + "\n" + "    </configuration>\n" + "    \n" + "</architecture>";
    final String illegalCyclicDependencyTestConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"ignore\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"ignore\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "        </sources>\n" + "\n" + "        <cyclicalDependency test=\"INVALID\"/>\n" + "\n" + "    </configuration>\n" + "    \n" + "</architecture>";
    final String noPackagesIgnoreConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"ignore\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"exception\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "        </sources>\n" + "\n" + "    </configuration>\n" + "\n" + "</architecture>";
    final String noPackagesExceptionConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"exception\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"exception\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "        </sources>\n" + "\n" + "    </configuration>\n" + "\n" + "</architecture>";
    final String noPackagesBlankConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources>\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"exception\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "        </sources>\n" + "\n" + "    </configuration>\n" + "\n" + "</architecture>";
    final String noPackagesInvalidConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"INVLALID\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"exception\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "        </sources>\n" + "\n" + "    </configuration>\n" + "\n" + "</architecture>";
    final String sourcesXmlConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"exception\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"exception\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "        </sources>\n" + "\n" + "    </configuration>\n" + "\n" + "</architecture>";
    final String rulesXmlConfiguration = "<architecture>\n" + "\n" + "    <rules>\n" + "\n" + "        <rule id=\"dao\">\n" + "\n" + "            <comment>\n" + "                The dao interface package should rely on nothing.\n" + "            </comment>\n" + "\n" + "            <packages>\n" + "                <package>\n" + "                    com.seventytwomiles.pagerank.core.dao\n" + "                </package>\n" + "                <package>\n" + "                    com.seventytwomiles.pagerank.core.dao.hibernate\n" + "                </package>\n" + "            </packages>\n" + "\n" + "            <violations>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.services\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.builder\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.util\n" + "                </violation>\n" + "            </violations>\n" + "        </rule>\n" + "\n" + "        <rule id=\"model\">\n" + "\n" + "            <comment>\n" + "                Model should remain completely isolated\n" + "            </comment>\n" + "\n" + "            <packages>\n" + "                <package>\n" + "                    com.seventytwomiles.pagerank.core.model\n" + "                </package>\n" + "            </packages>\n" + "\n" + "            <violations>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.dao\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.dao.hibernate\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.services\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.strategy\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.core.builder\n" + "                </violation>\n" + "                <violation>\n" + "                    com.seventytwomiles.pagerank.util\n" + "                </violation>\n" + "            </violations>\n" + "        </rule>\n" + "\n" + "    </rules>\n" + "\n" + "</architecture>";
    final String withIncludeListenersConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"exception\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"ignore\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "            <source not-found=\"exception\">target\\classes</source>\n" + "        </sources>\n" + "\n" + "        <cyclicalDependency test=\"true\" />\n" + "\n" + "        <listeners>\n" + "            <include>\n" + "                <listener>org.architecturerules.listeners.ExampleListener</listener>\n" + "            </include>\n" + "        </listeners>\n" + "    </configuration>\n" + "</architecture>";
    final String withExcludeListenersConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"exception\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"ignore\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "            <source not-found=\"exception\">target\\classes</source>\n" + "        </sources>\n" + "\n" + "        <cyclicalDependency test=\"true\" />\n" + "\n" + "        <listeners>\n" + "            <exclude>\n" + "                <listener>org.architecturerules.listeners.LoggerListener</listener>\n" + "            </exclude>\n" + "        </listeners>\n" + "    </configuration>\n" + "</architecture>";
    final String withNoListenersConfiguration = "<architecture>\n" + "\n" + "    <configuration>\n" + "\n" + "        <sources no-packages=\"exception\">\n" + "            <source not-found=\"ignore\">core\\target\\classes</source>\n" + "            <source not-found=\"ignore\">util\\target\\classes</source>\n" + "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "            <source not-found=\"ignore\">web\\target\\classes</source>\n" + "            <source not-found=\"exception\">target\\classes</source>\n" + "        </sources>\n" + "\n" + "        <cyclicalDependency test=\"true\" />\n" + "       \n" + "    </configuration>\n" + "</architecture>";
    final String withProperties = "<architecture><configuration><properties><property key=\"custom.report.output.directory\" value=\"custom/target/architecture\"/><property key=\"custom.report.output.format\" value=\"custom/xml\"/></properties></configuration></architecture>";
    final String withPropertiesWrongAttributes = "<architecture><configuration><properties><property name=\"report.output.directory\" value=\"target/architecture\"/></configuration></architecture>";
    final String withPropertiesMissingAttributes = "<architecture><configuration><properties><property value=\"target/architecture\"/></configuration></architecture>";

    /**
     * <p>Constructs a test case with the given name.</p>
     *
     * @param name name of test
     */
    public AbstractDigesterTest(final String name) {
        super(name);
    }

    public void test() {

        /**
         * A blank test so that IntelliJ doesn't complain that there are no
         * tests in this TestCase. TODO: how can this be fixed for IntelliJ?
         */
        assertTrue(true);
    }
}
