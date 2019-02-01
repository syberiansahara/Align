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
import ru.ninefoldcomplex.align.entity.repository.PriceRepository;
import ru.ninefoldcomplex.align.entity.repository.ProductRepository;
import ru.ninefoldcomplex.align.entity.repository.QuantityRepository;

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
    @Resource
    private PriceRepository priceRepository;
    @Resource
    private QuantityRepository quantityRepository;

    @Before
    public void setUp() {
        Brand brand = new Brand("ninefold");
        brandRepository.save(brand);

        Product product = new Product(1, "Watch");
        product.setBrand(brand);
        productRepository.save(product);

        Price price = new Price(product, 123);
        product.setPrice(price);
        priceRepository.save(price);

        Quantity quantity = new Quantity(product, 55);
        product.setQuantity(quantity);
        quantityRepository.save(quantity);

        productRepository.save(product);
    }

    @Test
    public void checkProduct() {
        assertEquals("Watch", productRepository.findOne(1L).getProductName());
    }

    @Test
    public void checkBrand() {
        final Product product = productRepository.findOne(1L);
        assertEquals("ninefold", product.getBrand().getBrandName());
    }
}