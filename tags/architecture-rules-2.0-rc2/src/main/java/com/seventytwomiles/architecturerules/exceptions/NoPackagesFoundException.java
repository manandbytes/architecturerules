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


/**
 * <p>Exception to be thrown when no packages are found in the given source path
 * if <samp>&lt;sources no-packages="exception"> </samp></p>
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see RuntimeException
 */
public class NoPackagesFoundException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public NoPackagesFoundException() {
        super("no packages found");
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public NoPackagesFoundException(final Throwable cause) {
        super("no packages found", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public NoPackagesFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public NoPackagesFoundException(final String path) {

        super("source directory '{0}' does not exist or can not be found"
                .replace("{0}", path));
    }
}
