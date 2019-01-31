package ru.ninefoldcomplex.align.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRICE_ID")
    private long priceId;
    public long getPriceId() {
        return priceId;
    }

    @Column(name = "PRODUCT_ID")
    private long productId;
    public long getProductId() {
        return productId;
    }

    @Column(name = "PRICE")
    private Integer price;
    public Integer getPrice() {
        return price;
    }

    @Column(name = "PRICE_TIMESTAMP")
    private final LocalDateTime priceTimestamp;
    public LocalDateTime getPriceTimestamp() {
        return priceTimestamp;
    }

    public Price(long productId, Integer price) {
        this();
        this.productId = productId;
        this.price = price;
    }

    public Price() {
        this.priceTimestamp = LocalDateTime.now();
    }
}
