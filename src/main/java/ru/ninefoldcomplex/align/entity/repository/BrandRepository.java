package ru.ninefoldcomplex.align.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.entity.Brand;
import ru.ninefoldcomplex.align.entity.Product;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByBrandName(String brandName);
}
