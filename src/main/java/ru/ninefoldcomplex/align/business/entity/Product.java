package ru.ninefoldcomplex.align.business.entity;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"PRODUCT_NAME", "BRAND_ID"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Product(String productName, Brand brand) {
        this.productName = productName;
        this.brand = brand;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "QUANTITY_ID")
    private Quantity quantity;
    public Quantity getQuantity() {
        return quantity;
    }
    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRICE_ID")
    private Price price;
    public Price getPrice() {
        return price;
    }
    public void setPrice(Price price) {
        this.price = price;
    }

    Product() {
    }
}