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

package com.seventytwomiles.architecturerules.domain;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static java.lang.String.format;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * <p>A java package. This class wraps the java package to give it
 * functionality, such as the ability to check and see if it matches another
 * package.</p>
 *
 * @author mikenereson
 */
public class JPackage {


    private static final Log log
            = LogFactory.getLog(JPackage.class);

    String path;


    public JPackage() {
    }


    public JPackage(final String path) {
        this.path = path;
    }


    public String getPath() {
        return path;
    }


    public void setPath(final String path) {
        this.path = path;
    }


    public boolean equals(final Object o) {

        return matches(o);
    }


    public int hashCode() {
        return (path != null ? path.hashCode() : 0);
    }


    public String toString() {
        return this.path;
    }


    public boolean matches(final Object that) {

        if (!(that instanceof String)
                && !(that instanceof JPackage)) {

            return false;
        }

        if (this.path.contains("*")) {

            return regExMatch(that);

        } else {

            return prefectMatch(that);

        }
    }


    private boolean regExMatch(final Object that) {

        final String regex = this.path
                .replaceAll("\\.", "\\\\.")  // foo.bar exactly foo.bar
                .replaceAll("\\\\.\\\\.\\\\*", "\\\\.\\[A-Za-z_0-9.]")
                .replaceAll("\\.\\*", "\\.[A-Za-z_0-9]*"); // packages only

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher;

        boolean matched;
        /**
         * Test Strings match
         */
        if (that instanceof String) {

            final String thatPackage = (String) that;
            matcher = pattern.matcher(thatPackage);

            matched = matcher.matches();

        } else if (that instanceof JPackage) {

            final JPackage thatPackage = (JPackage) that;
            matcher = pattern.matcher(thatPackage.getPath());

            matched = matcher.matches();

        } else {

            matched = false;
        }

        if (matched)
            System.out.println(format("matched %s to %s", this.path, that));

        return matched;
    }


    private boolean prefectMatch(final Object that) {
        /**
         * Test Strings match
         */
        if (that instanceof String) {

            final String thatPackage = (String) that;
            return this.path.equals(thatPackage);
        }

        if (that instanceof JPackage) {

            final JPackage thatPackage = (JPackage) that;
            return this.path.equals(thatPackage.getPath());
        }

        return false;
    }
}
