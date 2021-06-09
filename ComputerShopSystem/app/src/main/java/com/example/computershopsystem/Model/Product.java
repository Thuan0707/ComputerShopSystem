package com.example.computershopsystem.Model;

import java.util.Date;

public class Product extends Device {
    int id;
    String name;
String  image;
    String description;
    long buyPrice;
    long sellPrice;
    Date createAt;
    Date deleteAt;

    public Product() {
    }

    public Product(CPU cpu, Ram ram, Rom rom, Brand brand, Screen screen, int id, String name, String image, String description, long buyPrice, long sellPrice, Date createAt, Date deleteAt) {
        super(cpu, ram, rom, brand, screen);
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.createAt = createAt;
        this.deleteAt = deleteAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return image;
    }

    public void setUrlImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }
}
