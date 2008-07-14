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
package test.com.seventytwomiles.dao.hibernate;

import test.com.seventytwomiles.dao.PersonDao;
import test.com.seventytwomiles.services.PersonService;
import test.com.seventytwomiles.services.PersonServiceImpl;

/**
 * <p>Test implementation of the <code>PersonDao</code> that depends upon the
 * service layer.</p>
 *
 * @author mikenereson
 * @noinspection UnusedDeclaration
 * @see AbstractHibernateDao
 * @see PersonDao
 */
public class PersonDaoImpl
    extends AbstractHibernateDao
    implements PersonDao
{
    private final PersonService personService = new PersonServiceImpl(  );

    public PersonDaoImpl(  )
    {
    }
}
