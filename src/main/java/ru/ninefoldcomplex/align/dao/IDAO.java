package ru.ninefoldcomplex.align.dao;

import ru.ninefoldcomplex.align.entity.Product;

import java.util.List;

public interface IDAO {
    List<Product> getLeftovers();
    void addProduct(String productName, String brandName);
    void addProduct(String productName, String brandName, Integer quantity, Integer price);
    void addProductWithQuantity(String productName, String brandName, Integer quantity);
    void addProductWithPrice(String productName, String brandName, Integer price);
}
