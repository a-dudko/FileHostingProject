package by.bsu.fpmi.second.dudkoAA.dao;

import by.bsu.fpmi.second.dudkoAA.HibernateUtil;
import by.bsu.fpmi.second.dudkoAA.model.File;
import org.hibernate.Session;

import java.util.List;

public class FileDAO {
    public void add(File file) {
        // TODO Auto-generated method stub
        try {
            Session session = this.getSession();
            HibernateUtil.beginTransaction();
            session.saveOrUpdate(file);
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        }
    }

    public List<File> readAll() {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        return session.createQuery("from " + File.class).list();
    }

    public File read(Integer id) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        return (File)session.load(File.class, id);
    }

    public void update(File item) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        session.update(item);
        HibernateUtil.commitTransaction();
    }

    public void remove(File item) {
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
