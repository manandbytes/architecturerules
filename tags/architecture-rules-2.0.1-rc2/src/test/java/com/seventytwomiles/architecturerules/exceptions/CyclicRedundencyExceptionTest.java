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


import junit.framework.TestCase;


/**
 * <code>CyclicRedundancyException Tester.</code>
 *
 * @author mikenereson
 */
public class CyclicRedundencyExceptionTest extends TestCase {


    public CyclicRedundencyExceptionTest(String name) {
        super(name);
    }


    public void testTypicalConstructors() {

        CyclicRedundancyException exception;
        String message;
        Throwable cause;

        exception = new CyclicRedundancyException();
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("cyclic redundancy", message);
        assertEquals(null, cause);

        exception = new CyclicRedundancyException("cyclic redundancy found");
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("cyclic redundancy found", message);
        assertEquals(null, cause);


        exception = new CyclicRedundancyException(new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("cyclic redundancy", message);
        assertTrue(cause instanceof IllegalArgumentException);


        exception = new CyclicRedundancyException("cyclic redundancy found", new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("cyclic redundancy found", message);
        assertTrue(cause instanceof IllegalArgumentException);
    }


    public void testInterestingConstructors() {

        CyclicRedundancyException exception;
        String message;
        Throwable cause;


        exception = new CyclicRedundancyException("com.seventytwomiles.dao", "com.seventytwomiles.dao.hibernate");
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals("'com.seventytwomiles.dao' is involved in an cyclically redundant dependency with 'com.seventytwomiles.dao.hibernate'", message);
        assertEquals(null, cause);
    }
}
