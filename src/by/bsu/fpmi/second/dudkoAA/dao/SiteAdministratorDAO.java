package by.bsu.fpmi.second.dudkoAA.dao;

import by.bsu.fpmi.second.dudkoAA.HibernateUtil;
import by.bsu.fpmi.second.dudkoAA.model.SiteAdministrator;
import org.hibernate.Session;

import java.util.List;

/**
 * A class for interaction with
 * Site Administrators database.
 */
public class SiteAdministratorDAO {

    /** Instance of class object. */
    private static SiteAdministratorDAO instance;

    /**
     * Get the instance of class and create it
     * if it hasn't been created yet.
     * @return the instance of class
     */
    public static SiteAdministratorDAO getInstance() {
        if (instance == null) {
            instance = new SiteAdministratorDAO();
        }
        return instance;
    }

    /**
     * Adds the administrator to DB or updates it
     * if it's already there.
     * @param administrator to add or update
     */
    public void add(final SiteAdministrator administrator) {
        try {
            Session session = this.getSession();
            HibernateUtil.beginTransaction();
            session.saveOrUpdate(administrator);
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Gets all administrators from DB.
     * @return List of all SiteAdministrators from DB
     */
    public List<SiteAdministrator> readAll() {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        List<SiteAdministrator> administrators = session.createQuery("from SiteAdministrator").list();
        HibernateUtil.commitTransaction();
        return administrators;
    }

    /**
     * Gets the admin from DB by id.
     * @param id of the administrator to get
     * @return administrator or null if no
     * admins with such ID found
     */
    public SiteAdministrator read(final Integer id) {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        SiteAdministrator administrator = (SiteAdministrator) session.get(SiteAdministrator.class, id);
        HibernateUtil.commitTransaction();
        return administrator;
    }

    /**
     * Updates admin in DB.
     * @param administrator to update
     */
    public void update(final SiteAdministrator administrator) {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        session.update(administrator);
        HibernateUtil.commitTransaction();
    }

    /**
     * Removes admin from DB if it's there.
     * @param administrator to remove
     */
    public void remove(final SiteAdministrator administrator) {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        session.delete(administrator);
        HibernateUtil.commitTransaction();
    }

    /**
     * Gets current session working with DB.
     * @return current session
     */
    protected Session getSession() {
        return HibernateUtil.getSession();
    }
}
