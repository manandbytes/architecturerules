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


import java.util.Arrays;
import java.util.List;


/**
 * <p>Thrown to indicate that a cyclic redendency was found.</p>
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see RuntimeException
 */
public class CyclicRedundancyException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public CyclicRedundancyException() {
        super("cyclic redundancy");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public CyclicRedundancyException(String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public CyclicRedundancyException(Throwable cause) {
        super("cyclic redundancy", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public CyclicRedundancyException(String message, Throwable cause) {
        super(message, cause);
    }


    public CyclicRedundancyException(final String packageName, final String efferentPackage) {

        super("'{0}' is involved in an cyclically redundant dependency with '{1}'"
                .replace("{0}", packageName)
                .replace("{1}", efferentPackage));
    }


    public CyclicRedundancyException(final List cycles) {

        super("cyclic redundencies were found in each of the following packages: "
              + Arrays.deepToString(cycles.toArray()));
    }

}
