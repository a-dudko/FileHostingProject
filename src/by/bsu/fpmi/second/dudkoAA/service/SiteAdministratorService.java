package by.bsu.fpmi.second.dudkoAA.service;

import by.bsu.fpmi.second.dudkoAA.dao.SiteAdministratorDAO;
import by.bsu.fpmi.second.dudkoAA.model.SiteAdministrator;

import java.util.List;

/**
 * A class for interacting with site administrators storage
 * without dependency on what it exactly is.
 */
public class SiteAdministratorService {

    /** Administrators storage class instance. */
    private SiteAdministratorDAO siteAdministratorDAO = new SiteAdministratorDAO();

    /**
     * Adds the admin to the admins storage.
     * @param siteAdministrator to add to the storage
     */
    public void addAdministrator(final SiteAdministrator siteAdministrator) {
        siteAdministratorDAO.add(siteAdministrator);
    }

    /**
     * Gets the list of all admins in the storage.
     * @return list of all admins in the storage
     */
    public List<SiteAdministrator> getProfiles() {
        return siteAdministratorDAO.readAll();
    }

    /**
     * Gets the counts of admins in storage.
     * @return counts of admins in storage
     */
    public int getCounts() {
        List<SiteAdministrator> profiles = getProfiles();
        if (profiles != null) {
            return profiles.size();
        }
        return 0;
    }

    /**
     * Gets admin from the admins storage by his ID.
     * @param id of admin to get
     * @return admin with such ID or null
     * if there is no such admin
     */
    public SiteAdministrator getAdministrator(final Integer id) {
        return siteAdministratorDAO.read(id);
    }

    /**
     * Updates admin in admins storage.
     * @param administrator to update
     */
    public void updateAdministrator(final SiteAdministrator administrator) {
        siteAdministratorDAO.update(administrator);
    }

    /**
     * Removes administrator from admins storage.
     * @param administrator to remove
     */
    public void removeAdministrator(final SiteAdministrator administrator) {
        siteAdministratorDAO.remove(administrator);
    }
}
