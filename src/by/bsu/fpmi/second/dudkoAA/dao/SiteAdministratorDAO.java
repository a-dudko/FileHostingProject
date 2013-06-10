package by.bsu.fpmi.second.dudkoAA.dao;

import by.bsu.fpmi.second.dudkoAA.HibernateUtil;
import by.bsu.fpmi.second.dudkoAA.model.SiteAdministrator;
import org.hibernate.Session;

import java.util.List;

/**
 * A class for interaction with
 * Site Administrators database.
 */
public class SiteAdministratorDAO extends DataBaseDAO<SiteAdministrator> {

    /**
     * Gets all administrators from DB.
     * @return List of all SiteAdministrators from DB
     */
    public final List<SiteAdministrator> readAll() {
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
    public final SiteAdministrator read(final Integer id) {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        SiteAdministrator administrator = (SiteAdministrator) session.get(SiteAdministrator.class, id);
        HibernateUtil.commitTransaction();
        return administrator;
    }

}
