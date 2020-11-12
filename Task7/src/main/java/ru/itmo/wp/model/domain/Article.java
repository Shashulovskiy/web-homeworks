package ru.itmo.wp.model.domain;

import ru.itmo.wp.model.annotations.CreationTime;
import ru.itmo.wp.model.annotations.Id;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    @Id
    private Long id;
    private Long userId;
    private String title;
    private String text;
    private Boolean hidden;
    @CreationTime
    private Date creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
