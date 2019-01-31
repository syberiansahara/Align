package ru.ninefoldcomplex.align.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ninefoldcomplex.align.entity.Theme;

public interface UserRepository extends JpaRepository<Theme, Long> {
}
