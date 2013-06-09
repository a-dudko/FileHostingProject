package by.bsu.fpmi.second.dudkoAA.service;

import by.bsu.fpmi.second.dudkoAA.dao.SiteAdministratorDAO;
import by.bsu.fpmi.second.dudkoAA.model.SiteAdministrator;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 55
 * Date: 6.6.13
 * Time: 21.09
 */
public class SiteAdministratorService {

    private SiteAdministratorDAO siteAdministratorDAO = SiteAdministratorDAO.getInstance();

    private static SiteAdministratorService instance;

    public static SiteAdministratorService getInstance() {
        if (instance == null) {
            instance = new SiteAdministratorService();
        }
        return instance;
    }

    public void addAdministrator(SiteAdministrator siteAdministrator) {
        siteAdministratorDAO.add(siteAdministrator);
    }

    public List<SiteAdministrator> getProfiles() {
        return siteAdministratorDAO.readAll();
    }

    public int getCounts() {
        List<SiteAdministrator> profiles = getProfiles();
        if (profiles != null) {
            return profiles.size();
        }
        return 0;
    }

    public SiteAdministrator getAdministrator(Integer id) {
        return siteAdministratorDAO.read(id);
    }

    public void updateAdministrator(SiteAdministrator administrator) {
        siteAdministratorDAO.update(administrator);
    }

    public void removeAdministrator(SiteAdministrator administrator) {
        siteAdministratorDAO.remove(administrator);
    }

}
