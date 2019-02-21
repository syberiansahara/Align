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

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { JpaConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
public class DatabaseTest {
    @Resource
    private ProductRepository productRepository;
    @Resource
    private BrandRepository brandRepository;
    @Resource
    private PriceRepository priceRepository;
    @Resource
    private QuantityRepository quantityRepository;

    final String BRAND_ONE = "ninefold";

    final String PRODUCT_NAME_ONE = "Watch";
    final long PRODUCT_ID_ONE = 1L;

    @Before
    public void setUp() {
        Brand brand = new Brand(BRAND_ONE);
        brandRepository.save(brand);

        Product product = new Product(1, PRODUCT_NAME_ONE, brand);
        productRepository.save(product);

        Price price = new Price(product, 123);
        priceRepository.save(price);

        Quantity quantity = new Quantity(product, 55);
        quantityRepository.save(quantity);

        product.setPrice(price);
        product.setQuantity(quantity);
        productRepository.save(product);
    }

    @Test
    public void test_productName() {
        assertEquals(PRODUCT_NAME_ONE, productRepository.findOne(PRODUCT_ID_ONE).getProductName());
    }

    @Test
    public void test_productBrand() {
        final Product product = productRepository.findOne(PRODUCT_ID_ONE);
        assertEquals(BRAND_ONE, product.getBrand().getBrandName());
    }

    @Test
    public void test_findByProductName() {
        final Product product = productRepository.findByProductName(PRODUCT_NAME_ONE);
        assertEquals(PRODUCT_ID_ONE, product.getProductId());
    }

    @Test
    public void test_findByBrand() {
        final Brand brand = brandRepository.findByBrandName(BRAND_ONE);
        List<Product> products = productRepository.findByBrand(brand);
        assertEquals(1, products.size());
    }
}