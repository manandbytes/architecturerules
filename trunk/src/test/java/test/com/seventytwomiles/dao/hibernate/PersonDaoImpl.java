package test.com.seventytwomiles.dao.hibernate;


import test.com.seventytwomiles.dao.PersonDao;
import test.com.seventytwomiles.services.PersonService;
import test.com.seventytwomiles.services.PersonServiceImpl;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @noinspection UnusedDeclaration
 * @see AbstractHibernateDao
 * @see PersonDao
 */
public class PersonDaoImpl extends AbstractHibernateDao implements PersonDao {


    private PersonService personService = new PersonServiceImpl();


    public PersonDaoImpl() {

    }
}
