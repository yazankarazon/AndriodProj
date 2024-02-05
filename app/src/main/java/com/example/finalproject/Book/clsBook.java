package com.example.finalproject.Book;

import java.io.Serializable;

public class clsBook implements Serializable {

    private int id;
    private String title, category, pages, image, imageCover;
    private int copies;

    public clsBook(int id, String title, String image, int copies) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.copies = copies;
    }

    public clsBook(int id, String title, String category, String pages, String image, int copies, String imageCover) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.pages = pages;
        this.image = image;
        this.copies = copies;
        this.imageCover = imageCover;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }
}
