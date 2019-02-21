package ru.ninefoldcomplex.align.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.entity.Price;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductId(long productId);
    Price findTopByProductIdOrderByPriceTimestampDesc(long productId);
}
