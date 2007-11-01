package com.seventytwomiles.architecturerules.configuration.xml;


import junit.framework.TestCase;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see TestCase
 */
public class AbstractDigesterTest extends TestCase {


    public void test() {

        /**
         * A blank test so that IntelliJ doesn't complain that there are no
         * tests in this TestCase. TODO: how can this be fixed for IntelliJ?
         */
        assertTrue(true);
    }


    protected final String doCyclicDependencyTestConfiguration =
            "<architecture>\n" +
            "\n" +
            "    <configuration>\n" +
            "\n" +
            "        <sources no-packages=\"ignore\">\n" +
            "            <source not-found=\"ignore\">core\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">util\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">web\\target\\classes</source>\n" +
            "        </sources>\n" +
            "\n" +
            "        <cyclicalDependency test=\"true\"/>\n" +
            "\n" +
            "    </configuration>\n" +
            "    \n" +
            "</architecture>";

    protected final String skipCyclicDependencyTestConfiguration =
            "<architecture>\n" +
            "\n" +
            "    <configuration>\n" +
            "\n" +
            "        <sources no-packages=\"ignore\">\n" +
            "            <source not-found=\"ignore\">core\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">util\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">web\\target\\classes</source>\n" +
            "        </sources>\n" +
            "\n" +
            "        <cyclicalDependency test=\"false\"/>\n" +
            "\n" +
            "    </configuration>\n" +
            "    \n" +
            "</architecture>";

    protected final String blankCyclicDependencyTestConfiguration =
            "<architecture>\n" +
            "\n" +
            "    <configuration>\n" +
            "\n" +
            "        <sources no-packages=\"ignore\">\n" +
            "            <source not-found=\"ignore\">core\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">util\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">web\\target\\classes</source>\n" +
            "        </sources>\n" +
            "\n" +
            "    </configuration>\n" +
            "    \n" +
            "</architecture>";

    protected final String illegalCyclicDependencyTestConfiguration =
            "<architecture>\n" +
            "\n" +
            "    <configuration>\n" +
            "\n" +
            "        <sources no-packages=\"ignore\">\n" +
            "            <source not-found=\"ignore\">core\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">util\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">web\\target\\classes</source>\n" +
            "        </sources>\n" +
            "\n" +
            "        <cyclicalDependency test=\"INVALID\"/>\n" +
            "\n" +
            "    </configuration>\n" +
            "    \n" +
            "</architecture>";


    protected final String noPackagesIgnoreConfiguration =
            "<architecture>\n" +
            "\n" +
            "    <configuration>\n" +
            "\n" +
            "        <sources no-packages=\"ignore\">\n" +
            "            <source not-found=\"ignore\">core\\target\\classes</source>\n" +
            "            <source not-found=\"exception\">util\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">web\\target\\classes</source>\n" +
            "        </sources>\n" +
            "\n" +
            "    </configuration>\n" +
            "\n" +
            "</architecture>";

    protected final String noPackagesExceptionConfiguration =
            "<architecture>\n" +
            "\n" +
            "    <configuration>\n" +
            "\n" +
            "        <sources no-packages=\"exception\">\n" +
            "            <source not-found=\"ignore\">core\\target\\classes</source>\n" +
            "            <source not-found=\"exception\">util\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">web\\target\\classes</source>\n" +
            "        </sources>\n" +
            "\n" +
            "    </configuration>\n" +
            "\n" +
            "</architecture>";

    protected final String noPackagesBlankConfiguration =
            "<architecture>\n" +
            "\n" +
            "    <configuration>\n" +
            "\n" +
            "        <sources>\n" +
            "            <source not-found=\"ignore\">core\\target\\classes</source>\n" +
            "            <source not-found=\"exception\">util\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">web\\target\\classes</source>\n" +
            "        </sources>\n" +
            "\n" +
            "    </configuration>\n" +
            "\n" +
            "</architecture>";

    protected final String noPackagesInvalidConfiguration =
            "<architecture>\n" +
            "\n" +
            "    <configuration>\n" +
            "\n" +
            "        <sources no-packages=\"INVLALID\">\n" +
            "            <source not-found=\"ignore\">core\\target\\classes</source>\n" +
            "            <source not-found=\"exception\">util\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">web\\target\\classes</source>\n" +
            "        </sources>\n" +
            "\n" +
            "    </configuration>\n" +
            "\n" +
            "</architecture>";

    protected final String sourcesXmlConfiguration =
            "<architecture>\n" +
            "\n" +
            "    <configuration>\n" +
            "\n" +
            "        <sources no-packages=\"exception\">\n" +
            "            <source not-found=\"ignore\">core\\target\\classes</source>\n" +
            "            <source not-found=\"exception\">util\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">parent-pom\\target\\classes</source>\n" +
            "            <source not-found=\"ignore\">web\\target\\classes</source>\n" +
            "        </sources>\n" +
            "\n" +
            "    </configuration>\n" +
            "\n" +
            "</architecture>";

    protected final String rulesXmlConfiguration =
            "<architecture>\n" +
            "\n" +
            "    <rules>\n" +
            "\n" +
            "        <rule id=\"dao\">\n" +
            "\n" +
            "            <comment>\n" +
            "                The dao interface package should rely on nothing.\n" +
            "            </comment>\n" +
            "\n" +
            "            <packages>\n" +
            "                <package>\n" +
            "                    com.seventytwomiles.pagerank.core.dao\n" +
            "                </package>\n" +
            "                <package>\n" +
            "                    com.seventytwomiles.pagerank.core.dao.hibernate\n" +
            "                </package>\n" +
            "            </packages>\n" +
            "\n" +
            "            <violations>\n" +
            "                <violation>\n" +
            "                    com.seventytwomiles.pagerank.core.services\n" +
            "                </violation>\n" +
            "                <violation>\n" +
            "                    com.seventytwomiles.pagerank.core.builder\n" +
            "                </violation>\n" +
            "                <violation>\n" +
            "                    com.seventytwomiles.pagerank.util\n" +
            "                </violation>\n" +
            "            </violations>\n" +
            "        </rule>\n" +
            "\n" +
            "        <rule id=\"model\">\n" +
            "\n" +
            "            <comment>\n" +
            "                Model should remain competely isolated\n" +
            "            </comment>\n" +
            "\n" +
            "            <packages>\n" +
            "                <package>\n" +
            "                    com.seventytwomiles.pagerank.core.model\n" +
            "                </package>\n" +
            "            </packages>\n" +
            "\n" +
            "            <violations>\n" +
            "                <violation>\n" +
            "                    com.seventytwomiles.pagerank.core.dao\n" +
            "                </violation>\n" +
            "                <violation>\n" +
            "                    com.seventytwomiles.pagerank.core.dao.hibernate\n" +
            "                </violation>\n" +
            "                <violation>\n" +
            "                    com.seventytwomiles.pagerank.core.services\n" +
            "                </violation>\n" +
            "                <violation>\n" +
            "                    com.seventytwomiles.pagerank.core.strategy\n" +
            "                </violation>\n" +
            "                <violation>\n" +
            "                    com.seventytwomiles.pagerank.core.builder\n" +
            "                </violation>\n" +
            "                <violation>\n" +
            "                    com.seventytwomiles.pagerank.util\n" +
            "                </violation>\n" +
            "            </violations>\n" +
            "        </rule>\n" +
            "\n" +
            "    </rules>\n" +
            "\n" +
            "</architecture>";


    /**
     * <p>Constructs a test case with the given name.</p>
     *
     * @param name name of test
     */
    public AbstractDigesterTest(String name) {
        super(name);
    }
}
