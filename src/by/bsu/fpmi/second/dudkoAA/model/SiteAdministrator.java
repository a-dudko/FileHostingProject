package by.bsu.fpmi.second.dudkoAA.model;

import javax.persistence.*;

/**
 * A class for site administrators objects,
 * registered on the site.
 */
@Entity
@Table(name = "SITE_ADMINISTRATOR")
public class SiteAdministrator {

    /** Unique id for administrator in DB. */
    @Id
    @GeneratedValue
    @Column(name = "ADMIN_ID")
    private Integer id;

    /** Login of administrator. */
    @Column(name = "ADMIN_LOGIN")
    private String login;

    /** Encrypted password of administrator. */
    @Column(name = "ADMIN_PASSWORD")
    private String password;

    /**
     * Gets encrypted password of current admin.
     * @return password of current admin
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets encrypted password for current admin.
     * @param password to be set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets id of current admin.
     * @return id of current admin
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id for current admin.
     * @param id to be set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Gets login of current admin.
     * @return login of current admin
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login for current admin.
     * @param login to be set
     */
    public void setLogin(final String login) {
        this.login = login;
    }
}
