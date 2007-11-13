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
 * <p>Exception to be thrown when any <code>Rule</code> fails, that is to say,
 * the rule is violoated</p>
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see RuntimeException
 */
public class DependencyConstraintException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public DependencyConstraintException() {
        super("dependency constraint");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public DependencyConstraintException(String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public DependencyConstraintException(Throwable cause) {
        super("dependency constraint", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public DependencyConstraintException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * <p>Reports which <code>Rule</code> was broken, by its <tt>id</tt>, and
     * what packages that <code>Rule</code> governs.</p>
     *
     * @param ruleId
     * @param packages
     * @param cause
     */
    public DependencyConstraintException(final String ruleId, final String packages, final Throwable cause) {

        this("dependency constraint failed in '{id}' rule which constrains packages '{efferent}'"
                .replace("{id}", ruleId)
                .replace("{efferent}", packages)
                .replace("[", "")
                .replace("]", ""), cause);
    }
}
