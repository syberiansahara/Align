package ru.ninefoldcomplex.align.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.entity.Brand;
import ru.ninefoldcomplex.align.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);
    List<Product> findByProductNameContaining(String productName);
    List<Product> findByBrand(Brand brand);
    List<Product> findByBrand_BrandName(String brandName);
    List<Product> findByQuantity_QuantityLessThan(Integer leftoverMargin);
    Product findByProductNameAndBrand_BrandName(String productName, String brandName);
}
