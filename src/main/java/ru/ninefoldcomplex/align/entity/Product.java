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

    private String productName;
    @Column(name = "PRODUCT_NAME")
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Product(long productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    @Column(name = "BRAND_ID")
    private long brandId;
    public long getBrandId() {
        return brandId;
    }
    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    @Column(name = "QUANTITY_ID")
    private long quantityId;
    public long getQuantityId() {
        return quantityId;
    }
    public void setQuantityId(long quantityId) {
        this.quantityId = quantityId;
    }

    @Column(name = "PRICE_ID")
    private long priceId;
    public long getPriceId() {
        return priceId;
    }
    public void setPriceId(long priceId) {
        this.priceId = priceId;
    }

    private Brand brand;
    @OneToOne
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "BRAND_ID", nullable = false, insertable = false, updatable = false)
    public Brand getBrand() {
        return brand;
    }
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    private Quantity quantity;
    @OneToOne
    @JoinColumn(name = "QUANTITY_ID", referencedColumnName = "QUANTITY_ID", insertable = false, updatable = false)
    public Quantity getQuantity() {
        return quantity;
    }

    @OneToOne
    @JoinColumn(name = "PRICE_ID", referencedColumnName = "PRICE_ID", insertable = false, updatable = false)
    private Price price;
    public Price getPrice() {
        return price;
    }

    private Product() {
    }
}