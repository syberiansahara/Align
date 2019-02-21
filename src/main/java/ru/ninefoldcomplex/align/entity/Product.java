package ru.ninefoldcomplex.align.entity;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @Column(name = "PRODUCT_ID")
    private long productId;
    public long getProductId() {
        return productId;
    }

    @Column(name = "PRODUCT_NAME")
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @ManyToOne
    @JoinColumn(name = "BRAND_ID", nullable = false)
    private Brand brand;
    public Brand getBrand() {
        return brand;
    }

    public Product(long productId, String productName, Brand brand) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
    }

    @OneToOne
    @JoinColumn(name = "QUANTITY_ID")
    private Quantity quantity;
    public Quantity getQuantity() {
        return quantity;
    }
    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    @OneToOne
    @JoinColumn(name = "PRICE_ID")
    private Price price;
    public Price getPrice() {
        return price;
    }
    public void setPrice(Price price) {
        this.price = price;
    }

    private Product() {
    }
}