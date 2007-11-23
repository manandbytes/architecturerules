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
 * <p>Exception to be thrown when a Rule is illegal constructed. That is, an
 * illegal violation is created.</p>
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see RuntimeException
 */
public class IllegalArchitectureRuleException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public IllegalArchitectureRuleException() {
        super("illegal architecture rule");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public IllegalArchitectureRuleException(String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public IllegalArchitectureRuleException(Throwable cause) {
        super("illegal architecture rule", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public IllegalArchitectureRuleException(String message, Throwable cause) {
        super(message, cause);
    }


    public IllegalArchitectureRuleException(final String ruleId, final String rulePackages) {

        this(ruleId, rulePackages, null);
    }


    public IllegalArchitectureRuleException(final String ruleId, final String rulePackages, final Throwable cause) {

        super("rule '{id}' contains an invalid violation that referes to itself; remove violation '{violation}' or change package"
                .replaceAll("\\{id}", ruleId)
                .replaceAll("\\{violation}", rulePackages.trim())
                .replaceAll("\\[", "")
                .replaceAll("\\]", ""),
              cause);
    }
}
