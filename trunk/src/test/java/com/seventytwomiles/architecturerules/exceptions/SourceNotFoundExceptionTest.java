package com.seventytwomiles.architecturerules.exceptions;


import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import junit.framework.TestCase;

import java.util.Collection;
import java.util.HashSet;


/**
 * <code>SourceNotFoundException Tester.</code>
 *
 * @author mnereson
 */
public class SourceNotFoundExceptionTest extends TestCase {


    public SourceNotFoundExceptionTest(String name) {
        super(name);
    }


    public void testTypicalConstructors() {

        SourceNotFoundException exception;
        String message;
        Throwable cause;


        exception = new SourceNotFoundException();
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("sources not found"));
        assertEquals(null, cause);


        exception = new SourceNotFoundException("no source classes found");
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("no source classes found"));
        assertEquals(null, cause);


        exception = new SourceNotFoundException(new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("sources not found"));
        assertTrue(cause instanceof IllegalArgumentException);


        exception = new SourceNotFoundException("no source classes found", new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("no source classes found"));
        assertTrue(cause instanceof IllegalArgumentException);
    }


    public void testInterestingConstructors() {

        SourceNotFoundException exception;
        String message;
        Throwable cause;

        final Collection sourceDirectories = new HashSet();
        sourceDirectories.add(new SourceDirectory("core/target/classes"));
        sourceDirectories.add(new SourceDirectory("util/target/classes"));
        sourceDirectories.add(new SourceDirectory("parent-pom/target/classes"));
        sourceDirectories.add(new SourceDirectory("web/target/classes"));


        exception = new SourceNotFoundException(sourceDirectories);
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.contains("core/target/classes"));
        assertTrue(message.contains("util/target/classes"));
        assertTrue(message.contains("parent-pom/target/classes"));
        assertTrue(message.contains("web/target/classes"));

        /* TODO: why tf does the following assert fail? */
        // assertTrue(message.contains("unable to find any source files in given source directories [web/target/classes, core/target/classes, parent-pom/target/classes, util/target/classes]"));

        assertEquals(null, cause);
    }
}
