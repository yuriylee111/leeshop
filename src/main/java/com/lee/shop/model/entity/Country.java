package com.lee.shop.model.entity;

public class Country extends BaseEntity<Short> {

    private String name;

    public Country() {
    }

    public Country(Short id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
