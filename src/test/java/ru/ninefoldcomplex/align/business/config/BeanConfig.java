package ru.ninefoldcomplex.align.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.ninefoldcomplex.align.business.service.IService;
import ru.ninefoldcomplex.align.business.service.ProductService;

@Configuration
@EnableJpaRepositories(basePackages = "ru.ninefoldcomplex.align.business.entity.repository")
public class BeanConfig {
    @Bean
    public IService iService() {
        return new ProductService();
    }
}