package ru.itmo.wp.model.domain;

import ru.itmo.wp.model.annotations.CreationTime;
import ru.itmo.wp.model.annotations.Id;

import java.io.Serializable;
import java.util.Date;

public class Talk implements Serializable {
    @Id
    private Long id;
    private Long sourceUserId;
    private Long targetUserId;
    private String message;
    @CreationTime
    private Date creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
