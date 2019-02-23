package ru.ninefoldcomplex.align.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.ninefoldcomplex.align.dao.DAO;
import ru.ninefoldcomplex.align.dao.IDAO;

@Configuration
@EnableJpaRepositories(basePackages = "ru.ninefoldcomplex.align.entity.repository")
public class BeanConfig {
    @Bean
    public IDAO iDao() {
        return new DAO();
    }
}