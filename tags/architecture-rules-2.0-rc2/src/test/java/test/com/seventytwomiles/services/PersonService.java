package test.com.seventytwomiles.services;


import test.com.seventytwomiles.model.Person;

import java.util.Collection;


/**
 * <p>Test service interface.</p>
 *
 * @author mikenereson
 */
public interface PersonService {


    void createPerson(final Person person);


    Collection loadPersons();
}
