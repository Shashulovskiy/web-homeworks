package ru.itmo.web.lesson4.model;

import java.util.Date;

public class Post {
    private final long id;
    private final String title;
    private final String text;
    private final long user_id;
    private final String user_name;
    private final String date;

    public Post(long id, String title, String text, long user_id, String user_name, String date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.user_id = user_id;
        this.user_name = user_name;
        this.date = date;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getUser_id() {
        return user_id;
    }

}
