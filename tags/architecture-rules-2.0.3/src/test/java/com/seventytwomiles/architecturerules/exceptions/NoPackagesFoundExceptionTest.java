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
 * For more information visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import junit.framework.TestCase;



/**
 * <code>NoPackagesFoundException Tester.</code>
 *
 * @author mikenereson
 */
public class NoPackagesFoundExceptionTest extends TestCase {


    public NoPackagesFoundExceptionTest(final String name) {
        super(name);
    }


    public void testInterestingConstructors() {

        final NoPackagesFoundException exception;
        final String message;
        final Throwable cause;

        final SourceDirectory sourceDirectory = new SourceDirectory();
        sourceDirectory.setPath("core/target/classes");

        exception = new NoPackagesFoundException(sourceDirectory.getPath());
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals(
                "source directory 'core/target/classes' does not exist or can not be found",
                message);
        assertEquals(null, cause);
    }


    public void testTypicalConstructors() {

        NoPackagesFoundException exception;
        String message;
        Throwable cause;


        exception = new NoPackagesFoundException();
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("no packages found"));
        assertEquals(null, cause);


        exception = new NoPackagesFoundException("/core/target/classes");
        message = exception.getMessage();
        cause = exception.getCause();

        assertEquals(
                "source directory '/core/target/classes' does not exist or can not be found",
                message);
        assertEquals(null, cause);


        exception = new NoPackagesFoundException(
                new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("no packages found"));
        assertTrue(cause instanceof IllegalArgumentException);


        exception = new NoPackagesFoundException(
                "/core/target/classes directory not found",
                new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("/core/target/classes directory not found"));
        assertTrue(cause instanceof IllegalArgumentException);
    }
}
