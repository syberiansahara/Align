package ru.ninefoldcomplex.align.business.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import ru.ninefoldcomplex.align.business.config.BeanConfig;
import ru.ninefoldcomplex.align.business.config.JpaConfig;
import ru.ninefoldcomplex.align.business.entity.repository.BrandRepository;
import ru.ninefoldcomplex.align.business.entity.repository.PriceRepository;
import ru.ninefoldcomplex.align.business.entity.repository.ProductRepository;
import ru.ninefoldcomplex.align.business.entity.repository.QuantityRepository;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class,
                BeanConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
public class RepositoryTest {
    @Resource
    private ProductRepository productRepository;
    @Resource
    private BrandRepository brandRepository;
    @Resource
    private PriceRepository priceRepository;
    @Resource
    private QuantityRepository quantityRepository;

    final static String BRAND_NAME_ONE = "ninefold";
    final Brand BRAND_ONE = new Brand(BRAND_NAME_ONE);

    final static String PRODUCT_NAME_ONE = "Watch";
    long productIdOne;

    final static String PRODUCT_NAME_TWO = "Car";
    long productIdTwo;

    @Before
    public void setUp() {
        brandRepository.save(BRAND_ONE);

        Product product = new Product(PRODUCT_NAME_ONE, BRAND_ONE);
        productRepository.save(product);
        productIdOne = productRepository.findByProductNameAndBrand_BrandName(PRODUCT_NAME_ONE, BRAND_NAME_ONE).get().getProductId();

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
        assertEquals(PRODUCT_NAME_ONE, productRepository.findById(productIdOne).get().getProductName());
    }

    @Test
    public void test_productBrand() {
        final Product product = productRepository.findById(productIdOne).get();
        assertEquals(BRAND_NAME_ONE, product.getBrand().getBrandName());
    }

    @Test
    public void test_findByProductName() {
        final Product product = productRepository.findByProductName(PRODUCT_NAME_ONE).get(0);
        assertEquals(productIdOne, product.getProductId());
    }

    @Test
    public void test_findByProductNameContaining() {
        final Product product = productRepository.findByProductNameContaining(PRODUCT_NAME_ONE).get(0);
        assertEquals(productIdOne, product.getProductId());
    }

    @Test
    public void test_findByBrand() {
        List<Product> products = productRepository.findByBrand(BRAND_ONE);
        assertEquals(1, products.size());
    }

    @Test
    public void test_findByBrand_BrandName() {
        List<Product> products = productRepository.findByBrand_BrandName(BRAND_NAME_ONE);
        assertEquals(1, products.size());
    }

    @Test
    public void test_BrandAndProductCascade() {
        Product product = new Product(PRODUCT_NAME_TWO, BRAND_ONE);
        productRepository.save(product);

        product.setPrice(new Price(product, 555));
        product.setQuantity(new Quantity(product, 11));
        BRAND_ONE.getProducts().add(product);
        brandRepository.save(BRAND_ONE);
        productIdTwo = product.getProductId();

        assertTrue(productRepository.findById(productIdTwo).isPresent());
        assertEquals(priceRepository.findByProductId(productIdTwo).size(), 1);
        assertEquals(quantityRepository.findByProductId(productIdTwo).size(), 1);
    }

    @Test
    public void test_ProductCascade() {
        assertEquals(priceRepository.findByProductId(productIdOne).size(), 1);
        assertEquals(quantityRepository.findByProductId(productIdOne).size(), 1);

        final Product product = productRepository.findById(productIdOne).get();
        product.setPrice(new Price(product, 555));
        product.setQuantity(new Quantity(product, 11));

        assertEquals(priceRepository.findByProductId(productIdOne).size(), 2);
        assertEquals(quantityRepository.findByProductId(productIdOne).size(), 2);
    }

    @Test
    public void test_ProductPriceChange() throws InterruptedException {
        Product product = new Product(PRODUCT_NAME_TWO, BRAND_ONE);
        productRepository.save(product);
        productIdTwo = product.getProductId();

        product.setPrice(new Price(product, 777));
        productRepository.save(product);

        Thread.sleep(500);
        product.setPrice(new Price(product, 888));
        productRepository.save(product);

        assertEquals(priceRepository.findByProductId(productIdTwo).size(), 2);
        assertEquals(productRepository.findByProductNameAndBrand_BrandName(PRODUCT_NAME_TWO, BRAND_NAME_ONE).get().getPrice(),
                priceRepository.findTopByProductIdOrderByPriceTimestampDesc(productIdTwo).get());
    }

    public void test_deleteProduct() {
        Product product = new Product(PRODUCT_NAME_TWO, BRAND_ONE);
        productRepository.save(product);
        productIdTwo = product.getProductId();

        assertTrue(productRepository.findById(productIdTwo).isPresent());

        productRepository.delete(product);
        assertFalse(productRepository.findById(productIdTwo).isPresent());
    }

    @Test
    public void test_findByQuantity_QuantityLessThan() {
        List<Product> products = productRepository.findByQuantity_QuantityLessThan(5);
        assertEquals(products.size(), 0);

        Product product = new Product(PRODUCT_NAME_TWO, BRAND_ONE);
        product.setQuantity(new Quantity(product, 4));
        productRepository.save(product);

        products = productRepository.findByQuantity_QuantityLessThan(5);
        assertEquals(products.size(), 1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void test_uniqueConstraint() {
        Product product = new Product(PRODUCT_NAME_ONE, BRAND_ONE);
        productRepository.save(product);
        productRepository.findAll();
    }
}