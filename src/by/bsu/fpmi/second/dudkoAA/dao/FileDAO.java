package by.bsu.fpmi.second.dudkoAA.dao;

import by.bsu.fpmi.second.dudkoAA.HibernateUtil;
import by.bsu.fpmi.second.dudkoAA.model.File;
import org.hibernate.Session;

import java.util.List;

/**
 * A class for interaction with Files database.
 */
public class FileDAO {

    /** Instance of class object. */
    private static FileDAO instance;

    /**
     * Get the instance of class and create it
     * if it hasn't been created yet.
     * @return the instance of class
     */
    public static FileDAO getInstance() {
        if (instance == null) {
            instance = new FileDAO();
        }
        return instance;
    }

    /**
     * Adds the file to DB or updates it
     * if it's already there.
     * @param file to add or update
     */
    public final void add(final File file) {
        try {
            Session session = this.getSession();
            HibernateUtil.beginTransaction();
            session.saveOrUpdate(file);
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Gets all files from DB.
     * @return List of all Files from DB
     */
    public final List<File> readAll() {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        List<File> files = session.createQuery("from File").list();
        HibernateUtil.commitTransaction();
        return files;
    }

    /**
     * Gets the file from DB by id.
     * @param id of the file to get
     * @return file or null if no files with such ID found
     */
    public final File read(final Integer id) {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        File file = (File) session.get(File.class, id);
        HibernateUtil.commitTransaction();
        return file;
    }

    /**
     * Updates file in DB.
     * @param file to update
     */
    public final void update(final File file) {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        session.update(file);
        HibernateUtil.commitTransaction();
    }

    /**
     * Removes file from DB if it's there.
     * @param file to remove
     */
    public final void remove(final File file) {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        session.delete(file);
        HibernateUtil.commitTransaction();
    }

    /**
     * Gets current session working with DB.
     * @return current session
     */
    protected final Session getSession() {
        return HibernateUtil.getSession();
    }

}
