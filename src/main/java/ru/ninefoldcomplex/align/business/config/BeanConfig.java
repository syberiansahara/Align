package ru.ninefoldcomplex.align.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.ninefoldcomplex.align.business.dao.DAO;
import ru.ninefoldcomplex.align.business.dao.IDAO;
import ru.ninefoldcomplex.align.business.service.IService;
import ru.ninefoldcomplex.align.business.service.ProductService;

@Configuration
public class BeanConfig {
//    @Bean
//    public IDAO iDao() {
//        return new DAO();
//    }
//
//    @Bean
//    public IService iService() {
//        return new ProductService();
//    }
}