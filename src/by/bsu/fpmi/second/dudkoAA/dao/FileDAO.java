package by.bsu.fpmi.second.dudkoAA.dao;

import by.bsu.fpmi.second.dudkoAA.HibernateUtil;
import by.bsu.fpmi.second.dudkoAA.model.File;
import org.hibernate.Session;

import java.util.List;

/**
 * A class for interaction with Files database.
 */
public class FileDAO extends DataBaseDAO <File> {

    /**
     * Gets all files from DB.
     * @return List of all s from DB
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
}
