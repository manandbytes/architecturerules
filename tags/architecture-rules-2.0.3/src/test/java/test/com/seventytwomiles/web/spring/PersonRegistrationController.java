package test.com.seventytwomiles.web.spring;

/*
* Copyright 2007 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* For more information visit
* http://architecturerules.googlecode.com/svn/docs/index.html
*/


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

        final PersonDao personDao = new PersonDaoImpl();
    }
}