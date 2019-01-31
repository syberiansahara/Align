package ru.ninefoldcomplex.align.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long quantityId;

    private long productId;

    private Integer quantity;

    private final LocalDateTime timestamp;

    public Quantity(long productId, Integer quantity) {
        this();
        this.productId = productId;
        this.quantity = quantity;
    }

    public Quantity() {
        this.timestamp = LocalDateTime.now();
    }
}
