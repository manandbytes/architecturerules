/**
 * Copyright 2007, 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://wiki.architecturerules.org/ and
 *         http://blog.architecturerules.org
 */
package org.architecturerules.services;


import jdepend.framework.JavaClass;
import jdepend.framework.JavaPackage;

import java.util.ArrayList;
import java.util.Collection;


public class JDependUtils {

    /**
     * @param importedPackage package's name
     * @param classes list of classes to check
     * @return list of classes which import <code>importedPackage</code>
     */
    public static Collection<String> buildClasses(String importedPackage, Collection<JavaClass> classes) {

        Collection<String> result = new ArrayList<String>();

        for (JavaClass javaClass : classes) {

            if (javaClass.getImportedPackages().contains(new JavaPackage(importedPackage))) {

                result.add(javaClass.getName());
            }
        }

        return result;
    }


    public static StringBuffer prettyPrintListOfClasses(Collection<String> package1DependenciesOnPackage2) {

        final StringBuffer listOfClasses = new StringBuffer();

        for (String javaClassName : package1DependenciesOnPackage2) {

            listOfClasses.append("\t|\t");
            listOfClasses.append(" |-- @ ");
            listOfClasses.append(javaClassName);
            listOfClasses.append("\n");
        }

        return listOfClasses;
    }
}
