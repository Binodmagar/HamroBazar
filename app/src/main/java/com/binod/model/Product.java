package com.binod.model;

public class Product {

    String name;
    String image;
    Integer price;
    String type;
    Boolean popular;

    public Product(String name, String image, Integer price, String type, Boolean popular) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.type = type;
        this.popular = popular;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getPopular() {
        return popular;
    }

    public void setPopular(Boolean popular) {
        this.popular = popular;
    }
}
