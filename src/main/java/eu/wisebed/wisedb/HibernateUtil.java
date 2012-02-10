package eu.wisebed.wisedb;

import eu.wisebed.wisedb.controller.*;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility functions for accessing hibernate.
 */
public final class HibernateUtil {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static HibernateUtil ourInstance = null;

    /**
     * A static SessionFactory variable.
     */
    private final transient SessionFactory ourSessionFactory;

    /**
     * The Configuration of hibernate.
     */
    private final transient Configuration configuration;

    /**
     * Private constructor suppresses generation of a (public) default constructor.
     */
    private HibernateUtil() {
        try {
            // load configuration file
            configuration = new Configuration().configure();

            // Create the SessionFactory from hibernateMM.cfg.xml
            ourSessionFactory = createSessionFactory();

        } catch (Exception ex) {
            // Make sure you log the exception, as it might be swallowed
            LOGGER.error("Initial SessionFactory creation failed. " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * HibernateUtil is loaded on the first execution of HibernateUtili.getInstance().
     * or the first access to HibernateUtili.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static HibernateUtil getInstance() {
        synchronized (HibernateUtil.class) {
            if (ourInstance == null) {
                ourInstance = new HibernateUtil();
            }
        }

        return ourInstance;
    }

    /**
     * Returns the current Session.
     *
     * @return the current Session
     */
    public Session getSession() {
        Session ses = ourSessionFactory.getCurrentSession();

        if (!ses.isOpen()) {
            ses = ourSessionFactory.openSession();
        }

        return ses;
    }

    /**
     * Returns current Hibernate configuration.
     *
     * @return current configuration.
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Returns the current Session factory.
     *
     * @return the current Session Factory.
     */
    public SessionFactory getSessionFactory() {
        return ourSessionFactory;
    }

    /**
     * Try to create a SessionFactory.
     *
     * @return the SessionFactory
     */
    public SessionFactory createSessionFactory() {
        SessionFactory sFactory;

        try {
            // Create the SessionFactory from hibernateMM.cfg.xml
            sFactory = configuration.buildSessionFactory();

        } catch (Exception ex) {
            // Make sure you log the exception, as it might be swallowed
            LOGGER.debug("Initial SessionFactory creation failed. " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }

        return sFactory;
    }

    /**
     * Connect entity managers to Hibernate SessionFactory.
     */
    public static void connectEntityManagers() {
        // Set the session factories to all managers
        LOGGER.info("Connecting Entity Controllers to Hibernate SessionFactory");
        final SessionFactory thisFactory = HibernateUtil.getInstance().getSessionFactory();

        // main controllers
        TestbedController.getInstance().setSessionFactory(thisFactory);

        SetupController.getInstance().setSessionFactory(thisFactory);

        NodeController.getInstance().setSessionFactory(thisFactory);
        LinkController.getInstance().setSessionFactory(thisFactory);

        CapabilityController.getInstance().setSessionFactory(thisFactory);

        NodeCapabilityController.getInstance().setSessionFactory(thisFactory);
        LinkCapabilityController.getInstance().setSessionFactory(thisFactory);


        NodeReadingController.getInstance().setSessionFactory(thisFactory);
        LinkReadingController.getInstance().setSessionFactory(thisFactory);

        LastNodeReadingController.getInstance().setSessionFactory(thisFactory);
        LastLinkReadingController.getInstance().setSessionFactory(thisFactory);
    }

    /**
     * Closes the current Session.
     */
    public void closeSession() {
        final Session ses = ourSessionFactory.getCurrentSession();

        if (ses.isOpen()) {
            ses.close();
        }
    }
}
