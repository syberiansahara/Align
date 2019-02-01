package ru.ninefoldcomplex.align.entity;

import javax.persistence.*;

@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BRAND_ID")
    private long brandId;
    public long getBrandId() {
        return brandId;
    }

    @Column(name = "BRAND_NAME")
    private String brandName;
    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    @OneToOne
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "BRAND_ID", nullable = false, insertable = false, updatable = false)
    private Product product;
    public Product getProduct() {
        return product;
    }

    private Brand() {
    }
}