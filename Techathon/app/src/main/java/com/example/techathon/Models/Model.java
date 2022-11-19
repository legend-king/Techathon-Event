package com.example.techathon.Models;

public class Model {
    String dish;
    int id;

    public Model(String dish) {
        this.dish = dish;
    }

    public Model(String dish, int id) {
        this.dish = dish;
        this.id = id;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
