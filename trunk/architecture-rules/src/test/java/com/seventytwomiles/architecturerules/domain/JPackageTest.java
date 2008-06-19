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
 *         http://architecturerules.googlecode.com/
 */

package com.seventytwomiles.architecturerules.domain;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;



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


    public JPackageTest(String name) {
        super(name);
    }


    /**
     * @see TestCase#setName(String)
     */
    public void setUp() throws Exception {
        super.setUp();
    }


    /**
     * @see TestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * <p>terminating <tt>*</tt> should only look in one package past the last
     * package, not any deeper. so <tt>a.b.*</tt> should match <tt>a.b.c</tt>
     * but NOT <tt>a.b.c.d</tt></p>
     */
    public void testMatchPackage_pass() throws Exception {


        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.project.dao.*");

        assertTrue(wild.matches(
                new JPackage("com.seventytwomiles.project.dao.hibernate")));

        assertFalse(wild.matches(new JPackage(
                "com.seventytwomiles.project.dao.hibernate.mock")));
    }


    public void testMatchPackage_fail() throws Exception {

        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.project.web.*");

        assertFalse(wild.matches(
                new JPackage("com.seventytwomiles.project.dao.hibernate")));

        /**
         * com.seventytwomiles.project.web.filters should match, but since this package
         * goes deeper (into security) it should fail
         */
        assertFalse(wild.matches(
                new JPackage(
                        "com.seventytwomiles.project.web.filters.security")));
    }


    /**
     * <p>terminating <tt>..*</tt> should match the last package, and all sub
     * packages. so <tt>a.b..*</tt> should match <tt>a.b.c</tt> and
     * <tt>a.b.c.d</tt>, <tt>a.b.c.e</tt>, <tt>a.b.c.f</tt> plus
     * <tt>a.b.c.d.1</tt>, <tt>a.b.c.d.2</tt>, <tt>a.b.c.d.3</tt></p>
     */
    public void testMatchSubPackage_pass() throws Exception {

        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.project..*");

        assertTrue(wild.matches(
                new JPackage("com.seventytwomiles.project.dao.hibernate")));

        assertTrue(wild.matches(
                new JPackage(
                        "com.seventytwomiles.project.dao.hibernate.mock")));

        assertTrue(wild.matches(
                new JPackage("com.seventytwomiles.project.dao.hibernate")));

        assertTrue(wild.matches(
                new JPackage(
                        "com.seventytwomiles.project.dao.hibernate.stub")));

    }


    public void testMatchSubPackage_fail() throws Exception {

        final JPackage wild;
        wild = new JPackage("com.seventytwomiles.project2..*");

        assertFalse(wild.matches(
                new JPackage("com.seventytwomiles.project.dao.hibernate")));

    }

}
