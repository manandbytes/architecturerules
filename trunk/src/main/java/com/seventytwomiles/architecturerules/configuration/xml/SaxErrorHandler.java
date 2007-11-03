package com.seventytwomiles.architecturerules.configuration.xml;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * <p>Implementation of <code>ErrorHandler</code> to handle errors within
 * commons digester parsing.</p>
 *
 * @author mnereson
 * @see ErrorHandler
 */
class SaxErrorHandler implements ErrorHandler {


    private static final Log log = LogFactory.getLog(SaxErrorHandler.class);


    /**
     * @see ErrorHandler#warning(SAXParseException)
     */
    public void warning(final SAXParseException exception) throws SAXException {

        log.warn(exception);

        onAnything(exception);
    }


    /**
     * @see ErrorHandler#error(SAXParseException)
     */
    public void error(final SAXParseException exception) throws SAXException {

        log.error("error", exception);

        onAnything(exception);
    }


    /**
     * @see ErrorHandler#fatalError(SAXParseException)
     */
    public void fatalError(final SAXParseException exception) throws SAXException {

        log.error("fatal error", exception);

        onAnything(exception);
    }


    private void onAnything(final SAXParseException exception) {
        // do nothing
    }
}