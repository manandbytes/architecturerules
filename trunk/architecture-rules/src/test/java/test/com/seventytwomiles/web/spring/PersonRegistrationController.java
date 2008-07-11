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

package test.com.seventytwomiles.web.spring;


import test.com.seventytwomiles.dao.PersonDao;
import test.com.seventytwomiles.dao.hibernate.PersonDaoImpl;



/**
 * <p>Test Controller that depends on the DAO layer.</p>
 *
 * @author mikenereson
 * @noinspection UnusedDeclaration
 */
class PersonRegistrationController {


    /**
     * @noinspection UnusedAssignment
     */
    public PersonRegistrationController() {

        new PersonDaoImpl();
    }
}
