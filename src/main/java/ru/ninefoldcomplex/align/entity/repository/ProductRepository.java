package ru.ninefoldcomplex.align.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.ninefoldcomplex.align.entity.Product;

import javax.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);
}
