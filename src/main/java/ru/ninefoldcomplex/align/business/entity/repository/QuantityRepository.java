package ru.ninefoldcomplex.align.business.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.business.entity.Quantity;

import java.util.List;
import java.util.Optional;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {
    List<Quantity> findByProductId(long productId);
    Optional<Quantity> findTopByProductIdOrderByQuantityTimestampDesc(long productId);
}
