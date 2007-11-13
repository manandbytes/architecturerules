package test.com.seventytwomiles.dao.hibernate;


import test.com.seventytwomiles.dao.Dao;
import test.com.seventytwomiles.model.Person;


/**
 * <p>todo: javadocs</p>
 *
 * @author mikenereson
 */
public class AbstractHibernateDao implements Dao {


    public AbstractHibernateDao() {

    }


    Person loadPerson(final int id) {
        Person person = new Person(id);
        return person;
    }
}
