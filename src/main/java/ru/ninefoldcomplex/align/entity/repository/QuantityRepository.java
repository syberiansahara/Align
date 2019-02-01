package ru.ninefoldcomplex.align.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.entity.Quantity;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {
}
