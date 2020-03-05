package com.example.dundone.data.item;

public class EpicData {
    private String name;
    private String itemId;

    public EpicData(String name, String itemId) {
        this.name = name;
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public String getItemId() {
        return itemId;
    }
}
