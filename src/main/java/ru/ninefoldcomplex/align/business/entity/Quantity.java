package ru.ninefoldcomplex.align.business.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QUANTITY_ID")
    private long quantityId;
    public long getQuantityId() {
        return quantityId;
    }

    @Column(name = "PRODUCT_ID")
    private long productId;
    public long getProductId() {
        return productId;
    }

    @Column(name = "QUANTITY")
    private Integer quantity;
    public Integer getQuantity() {
        return quantity;
    }

    @Column(name = "QUANTITY_TIMESTAMP")
    private final LocalDateTime quantityTimestamp;
    public LocalDateTime getQuantityTimestamp() {
        return quantityTimestamp;
    }

    public Quantity(long productId, Integer quantity, LocalDateTime quantityTimestamp) {
        this.productId = productId;
        this.quantity = quantity;
        this.quantityTimestamp = quantityTimestamp;
    }

    public Quantity(Product product, Integer quantity) {
        this();
        this.productId = product.getProductId();
        this.quantity = quantity;
    }

    Quantity() {
        this.quantityTimestamp = LocalDateTime.now();
    }
}
