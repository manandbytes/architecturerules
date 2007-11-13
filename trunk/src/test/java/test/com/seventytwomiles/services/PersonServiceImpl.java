package test.com.seventytwomiles.services;


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
