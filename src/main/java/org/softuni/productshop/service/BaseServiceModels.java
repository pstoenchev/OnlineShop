package org.softuni.productshop.service;

public abstract class BaseServiceModels {

    private String id;

    public BaseServiceModels() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
