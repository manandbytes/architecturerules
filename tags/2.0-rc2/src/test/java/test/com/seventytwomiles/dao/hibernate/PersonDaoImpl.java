package test.com.seventytwomiles.dao.hibernate;


import test.com.seventytwomiles.dao.PersonDao;
import test.com.seventytwomiles.services.PersonService;
import test.com.seventytwomiles.services.PersonServiceImpl;


/**
 * <p>Test implemenation of the <code>PersonDao</code> that depends upon the
 * service layer.</p>
 *
 * @author mikenereson
 * @noinspection UnusedDeclaration
 * @see AbstractHibernateDao
 * @see PersonDao
 */
public class PersonDaoImpl extends AbstractHibernateDao implements PersonDao {


    private final PersonService personService = new PersonServiceImpl();


    public PersonDaoImpl() {

    }
}
