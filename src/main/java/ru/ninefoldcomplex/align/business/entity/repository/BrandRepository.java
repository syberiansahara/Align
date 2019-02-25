package ru.ninefoldcomplex.align.business.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.business.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByBrandName(String brandName);
}
