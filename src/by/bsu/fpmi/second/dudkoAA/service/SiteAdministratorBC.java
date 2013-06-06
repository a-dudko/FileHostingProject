package by.bsu.fpmi.second.dudkoAA.service;

import by.bsu.fpmi.second.dudkoAA.HibernateUtil;
import by.bsu.fpmi.second.dudkoAA.dao.FileDAO;
import by.bsu.fpmi.second.dudkoAA.dao.SiteAdministratorDAO;
import by.bsu.fpmi.second.dudkoAA.model.File;
import by.bsu.fpmi.second.dudkoAA.model.SiteAdministrator;
import org.hibernate.Session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 55
 * Date: 6.6.13
 * Time: 21.09
 */
public class SiteAdministratorBC {

    private SiteAdministratorDAO siteAdministratorDAO = SiteAdministratorDAO.getInstance();

    private static SiteAdministratorBC instance;

    public static SiteAdministratorBC getInstance() {
        if (instance == null) {
            instance = new SiteAdministratorBC();
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
