package test.com.seventytwomiles.model;

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


import test.com.seventytwomiles.services.PersonService;
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

        final PersonService personService = new PersonServiceImpl();

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
