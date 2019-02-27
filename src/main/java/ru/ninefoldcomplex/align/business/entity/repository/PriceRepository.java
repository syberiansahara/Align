package ru.ninefoldcomplex.align.business.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.business.entity.Price;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductId(long productId);
    Optional<Price> findTopByProductIdOrderByPriceTimestampDesc(long productId);
}
