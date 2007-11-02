package test.com.seventytwomiles.model;


import test.com.seventytwomiles.services.PersonService;
import test.com.seventytwomiles.services.PersonServiceImpl;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @noinspection UnusedDeclaration
 */
public class Person {


    public Person() {

        final PersonService personService = new PersonServiceImpl();

    }
}
