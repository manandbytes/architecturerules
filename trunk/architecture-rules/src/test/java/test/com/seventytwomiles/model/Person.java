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
 *         http://architecturerules.googlecode.com/
 */
package test.com.seventytwomiles.model;


import test.com.seventytwomiles.services.PersonServiceImpl;


/**
 * <p>Test model object</p>
 *
 * @author mikenereson
 * @noinspection UnusedDeclaration
 */
public class Person {

    private int id;

    /**
     * @noinspection UnusedAssignment
     */
    public Person() {

        new PersonServiceImpl();
    }


    public Person(final int id) {

        this.id = id;
    }

    /**
     * Getter for property 'id'.
     *
     * @return Value for property 'id'.
     */
    public int getId() {

        return id;
    }
}
