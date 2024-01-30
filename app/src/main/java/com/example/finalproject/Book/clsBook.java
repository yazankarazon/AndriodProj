package com.example.finalproject.Book;

public class clsBook {
    private String title, category, pages, image, imageCover;
    private int copies;

    public clsBook(String title, String category, String pages, String image, int copies, String imageCover) {
        this.title = title;
        this.category = category;
        this.pages = pages;
        this.image = image;
        this.copies = copies;
        this.imageCover = imageCover;
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
