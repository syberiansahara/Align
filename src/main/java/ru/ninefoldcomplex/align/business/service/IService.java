package ru.ninefoldcomplex.align.business.service;

import ru.ninefoldcomplex.align.business.entity.Product;

import java.util.List;

public interface IService {
    List<Product> getLeftovers();
    Long getProductId(String productName, String brandName);
    Product addProduct(String productName, String brandName, Integer quantity, Integer price);
    Product updateProduct(Long productId, Integer quantity, Integer price);
    void removeProduct(Long productId);
}
