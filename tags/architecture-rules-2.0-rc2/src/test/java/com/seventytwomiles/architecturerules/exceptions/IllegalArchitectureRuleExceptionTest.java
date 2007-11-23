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
 * <code>IllegalArchitectureRuleException Tester.</code>
 *
 * @author mikenereson
 */
public class IllegalArchitectureRuleExceptionTest extends TestCase {


    public IllegalArchitectureRuleExceptionTest(String name) {
        super(name);
    }


    public void testTypicalConstructors() {

        IllegalArchitectureRuleException exception;
        String message;
        Throwable cause;


        exception = new IllegalArchitectureRuleException();
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("illegal architecture rule", message);
        assertEquals(null, cause);


        exception = new IllegalArchitectureRuleException("oops, you broke an architecture rule");
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("oops, you broke an architecture rule", message);
        assertEquals(null, cause);


        exception = new IllegalArchitectureRuleException(new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("illegal architecture rule", message);
        assertTrue(cause instanceof IllegalArgumentException);


        exception = new IllegalArchitectureRuleException("oops, you broke an architecture rule", new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("oops, you broke an architecture rule", message);
        assertTrue(cause instanceof IllegalArgumentException);
    }


    public void testInterestingConstructors() {

        IllegalArchitectureRuleException exception;
        String message;
        Throwable cause;

        final Rule rule = new Rule();
        rule.setId("dao");
        rule.setComment("dao layer");
        assertTrue(rule.addPackage("com.seventytwomiles.dao"));


        exception = new IllegalArchitectureRuleException(rule.getId(), rule.describePackges());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("rule 'dao' contains an invalid violation that referes to itself; remove violation 'com.seventytwomiles.dao' or change package", message);
        assertEquals(null, cause);


        exception = new IllegalArchitectureRuleException(rule.getId(), rule.describePackges(), new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("rule 'dao' contains an invalid violation that referes to itself; remove violation 'com.seventytwomiles.dao' or change package", message);
        assertTrue(cause instanceof IllegalArgumentException);

    }
}
