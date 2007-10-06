package com.seventytwomiles.architecturerules.exceptions;


import junit.framework.TestCase;


/**
 * <code>CyclicRedundancyException Tester.</code>
 *
 * @author mnereson
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
