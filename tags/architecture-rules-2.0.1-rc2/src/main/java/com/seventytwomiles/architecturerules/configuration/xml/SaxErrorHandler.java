package com.seventytwomiles.architecturerules.configuration.xml;

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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * <p>Implementation of <code>ErrorHandler</code> to handle errors within
 * commons digester parsing.</p>
 *
 * @author mikenereson
 * @see ErrorHandler
 */
class SaxErrorHandler implements ErrorHandler {


    private static final Log log = LogFactory.getLog(SaxErrorHandler.class);


    /**
     * @see ErrorHandler#warning(SAXParseException)
     */
    public void warning(final SAXParseException exception) throws SAXException {
        log.warn(exception);
    }


    /**
     * @see ErrorHandler#error(SAXParseException)
     */
    public void error(final SAXParseException exception) throws SAXException {
        log.error("error", exception);
    }


    /**
     * @see ErrorHandler#fatalError(SAXParseException)
     */
    public void fatalError(final SAXParseException exception) throws SAXException {

        log.error("fatal error", exception);
    }
}