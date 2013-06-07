package by.bsu.fpmi.second.dudkoAA.dao;

import by.bsu.fpmi.second.dudkoAA.HibernateUtil;
import by.bsu.fpmi.second.dudkoAA.model.SiteAdministrator;
import org.hibernate.Session;

import org.hibernate.Query;
import java.util.List;

public class SiteAdministratorDAO {

    private static SiteAdministratorDAO instance;

    public static SiteAdministratorDAO getInstance() {
        if (instance == null) {
            instance = new SiteAdministratorDAO();
        }
        return instance;
    }

    public void add(SiteAdministrator administrator) {
        // TODO Auto-generated method stub
        try {
            Session session = this.getSession();
            HibernateUtil.beginTransaction();
            session.saveOrUpdate(administrator);
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        }
    }

    public List<SiteAdministrator> readAll() {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        List<SiteAdministrator> administrators = session.createQuery("from SiteAdministrator").list();
        HibernateUtil.commitTransaction();
        return administrators;
    }

    public SiteAdministrator read(Integer id) {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        SiteAdministrator administrator = (SiteAdministrator)session.get(SiteAdministrator.class, id);
        HibernateUtil.commitTransaction();
        return administrator;
    }

    public void update(SiteAdministrator item) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        session.update(item);
        HibernateUtil.commitTransaction();
    }

    public void remove(SiteAdministrator item) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        session.delete(item);
        HibernateUtil.commitTransaction();
    }

    protected Session getSession() {
        return HibernateUtil.getSession();
    }
}
