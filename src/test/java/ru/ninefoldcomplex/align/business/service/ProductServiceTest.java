package ru.ninefoldcomplex.align.business.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import ru.ninefoldcomplex.align.business.config.BeanConfig;
import ru.ninefoldcomplex.align.business.config.JpaConfig;
import ru.ninefoldcomplex.align.business.entity.Brand;
import ru.ninefoldcomplex.align.business.entity.Price;
import ru.ninefoldcomplex.align.business.entity.Product;
import ru.ninefoldcomplex.align.business.entity.Quantity;
import ru.ninefoldcomplex.align.business.entity.repository.BrandRepository;
import ru.ninefoldcomplex.align.business.entity.repository.PriceRepository;
import ru.ninefoldcomplex.align.business.entity.repository.ProductRepository;
import ru.ninefoldcomplex.align.business.entity.repository.QuantityRepository;
import ru.ninefoldcomplex.align.business.utils.exceptions.BrandNotFoundException;
import ru.ninefoldcomplex.align.business.utils.exceptions.ProductAlreadyExistsException;
import ru.ninefoldcomplex.align.business.utils.exceptions.ProductNotFoundException;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author Belyalova1-MS
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class,
                BeanConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
public class ProductServiceTest {
    @Resource
    private ProductRepository productRepository;
    @Resource
    private BrandRepository brandRepository;
    @Resource
    private PriceRepository priceRepository;
    @Resource
    private QuantityRepository quantityRepository;

    @Autowired
    private IService productService;

    final String BRAND_NAME_ONE = "ninefold";
    final Brand brandOne = new Brand(BRAND_NAME_ONE);

    final String PRODUCT_NAME_ONE = "Watch";
    final String PRODUCT_NAME_TWO = "Monitor";

    @Before
    public void setUp() {
        brandRepository.save(brandOne);
        Product product = new Product(PRODUCT_NAME_ONE, brandOne);
        product.setQuantity(new Quantity(product, 3));
        product.setPrice(new Price(product, 199));
        productRepository.save(product);
    }

    @Test
    public void test_getLeftovers() {
        assertEquals(productService.getLeftovers().size(), 1);
    }

    @Test
    public void test_getProductId() {
        assertNotNull(productService.getProductId(PRODUCT_NAME_ONE, BRAND_NAME_ONE));
        assertNull(productService.getProductId("gar", "bage"));
    }

    @Test(expected = BrandNotFoundException.class)
    public void test_addProduct_BrandNotFound() {
        productService.addProduct(PRODUCT_NAME_TWO, "Wow", null, null);
    }

    @Test(expected = ProductAlreadyExistsException.class)
    public void test_addProduct_ProductAlreadyExists() {
        productService.addProduct(PRODUCT_NAME_ONE, BRAND_NAME_ONE, null, null);
    }

    @Test
    public void test_addProduct() {
        Product product = productService.addProduct(PRODUCT_NAME_TWO, BRAND_NAME_ONE, null, 144);
        Long productId = product.getProductId();
        assertEquals(quantityRepository.findByProductId(productId).size(), 0);
        assertEquals(priceRepository.findByProductId(productId).size(), 1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void test_updateProduct_ProductNotFound() {
        productService.updateProduct(111L, 77, 199);
    }

    @Test
    public void test_updateProduct() {
        Long productId = productService.getProductId(PRODUCT_NAME_ONE, BRAND_NAME_ONE);
        assertEquals(quantityRepository.findByProductId(productId).size(), 1);
        assertEquals(priceRepository.findByProductId(productId).size(), 1);
        productService.updateProduct(productId, 77, null);
        assertEquals(quantityRepository.findByProductId(productId).size(), 2);
        assertEquals(priceRepository.findByProductId(productId).size(), 1);
        productService.updateProduct(productId, 44, 99);
        assertEquals(quantityRepository.findByProductId(productId).size(), 3);
        assertEquals(priceRepository.findByProductId(productId).size(), 2);
    }

    @Test(expected = ProductNotFoundException.class)
    public void test_removeProduct_ProductNotFound() {
        productService.removeProduct(111L);
    }

    @Test
    public void test_removeProduct() {
        final Long productId = productService.getProductId(PRODUCT_NAME_ONE, BRAND_NAME_ONE);
        assertNotNull(productId);
        productService.removeProduct(productId);
        assertNull(productService.getProductId(PRODUCT_NAME_ONE, BRAND_NAME_ONE));
    }
}