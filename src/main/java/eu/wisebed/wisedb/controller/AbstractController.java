package eu.wisebed.wisedb.controller;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * This class implements an abstract manager which is responsible for all the
 * basic CRUD(insert,select,update,delete) functions in the database. Other
 * sub-classes inherit from this one. This means that every sub-class can use
 * all the methods that are implemented here and consequently there is no need
 * of defining the CRUD functions in every class. Templates are used here as a
 * generic to all the class types that are defined inside the logic package.
 *
 * @param <E> Generic type of AbstractController
 */
public abstract class AbstractController<E> {

    /**
     * the session factory which opens a new session.
     */
    private transient SessionFactory sessionFactory;

    /**
     * a SessionFactory bean is injected by Spring.
     *
     * @param factory sessionFactory bean.
     */
    public void setSessionFactory(final SessionFactory factory) {
        this.sessionFactory = factory;
    }

    /**
     * Returns the current Session factory.
     * @return the current Session Factory.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * adding a new entry into the database, according to the input object it
     * receives.
     *
     * @param entity an Entity object that may be of every type of entity.
     */
    public void add(final E entity) {
        final Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
    }

    /**
     * updating an entry into the database, according to the input object it
     * receives.
     *
     * @param entity an Entity object that may be of every type of entity.
     */
    public void update(final E entity) {
        final Session session = sessionFactory.getCurrentSession();
        session.merge(entity);
    }

    /**
     * deleting an entry into the database, according to the input object it
     * receives.
     *
     * @param entity   an Entity object that may be of every type of entity.
     * @param entityID the (int) id of the Entity object.
     */
    public void delete(final E entity, final int entityID) {
        final Session session = sessionFactory.getCurrentSession();
        final Object entity2 = session.load(entity.getClass(), entityID);
        session.delete(entity2);
    }

    /**
     * deleting an entry into the database, according to the input object it
     * receives.
     *
     * @param entity   an Entity object that may be of every type of entity.
     * @param entityID the (String) id of the Entity object.
     */
    public void delete (final E entity,final String entityID) {
        final Session session = sessionFactory.getCurrentSession();
        final Object entity2 = session.load(entity.getClass(), entityID);
        session.delete(entity2);
    }


    /**
     * listing all the entries from the database related to the input object it
     * receives.
     *
     * @param entity an Entity object that may be of every type of entity.
     * @return a list of all the records(objects) that exist inside the table
     *         related to the input Entity object.
     */
    @SuppressWarnings("unchecked")
    protected List<E> list(final E entity) {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria criteria = session.createCriteria(entity.getClass());
        return (List<E>) criteria.list();
    }

    /**
     * Get the entry from the database that corresponds to the input id.
     *
     * @param entity   an Entity object that may be of every type of entity.
     * @param entityID the (int) id of the Entity object.
     * @return an Entity object.
     */
    @SuppressWarnings("unchecked")
    protected E getByID(final E entity, final int entityID) {
        final Session session = sessionFactory.getCurrentSession();
        Object entityByID = session.get(entity.getClass(), entityID);
        return (E) entityByID;
    }

    /**
     * Get the entry from the database that corresponds to the input id.
     *
     * @param entity   an Entity object that may be of every type of entity.
     * @param entityID the (String) id of the Entity object.
     * @return an Entity object.
     */
    @SuppressWarnings("unchecked")
    protected E getByID(final E entity, final String entityID) {
        final Session session = sessionFactory.getCurrentSession();
        Object entityByID = session.get(entity.getClass(), entityID);
        return (E) entityByID;

    }
}
