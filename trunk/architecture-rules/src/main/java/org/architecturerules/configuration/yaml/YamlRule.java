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

/**
 *
 */
package org.architecturerules.configuration.yaml;


import java.util.Collection;

import org.architecturerules.domain.Rule;


/**
 * This class is JavaBeans-compliant and just mimics {@link Rule}, which is not.
 *
 * @see http://java.sun.com/docs/books/tutorial/javabeans/index.html
 * @author mn
 */
public class YamlRule {

    public YamlRule() {

        // TODO Auto-generated constructor stub
    }

    private String id;

    public String getId() {

        return id;
    }


    public void setId(String id) {

        this.id = id;
    }

    Collection<String> packages;

    public Collection<String> getPackages() {

        return packages;
    }


    public void setPackages(Collection<String> packages) {

        this.packages = packages;
    }

    Collection<String> violations;

    public Collection<String> getViolations() {

        return violations;
    }


    public void setViolations(Collection<String> violations) {

        this.violations = violations;
    }
}
