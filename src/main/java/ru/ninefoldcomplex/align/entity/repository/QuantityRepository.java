package ru.ninefoldcomplex.align.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.entity.Quantity;

import java.util.List;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {
    List<Quantity> findByProductId(long productId);
}
