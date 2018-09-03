package com.example.nour.backgrounds.Model;

public class CategoryItem {
private String Name;
private String ImageLinke;

    public CategoryItem() {
    }

    public CategoryItem(String name, String imageLinke) {
       this.Name = name;
        this.ImageLinke = imageLinke;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageLinke() {
        return ImageLinke;
    }

    public void setImageLinke(String imageLinke) {
        this.ImageLinke = imageLinke;
    }
}
