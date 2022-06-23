package com.lee.shop.model.entity;

public class Category extends BaseEntity<Short> {

    private String name;

    public Category() {
    }

    public Category(Short id, String name) {
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
        return "Category{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
