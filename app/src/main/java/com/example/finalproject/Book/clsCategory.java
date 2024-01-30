package com.example.finalproject.Book;

public class clsCategory {

    String name, imageCover;

    public clsCategory(String name, String imageCover) {
        this.name = name;
        this.imageCover = imageCover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }
}
