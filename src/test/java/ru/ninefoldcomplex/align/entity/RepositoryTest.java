package ru.ninefoldcomplex.align.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
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

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
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

    final String BRAND_NAME_ONE = "ninefold";
    final Brand brandOne = new Brand(BRAND_NAME_ONE);

    final String PRODUCT_NAME_ONE = "Watch";
    long PRODUCT_ID_ONE;

    final String PRODUCT_NAME_TWO = "Car";
    long PRODUCT_ID_TWO;

    @Before
    public void setUp() {
        brandRepository.save(brandOne);

        Product product = new Product(PRODUCT_NAME_ONE, brandOne);
        productRepository.save(product);
        PRODUCT_ID_ONE = productRepository.findByProductNameAndBrand_BrandName(PRODUCT_NAME_ONE, BRAND_NAME_ONE).getProductId();

        Price price = new Price(product, 123);
        priceRepository.save(price);

        Quantity quantity = new Quantity(product, 55);
        quantityRepository.save(quantity);
    }

    @Test
    public void test_productName() {
        assertEquals(PRODUCT_NAME_ONE, productRepository.findOne(PRODUCT_ID_ONE).getProductName());
    }

    @Test
    public void test_productBrand() {
        final Product product = productRepository.findOne(PRODUCT_ID_ONE);
        assertEquals(BRAND_NAME_ONE, product.getBrand().getBrandName());
    }

    @Test
    public void test_findByProductName() {
        final Product product = productRepository.findByProductName(PRODUCT_NAME_ONE).get(0);
        assertEquals(PRODUCT_ID_ONE, product.getProductId());
    }

    @Test
    public void test_findByProductNameContaining() {
        final Product product = productRepository.findByProductNameContaining(PRODUCT_NAME_ONE).get(0);
        assertEquals(PRODUCT_ID_ONE, product.getProductId());
    }

    @Test
    public void test_findByBrand() {
        List<Product> products = productRepository.findByBrand(brandOne);
        assertEquals(1, products.size());
    }

    @Test
    public void test_findByBrand_BrandName() {
        List<Product> products = productRepository.findByBrand_BrandName(BRAND_NAME_ONE);
        assertEquals(1, products.size());
    }

    @Test
    public void test_BrandAndProductCascade() {
        Product product = new Product(PRODUCT_NAME_TWO, brandOne);
        productRepository.save(product);

        product.setPrice(new Price(product, 555));
        product.setQuantity(new Quantity(product, 11));
        brandOne.getProducts().add(product);
        brandRepository.save(brandOne);
        PRODUCT_ID_TWO = product.getProductId();

        assertNotNull(productRepository.findOne(PRODUCT_ID_TWO));
        assertEquals(priceRepository.findByProductId(PRODUCT_ID_TWO).size(), 1);
        assertEquals(quantityRepository.findByProductId(PRODUCT_ID_TWO).size(), 1);
    }

    @Test
    public void test_ProductPriceChange() throws InterruptedException {
        Product product = new Product(PRODUCT_NAME_TWO, brandOne);
        productRepository.save(product);
        PRODUCT_ID_TWO = product.getProductId();

        product.setPrice(new Price(product, 777));
        productRepository.save(product);

        Thread.sleep(500);
        product.setPrice(new Price(product, 888));
        productRepository.save(product);

        assertEquals(priceRepository.findByProductId(PRODUCT_ID_TWO).size(), 2);
        assertEquals(productRepository.findByProductNameAndBrand_BrandName(PRODUCT_NAME_TWO, BRAND_NAME_ONE).getPrice(),
                priceRepository.findTopByProductIdOrderByPriceTimestampDesc(PRODUCT_ID_TWO));
    }

    @Test
    public void test_deleteProduct() {
        Product product = new Product(PRODUCT_NAME_TWO, brandOne);
        productRepository.save(product);
        PRODUCT_ID_TWO = product.getProductId();

        product = productRepository.findOne(PRODUCT_ID_TWO);
        assertNotNull(product);

        productRepository.delete(PRODUCT_ID_TWO);
        product = productRepository.findOne(PRODUCT_ID_TWO);
        assertNull(product);
    }

    @Test
    public void test_findByQuantity_QuantityLessThan() {
        List<Product> products = productRepository.findByQuantity_QuantityLessThan(5);
        assertEquals(products.size(), 0);

        Product product = new Product(PRODUCT_NAME_TWO, brandOne);
        product.setQuantity(new Quantity(product, 4));
        productRepository.save(product);

        products = productRepository.findByQuantity_QuantityLessThan(5);
        assertEquals(products.size(), 1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void test_uniqueConstraint() {
        Product product = new Product(PRODUCT_NAME_ONE, brandOne);
        productRepository.save(product);
        productRepository.findAll();
    }
}