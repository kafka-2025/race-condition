package com.example.demo.sample.entity;

public class Sample {

    private Integer id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sample(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
