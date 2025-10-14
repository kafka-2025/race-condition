package com.example.demo.sample.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_sample")
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Sample(){}

    public Sample(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
