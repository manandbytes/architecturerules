package test.com.seventytwomiles.services;


import test.com.seventytwomiles.dao.PersonDao;
import test.com.seventytwomiles.dao.hibernate.PersonDaoImpl;
import test.com.seventytwomiles.model.Person;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @noinspection UnusedDeclaration
 */
public class PersonServiceImpl implements PersonService {


    private Collection persons = new HashSet();
    private PersonDao personDao = new PersonDaoImpl();


    public void createPerson(final Person person) {
        persons.add(person);
    }


    public Collection loadPersons() {
        return Collections.unmodifiableCollection(persons);
    }
}
