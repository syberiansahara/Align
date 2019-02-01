package ru.ninefoldcomplex.align.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import ru.ninefoldcomplex.align.config.JpaConfig;
import ru.ninefoldcomplex.align.entity.repository.BrandRepository;
import ru.ninefoldcomplex.align.entity.repository.ProductRepository;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { JpaConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
public class ProductTest {

    @Resource
    private ProductRepository productRepository;
    @Resource
    private BrandRepository brandRepository;

    @Before
    public void setUp() {
        Brand brand = new Brand("ninefold");
        brandRepository.save(brand);

        Product product = new Product(1, "Watch");
        product.setBrandId(brand.getBrandId());
        productRepository.save(product);
    }

    @Test
    public void checkProduct() {
        assertEquals("Watch", productRepository.findOne(1L).getProductName());
    }

    @Test
    public void checkBrand() {
        assertEquals("ninefold", productRepository.findOne(1L).getBrand().getBrandName());
    }
}