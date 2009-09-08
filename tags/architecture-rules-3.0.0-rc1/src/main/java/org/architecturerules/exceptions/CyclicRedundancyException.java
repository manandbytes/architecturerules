/**
 * Copyright 2007 - 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://wiki.architecturerules.org/ and
 *         http://blog.architecturerules.org/
 */
package org.architecturerules.exceptions;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * <p>Thrown to indicate that a cyclic redundancy was found.</p>
 *
 * @author mikenereson
 * @see ArchitectureException
 */
public class CyclicRedundancyException extends ArchitectureException {

    /**
     * <p>Holds the cycles by package name. The Map key is the full package and the key is a Set of packages that are
     * involved in the cycle.</p>
     */
    protected Map<String, Set<String>> cycles = new HashMap<String, Set<String>>();

    /**
     * @see RuntimeException#RuntimeException()
     */
    public CyclicRedundancyException() {
        super("cyclic redundancy");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public CyclicRedundancyException(final String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public CyclicRedundancyException(final Throwable cause) {
        super("cyclic redundancy", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public CyclicRedundancyException(final String message, final Throwable cause) {
        super(message, cause);
    }


    /**
     * <p>Constructs a new CyclicRedundancyException with a generated message containing the given <tt>packageName</tt>
     * and <tt>efferentPackage</tt>.</p>
     *
     * @param packageName String the name of the package containing the cyclic dependency
     * @param efferentPackage String the name of the package the package is cyclically involved with.
     */
    public CyclicRedundancyException(final String packageName, final String efferentPackage) {
        super("'{0}' is involved in an cyclically redundant dependency with '{1}'".replaceAll("\\{0}", packageName).replaceAll("\\{1}", efferentPackage));
    }

    public Map<String, Set<String>> getCycles() {

        return this.cycles;
    }
}
