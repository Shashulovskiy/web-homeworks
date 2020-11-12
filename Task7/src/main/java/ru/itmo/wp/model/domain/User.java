package ru.itmo.wp.model.domain;

import ru.itmo.wp.model.annotations.CreationTime;
import ru.itmo.wp.model.annotations.Id;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    @Id
    private Long id;
    private String login;
    private String email;
    private Boolean admin;
    @CreationTime
    private Date creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
