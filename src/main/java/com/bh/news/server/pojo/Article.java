package com.bh.news.server.pojo;

import java.util.Date;
import java.util.UUID;

public class Article {
    private String id;
    private String title;
    private String preview;
    private String postImage;
    private Date date;

    public Article() {
    }

    public Article(String title) {
        this();
        this.title = title;
    }

    public void generateAttribute() {
        if (this.id == null) {
            UUID uuid = UUID.randomUUID();
            this.id = uuid.toString();
        }
        if (this.date == null) {
            this.date = new Date();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
