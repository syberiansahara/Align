package ru.ninefoldcomplex.align.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long brandId;

    private String name;

    public Brand(long brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }

    public Brand() {
    }
}