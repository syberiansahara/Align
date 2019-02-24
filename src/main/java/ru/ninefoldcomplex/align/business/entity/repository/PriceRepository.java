package ru.ninefoldcomplex.align.business.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.business.entity.Price;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductId(long productId);
    Price findTopByProductIdOrderByPriceTimestampDesc(long productId);
}
