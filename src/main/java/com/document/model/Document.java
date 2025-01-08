package com.document.model;

public class Document {
    private int id;
    private String title;
    private String author;
    private String size;
    private String filename;
    private String dateCreated;
    private String dateModified;

    public Document(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Document(String title, String author, String size, String filename, String dateCreated, String dateModified) {
        this.title = title;
        this.author = author;
        this.size = size;
        this.filename = filename;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
}
