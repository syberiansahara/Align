package ru.ninefoldcomplex.align.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long priceId;

    private long productId;

    private Integer price;

    private final LocalDateTime timestamp;

    public Price(long productId, Integer price) {
        this();
        this.productId = productId;
        this.price = price;
    }

    public Price() {
        this.timestamp = LocalDateTime.now();
    }
}
