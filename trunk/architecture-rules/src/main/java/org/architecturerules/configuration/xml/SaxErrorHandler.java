/**
 * Copyright 2007, 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com/ and
 *         http://architecturerules.googlecode.com
 */
package org.architecturerules.configuration.xml;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * <p>Implementation of <code>ErrorHandler</code> to handle errors within commons digester parsing.</p>
 *
 * @author mikenereson
 * @see ErrorHandler
 */
class SaxErrorHandler implements ErrorHandler {

    /**
     * <p>To log with. See <tt>log4j.xml</tt>.</p>
     *
     * @parameter log Log
     */
    protected static final Log log = LogFactory.getLog(SaxErrorHandler.class);

    /**
     * @see ErrorHandler#error(SAXParseException)
     */
    public void error(final SAXParseException exception)
            throws SAXException {

        log.error("error", exception);
    }


    /**
     * @see ErrorHandler#fatalError(SAXParseException)
     */
    public void fatalError(final SAXParseException exception)
            throws SAXException {

        log.error("fatal error", exception);
    }


    /**
     * @see ErrorHandler#warning(SAXParseException)
     */
    public void warning(final SAXParseException exception)
            throws SAXException {

        log.warn(exception);
    }
}
