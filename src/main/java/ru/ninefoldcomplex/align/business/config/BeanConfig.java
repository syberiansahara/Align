package ru.ninefoldcomplex.align.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.ninefoldcomplex.align.business.dao.DAO;
import ru.ninefoldcomplex.align.business.dao.IDAO;

@Configuration
@EnableJpaRepositories(basePackages = "ru.ninefoldcomplex.align.business.entity.repository")
public class BeanConfig {
    @Bean
    public IDAO iDao() {
        return new DAO();
    }
}