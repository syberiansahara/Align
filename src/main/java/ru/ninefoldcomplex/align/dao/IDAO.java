package ru.ninefoldcomplex.align.dao;

import ru.ninefoldcomplex.align.entity.Product;

import java.util.List;

public interface IDAO {
    List<Product> getLeftovers();
    Product addProduct(String productName, String brandName);
    Product addProduct(String productName, String brandName, Integer quantity, Integer price);
//    Product addProductWithQuantity(String productName, String brandName, Integer quantity);
//    Product addProductWithPrice(String productName, String brandName, Integer price);
}
