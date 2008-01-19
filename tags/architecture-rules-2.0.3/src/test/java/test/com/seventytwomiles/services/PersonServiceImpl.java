package test.com.seventytwomiles.services;

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
import test.com.seventytwomiles.model.Person;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;



/**
 * <p>Test implementation of the PersonService that depends upon the DAO layer
 * and the model package.</p>
 *
 * @author mikenereson
 * @noinspection UnusedDeclaration
 */
public class PersonServiceImpl implements PersonService {


    private final Collection persons = new HashSet();
    private final PersonDao personDao = new PersonDaoImpl();


    public void createPerson(final Person person) {
        persons.add(person);
    }


    public Collection loadPersons() {
        return Collections.unmodifiableCollection(persons);
    }
}
