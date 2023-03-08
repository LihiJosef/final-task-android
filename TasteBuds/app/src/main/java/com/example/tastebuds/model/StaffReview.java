package com.example.tastebuds.model;


public class StaffReview {
    private String id;
    private String img;
    private String name;
    private String dsc;
    private double price;
    private int rate;
    private String country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "StaffReview{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", dsc='" + dsc + '\'' +
                ", rate=" + rate +
                ", country='" + country + '\'' +
                ", price=" + price +
                '}';
    }
}
