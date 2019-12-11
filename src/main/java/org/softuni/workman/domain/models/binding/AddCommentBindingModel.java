package org.softuni.workman.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

public class AddCommentBindingModel {
    private String title;
    private String author;
    private String description;
    private MultipartFile imgUrl;
    private String workman;

    public AddCommentBindingModel() {
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

    public MultipartFile getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(MultipartFile imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getWorkman() {
        return workman;
    }

    public void setWorkman(String workman) {
        this.workman = workman;
    }


}
