package ru.itmo.wp.model.domain;

import ru.itmo.wp.model.annotations.CreationTime;
import ru.itmo.wp.model.annotations.Id;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    @Id
    private Long id;
    private Long userId;
    private EventType type;
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

    public String getType() {
        return type.name();
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public enum EventType {
        ENTER, LOGOUT
    }
}
