package com.example.demo.sample.entity;

public class Sample {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sample(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
