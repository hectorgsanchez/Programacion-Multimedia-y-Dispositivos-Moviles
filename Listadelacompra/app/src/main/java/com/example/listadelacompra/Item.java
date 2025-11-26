package com.example.listadelacompra;

public class Item {

    private String name;
    private int quantity;
    private int imageId;

    public Item(String name, int quantity, int imageId) {
        this.name = name;
        this.quantity = quantity;
        this.imageId = imageId;
    }

    public String getName() { return name; }

    public int getQuantity() { return quantity; }

    public int getImageId() { return imageId; }

    public void setName(String name) { this.name = name; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
