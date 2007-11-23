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


import java.util.Collection;


/**
 * <p>Exception to be thrown when a configured source is not found and
 * <samp>&lt;source not-found="exception"></samp></p>
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see RuntimeException
 */
public class SourceNotFoundException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public SourceNotFoundException() {
        super("sources not found");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public SourceNotFoundException(final String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public SourceNotFoundException(final Throwable cause) {
        super("sources not found", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public SourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public SourceNotFoundException(final Collection sources) {

        super("unable to find any source files in given source directories {0}"
                .replaceAll("\\{0}", sources.toString()).
                replaceAll("\\[", "")
                .replaceAll("\\]", ""));
    }
}
