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
 *         http://72miles.com/ and
 *         http://architecturerules.googlecode.com/
 */
package test.com.seventytwomiles.dao.hibernate;


import test.com.seventytwomiles.dao.Dao;
import test.com.seventytwomiles.model.Person;


/**
 * <p>Test abstract DAO class that depends on the model package and dao package.</p>
 *
 * @author mikenereson
 */
public class AbstractHibernateDao implements Dao {

    public AbstractHibernateDao() {

    }

    Person loadPerson(final int id) {

        Person person = new Person(id);

        return person;
    }
}
