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
