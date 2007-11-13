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

        final PersonDao personDao = new PersonDaoImpl();
    }
}
