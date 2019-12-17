package org.softuni.workman.domain.models.service;

import org.softuni.workman.domain.entities.Workman;

import java.util.List;

public class CommentServiceModel extends BaseServiceModel {
    private String title;
    private String author;
    private String description;
    private String imageUrl;
    private UserServiceModel user;
    private WorkmanServiceModel workman;


    public CommentServiceModel() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

//    public List<WorkmanServiceModel> getWorkman() {
//        return workman;
//    }
//
//    public void setWorkman(List<WorkmanServiceModel> workman) {
//        this.workman = workman;
//    }


    public WorkmanServiceModel getWorkman() {
        return workman;
    }

    public void setWorkman(WorkmanServiceModel workman) {
        this.workman = workman;
    }
}
