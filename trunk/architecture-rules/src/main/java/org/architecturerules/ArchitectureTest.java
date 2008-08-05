/**
 * Copyright 2007, 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://72miles.com and
 *         http://architecturerules.googlecode.com
 */
package org.architecturerules;


import java.io.File;

import org.architecturerules.configuration.Configuration;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;
import org.architecturerules.exceptions.CyclicRedundancyException;


/**
 * Silly little class that allows me to call .contains on a String. It was
 * created simply to prevent assertTrue(message.has("test.com.seventytwomiles.dao.hibernate"));
 * from wrapping to a new line.
 */
class Stringer {

    private final String string;

    Stringer(final String string) {

        this.string = string;
    }

    public boolean has(String contains) {

        return this.string.indexOf(contains) > -1;
    }
}
