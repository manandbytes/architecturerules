package com.seventytwomiles.architecturerules.configuration.xml;


import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see ErrorHandler
 */
public class SaxErrorHandler implements ErrorHandler {


    /**
     * @see ErrorHandler#warning(SAXParseException)
     */
    public void warning(final SAXParseException exception) throws SAXException {
        System.out.println(exception.getMessage());

        onAnything(exception);
    }


    /**
     * @see ErrorHandler#error(SAXParseException)
     */
    public void error(final SAXParseException exception) throws SAXException {
        System.out.println(exception.getMessage());

        onAnything(exception);
    }


    /**
     * @see ErrorHandler#fatalError(SAXParseException)
     */
    public void fatalError(final SAXParseException exception) throws SAXException {
        System.out.println(exception.getMessage());

        onAnything(exception);
    }


    public void onAnything(final SAXParseException exception) {
        // do nothing
    }
}