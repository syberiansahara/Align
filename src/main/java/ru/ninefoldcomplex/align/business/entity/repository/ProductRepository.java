package ru.ninefoldcomplex.align.business.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.business.entity.Brand;
import ru.ninefoldcomplex.align.business.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);
    List<Product> findByProductNameContaining(String productName);
    List<Product> findByBrand(Brand brand);
    List<Product> findByBrand_BrandName(String brandName);
    List<Product> findByQuantity_QuantityLessThan(Integer leftoverMargin);
    Optional<Product> findByProductNameAndBrand_BrandName(String productName, String brandName);
}
