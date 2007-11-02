package com.seventytwomiles.architecturerules.exceptions;


import com.seventytwomiles.architecturerules.domain.SourceDirectory;
import junit.framework.TestCase;


/**
 * <code>NoPackagesFoundException Tester.</code>
 *
 * @author mnereson
 */
public class NoPackagesFoundExceptionTest extends TestCase {


    public NoPackagesFoundExceptionTest(String name) {
        super(name);
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

        assertTrue(message.equals("source directory '/core/target/classes' does not exist or can not be found"));
        assertEquals(null, cause);


        exception = new NoPackagesFoundException(new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("no packages found"));
        assertTrue(cause instanceof IllegalArgumentException);


        exception = new NoPackagesFoundException("/core/target/classes directory not found", new IllegalArgumentException());
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("/core/target/classes directory not found"));
        assertTrue(cause instanceof IllegalArgumentException);
    }


    public void testInterestingConstructors() {

        NoPackagesFoundException exception;
        String message;
        Throwable cause;

        final SourceDirectory sourceDirectory = new SourceDirectory();
        sourceDirectory.setPath("core/target/classes");

        exception = new NoPackagesFoundException(sourceDirectory.getPath());
        message = exception.getMessage();
        cause = exception.getCause();

        assertTrue(message.equals("source directory 'core/target/classes' does not exist or can not be found"));
        assertEquals(null, cause);
    }
}
