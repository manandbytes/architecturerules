package com.seventytwomiles.architecturerules.exceptions;

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
* For more infomration visit
* http://architecturerules.googlecode.com/svn/docs/index.html
*/


import com.seventytwomiles.architecturerules.domain.Rule;
import junit.framework.TestCase;


/**
 * <code>DependencyConstraintException Tester.</code>
 *
 * @author mikenereson
 */
public class DependencyConstraintExceptionTest extends TestCase {


    public DependencyConstraintExceptionTest(String name) {
        super(name);
    }


    public void testTypicalConstructors() {

        DependencyConstraintException exception;
        String message;
        Throwable cause;

        final Rule rule = new Rule();
        rule.setId("dao");
        rule.setComment("dao layer");
        assertTrue(rule.addPackage("com.seventytwomiles.dao"));
        rule.addViolation("com.seventytwomiles.web.controllers");

        exception = new DependencyConstraintException();
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("dependency constraint", message);
        assertEquals(null, cause);

        exception = new DependencyConstraintException("dependency constraint violated");
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("dependency constraint violated", message);
        assertEquals(null, cause);


        exception = new DependencyConstraintException(new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("dependency constraint", message);
        assertTrue(cause instanceof IllegalArgumentException);


        exception = new DependencyConstraintException("dependency constraint violated", new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("dependency constraint violated", message);
        assertTrue(cause instanceof IllegalArgumentException);


    }


    public void testInterestingConstructors() {

        DependencyConstraintException exception;
        String message;
        Throwable cause;

        final Rule rule = new Rule();
        rule.setId("dao");
        rule.setComment("dao layer");
        assertTrue(rule.addPackage("com.seventytwomiles.dao"));
        rule.addViolation("com.seventytwomiles.web.controllers");

        exception = new DependencyConstraintException(rule.getId(), rule.describePackges(), null);
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("dependency constraint failed in 'dao' rule which constrains packages 'com.seventytwomiles.dao'", message);
        assertEquals(null, cause);


        exception = new DependencyConstraintException(rule.getId(), rule.describePackges(), new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("dependency constraint failed in 'dao' rule which constrains packages 'com.seventytwomiles.dao'", message);
        assertTrue(cause instanceof IllegalArgumentException);
    }
}
