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
package org.architecturerules.domain;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;


/**
 * WildcardJPackage Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>06/17/2008</pre>
 */
public class JPackageTest extends TestCase {

    public static Test suite() {

        return new TestSuite(JPackageTest.class);
    }

    public JPackageTest(final String name) {
        super(name);
    }

    /**
     * @see TestCase#setName(String)
     */
    @Override
    public void setUp()
            throws Exception {

        super.setUp();
    }


    /**
     * @see TestCase#tearDown()
     */
    @Override
    public void tearDown()
            throws Exception {

        super.tearDown();
    }


    public void testMatch_perfect_pass()
            throws Exception {

        final JPackage jPackage1;
        jPackage1 = new JPackage("com.seventytwomiles.project.dao");

        final JPackage jPackage2;
        jPackage2 = new JPackage("com.seventytwomiles.project.dao");

        assertTrue(jPackage1.equals(jPackage2));
        assertTrue(jPackage2.equals(jPackage1));

        final List<JPackage> packages = new ArrayList<JPackage>();
        assertTrue(packages.add(jPackage1));

        assertTrue(packages.contains(jPackage1));
        assertTrue(packages.contains(jPackage2));
    }


    public void testMatch_perfect_fail()
            throws Exception {

        final JPackage jPackage1;
        jPackage1 = new JPackage("com.seventytwomiles.project.service");

        final JPackage jPackage2;
        jPackage2 = new JPackage("com.seventytwomiles.project.dao");

        assertFalse(jPackage1.equals(jPackage2));
        assertFalse(jPackage2.equals(jPackage1));

        assertFalse(jPackage1.equals(new JPackage("")));
        assertFalse(jPackage1.equals(""));

        final List<JPackage> packages = new ArrayList<JPackage>();
        packages.add(jPackage1);

        assertFalse(packages.contains(jPackage2));
    }


    /**
     * <p>terminating <tt>*</tt> should only look in one package past the last package, not any deeper. so
     * <tt>a.b.*</tt> should match <tt>a.b.c</tt> but NOT <tt>a.b.c.d</tt></p>
     */
    public void testMatchPackage_wildcards_pass()
            throws Exception {

        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.project.dao.*");

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project.dao.hibernate")));

        assertFalse(wild.matches(new JPackage("com.seventytwomiles.project.dao.hibernate.mock")));
    }


    public void testMatchPackage_wildcards_fail()
            throws Exception {

        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.project.web.*");

        assertFalse(wild.matches(new JPackage("com.seventytwomiles.project.dao.hibernate")));

        /**
         * com.seventytwomiles.project.web.filters should match, but since this package
         * goes deeper (into security) it should fail
         */
        assertFalse(wild.matches(new JPackage("com.seventytwomiles.project.web.filters.security")));
    }


    /**
     * <p>terminating <tt>..*</tt> should match the last package, and all sub packages. so <tt>a.b..*</tt> should match
     * <tt>a.b.c</tt> and <tt>a.b.c.d</tt>, <tt>a.b.c.e</tt>, <tt>a.b.c.f</tt> plus <tt>a.b.c.d.1</tt>,
     * <tt>a.b.c.d.2</tt>, <tt>a.b.c.d.3</tt></p>
     */
    public void testMatchSubPackage_wildcards_pass()
            throws Exception {

        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.project..*");

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project.dao.hibernate")));

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project.dao.hibernate.mock")));

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project.dao.hibernate")));

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project.dao.hibernate.stub")));
    }


    public void testMatchSubPackage_wildcards_fail()
            throws Exception {

        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.project2..*");

        assertFalse(wild.matches(new JPackage("com.seventytwomiles.project.dao.hibernate")));
    }


    public void testMatchInnerPackage_wildcards_pass()
            throws Exception {

        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.*.dao.hibernate");

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project1.dao.hibernate")));

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project2.dao.hibernate")));

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project3.dao.hibernate")));
    }


    public void testMatchInnerPackage_wildcards_fail()
            throws Exception {

        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.*.dao.hibernate");

        assertFalse(wild.matches(new JPackage("com.seventytwomiles.project1.services")));

        assertFalse(wild.matches(new JPackage("com.seventytwomiles.dao.hibernate")));

        assertFalse(wild.matches(new JPackage("com.seventytwomiles.*.dao.hibernate")));
    }


    public void testMatchInnerAndTerminatingPackage_wildcards_pass()
            throws Exception {

        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.*.dao.*");

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project1.dao.hibernate")));

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project2.dao.hibernate")));

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project1.dao.ldap")));

        assertTrue(wild.matches(new JPackage("com.seventytwomiles.project3.dao.jdbc")));
    }


    public void testMatchInnerAndTerminatingPackage_pass()
            throws Exception {

        assertTrue(new JPackage("com.seventytwomiles..*.filter.*").matches(new JPackage("com.seventytwomiles.project1.web.filter.security")));

        assertTrue(new JPackage("com.seventytwomiles..*.filter").matches(new JPackage("com.seventytwomiles.project1.web.filter")));

        assertTrue(new JPackage("com.seventytwomiles..*.filter..*").matches(new JPackage("com.seventytwomiles.project1.web.filter.security.ws")));

        assertTrue(new JPackage("com.seventytwomiles.*.web.*").matches(new JPackage("com.seventytwomiles.project1.web.filter")));

        assertTrue(new JPackage("com.seventytwomiles.*.web..*").matches(new JPackage("com.seventytwomiles.project1.web.filter")));

        assertTrue(new JPackage("com.seventytwomiles.*.web..*").matches(new JPackage("com.seventytwomiles.project1.web.filter.security")));

        assertTrue(new JPackage("com..*").matches(new JPackage("com.seventytwomiles.project1")));
    }


    public void testMatchInnerAndTerminatingPackage_fail()
            throws Exception {

        assertFalse(new JPackage("com.seventytwomiles..*.filter.*").matches(new JPackage("com.seventytwomiles.project1.web.controller.api")));

        assertFalse(new JPackage("com.seventytwomiles..*.filter").matches(new JPackage("com.seventytwomiles.filter")));

        assertFalse(new JPackage("com.seventytwomiles..*.filter..*").matches(new JPackage("com.seventytwomiles.filter.security.ws")));

        assertFalse(new JPackage("com.seventytwomiles.*.web.*").matches(new JPackage("com.seventytwomiles.project1.web")));

        assertFalse(new JPackage("com.seventytwomiles.*.web..*").matches(new JPackage("com.seventytwomiles.web.filter")));

        assertFalse(new JPackage("com.seventytwomiles.*.web..*").matches(new JPackage("com.seventytwomiles.project1.web")));

        assertFalse(new JPackage("com.*").matches(new JPackage("com.seventytwomiles.project1")));
    }


    public void testCompareEquals() {

        final JPackage package1 = new JPackage("");
        final JPackage package2 = new JPackage("");
        assertEquals(0, package1.compareTo(package2));
        assertEquals(0, package2.compareTo(package1));
    }


    public void testCompareAgainstNull() {

        try {

            new JPackage("").compareTo(null);
        } catch (NullPointerException e) {

        }
    }
}
