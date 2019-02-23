package ru.ninefoldcomplex.align.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.ninefoldcomplex.align.dao.DAO;
import ru.ninefoldcomplex.align.dao.IDAO;
import ru.ninefoldcomplex.align.entity.RepositoryTest;

@Configuration
@EnableJpaRepositories(basePackages = "ru.ninefoldcomplex.align.entity.repository")
public class TestBeanConfig {
    @Bean
    public RepositoryTest repositoryTest() {
        return new RepositoryTest();
    }
}