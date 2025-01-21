package com.document.model;

public class Document {
    private int id;
    private String title;
    private String author;
    private String size;
    private String fileName;
    private String dateCreated;
    private String dateModified;
    private String type;

    public Document(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Document(String title, String author, String size, String fileName, String dateCreated, String dateModified, String type) {
        this.title = title;
        this.author = author;
        this.size = size;
        this.fileName = fileName;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.type = type;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
