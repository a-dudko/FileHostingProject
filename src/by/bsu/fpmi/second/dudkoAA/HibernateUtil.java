package by.bsu.fpmi.second.dudkoAA;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * An util class working with
 * Hibernate sessions and transactions.
 */
public final class HibernateUtil  {

    /** Session factory for creating sessions. */
    private static SessionFactory sessionFactory;

    /** Service registry for the current hibernate properties. */
    private static ServiceRegistry serviceRegistry;

    /** Getting hibernate configuration and creating session factory. */
    static {
        try {
            Configuration configuration = new Configuration().configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException he) {
            System.err.println("Error creating Session: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }

    /**
     * Gets session factory.
     * @return sessionFactory.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Begins new transaction.
     * @return currentsession with the transaction began.
     */
    public static Session beginTransaction() {
        Session hibernateSession = HibernateUtil.getSession();
        hibernateSession.beginTransaction();
        return hibernateSession;
    }

    /**
     * Commits current transaction.
     */
    public static void commitTransaction() {
        HibernateUtil.getSession().getTransaction().commit();
    }

    /**
     * Rollbacks current transaction.
     */
    public static void rollbackTransaction() {
        HibernateUtil.getSession().getTransaction().rollback();
    }

    /**
     * Closes current session.
     */
    public static void closeSession() {
        HibernateUtil.getSession().close();
    }

    /**
     * Gets current session working with DB.
     * @return current session
     */
    public static Session getSession() {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return hibernateSession;
    }
}
