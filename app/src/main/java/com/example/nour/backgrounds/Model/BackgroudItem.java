package com.example.nour.backgrounds.Model;

public class BackgroudItem
{
  private String ImageUrl;
  private  String CategoryId;
  private long viewCount;

    public BackgroudItem() {

    }

    public BackgroudItem(String imageUrl, String categoryId) {
        ImageUrl = imageUrl;
        CategoryId = categoryId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }
}
