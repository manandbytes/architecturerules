/**
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com/svn/docs/index.html
 */

package org.springframework.util;


/**
 * <p>Miscellaneous class utility methods. Mainly for internal use within the
 * spring framework; consider Jakarta's Commons Lang for a more comprehensive
 * suite of class utilities.</p>
 *
 * <p>This was extracted from Spring in order to remove the dependency on apache
 * commons-io. <a href="http://code.google.com/p/architecturerules/issues/detail?id=2&can=1">
 * issue 2 (remove unneccessary dependencies)</a>/p>
 *
 * @author Keith Donald
 * @author Rob Harrop
 * @author Juergen Hoeller
 */
public class ClassUtils {


    /**
     * <p>Return a default <code>ClassLoader</code> to use (never
     * <code>null</code>). Returns the thread context ClassLoader, if available.
     * The ClassLoader that loaded the ClassUtils class will be used as
     * fallback. <p>Call this method if you intend to use the thread context
     * ClassLoader in a scenario where you absolutely need a non-null
     * ClassLoader reference: for example, for class path resource loading (but
     * not necessarily for <code>Class.forName</code>, which accepts a
     * <code>null</code> ClassLoader reference as well).</p>
     *
     * @return ClassLoader
     * @see java.lang.Thread#getContextClassLoader()
     */
    public static ClassLoader getDefaultClassLoader() {

        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();

        // No thread context class loader -> use class loader of this class.
        if (classLoader == null)
            classLoader = ClassUtils.class.getClassLoader();

        return classLoader;
    }
}
