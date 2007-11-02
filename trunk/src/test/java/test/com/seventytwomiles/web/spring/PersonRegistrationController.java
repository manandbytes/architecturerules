package test.com.seventytwomiles.web.spring;


import test.com.seventytwomiles.dao.PersonDao;
import test.com.seventytwomiles.dao.hibernate.PersonDaoImpl;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @noinspection UnusedDeclaration
 */
public class PersonRegistrationController {


    public PersonRegistrationController() {

        final PersonDao personDao = new PersonDaoImpl();
    }
}
