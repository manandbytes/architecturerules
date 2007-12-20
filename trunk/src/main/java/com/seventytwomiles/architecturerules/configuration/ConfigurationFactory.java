package com.seventytwomiles.architecturerules.configuration;

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


import java.util.Collection;
import java.util.List;



/**
 * <p>Interface for <code>ConfigurationFactory</code> implementations to adhere
 * to.</p>
 *
 * @author mikenereson
 */
public interface ConfigurationFactory {


    /**
     * @return boolean <tt>true</tt> when <samp>&lt;cyclicalDependency
     *         test="true"/> </samp>
     */
    boolean doCyclicDependencyTest();


    /**
     * <p>Getter for property {@link /rules}.</p>
     *
     * @return Value for property <tt>rules</tt>.
     */
    Collection getRules();


    /**
     * <p>Getter for property {@link //sources}.</p>
     *
     * @return Value for property <tt>sources</tt>.
     */
    List getSources();


    /**
     * @return boolean <tt>true</tt> when <samp>&lt;sources
     *         no-packages="exception"> </samp>
     */
    boolean throwExceptionWhenNoPackages();
}
