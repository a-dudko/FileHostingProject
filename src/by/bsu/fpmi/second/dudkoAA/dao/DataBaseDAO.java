package by.bsu.fpmi.second.dudkoAA.dao;

import by.bsu.fpmi.second.dudkoAA.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public abstract class DataBaseDAO <EntityType> {
    /**
     * Adds the item to DB or updates it
     * if it's already there.
     * @param item to add or update
     */
    public final void add(final EntityType item) {
        try {
            Session session = this.getSession();
            HibernateUtil.beginTransaction();
            session.saveOrUpdate(item);
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Gets all items from DB.
     * @return List of all items from DB
     */
    public abstract List readAll();

    /**
     * Gets the item from DB by id.
     * @param id id of item to be found
     * @return item or null if no item with such ID found
     */
    public abstract EntityType read(final Integer id);

    /**
     * Updates item in DB.
     * @param item to update
     */
    public final void update(final EntityType item) {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        session.update(item);
        HibernateUtil.commitTransaction();
    }

    /**
     * Removes item from DB if it's there.
     * @param item to remove
     */
    public final void remove(final EntityType item) {
        Session session = this.getSession();
        HibernateUtil.beginTransaction();
        session.delete(item);
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
