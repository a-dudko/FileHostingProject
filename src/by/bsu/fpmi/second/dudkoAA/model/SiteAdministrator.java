package by.bsu.fpmi.second.dudkoAA.model;

import javax.persistence.*;

@Entity
@Table(name = "SITE_ADMINISTRATOR")
public class SiteAdministrator {
    @Id
    @GeneratedValue
    @Column(name = "ADMIN_ID")
    private Integer id;

    @Column(name = "ADMIN_LOGIN")
    private String login;

    @Column(name = "ADMIN_PASSWORD")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
