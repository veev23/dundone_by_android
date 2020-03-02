package com.example.dundone.main.entities;

public class TabItem {
    private int unClickedImage;
    private int clickedImage;
    private String text;

    public TabItem(int unClickedImage, int clickedImage, String text) {
        this.unClickedImage = unClickedImage;
        this.clickedImage = clickedImage;
        this.text = text;
    }
    public int getUnClickedImage() {
        return unClickedImage;
    }

    public int getClickedImage() {
        return clickedImage;
    }

    public String getText() {
        return text;
    }
}