package com.example.restaurantnotification;

/**
 * Created by david on 03/03/2016.
 */
public class Item {

    private int idPhoto;
    private String name;
    private String price;

    public Item (int idPhoto,String name,String price){
        super();
        this.idPhoto=idPhoto;
        this.name=name;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String pathPhoto) {
        this.idPhoto = idPhoto;
    }
}
