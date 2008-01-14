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
* For more information visit
* http://architecturerules.googlecode.com/svn/docs/index.html
*/


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
* For more information visit
* http://architecturerules.googlecode.com/svn/docs/index.html
*/
/**
 * <p>RuntimeException that is thrown when a configuration is invalid. A
 * configuraiton could be invalid for a number of reasons but happens most
 * regularly in the creation of <code>Rules</code>.</p>
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see Exception
 */
public class InvalidConfigurationException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public InvalidConfigurationException() {
        super();
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public InvalidConfigurationException(final String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public InvalidConfigurationException(final Throwable cause) {
        super(cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public InvalidConfigurationException(final String message,
                                         final Throwable cause) {
        super(message, cause);
    }
}
