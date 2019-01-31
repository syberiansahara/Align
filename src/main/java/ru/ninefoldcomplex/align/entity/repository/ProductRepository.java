package ru.ninefoldcomplex.align.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
