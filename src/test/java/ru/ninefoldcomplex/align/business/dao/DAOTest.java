package ru.ninefoldcomplex.align.business.dao;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class,
                BeanConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
public class DAOTest {
    @Resource
    private ProductRepository productRepository;
    @Resource
    private BrandRepository brandRepository;
    @Resource
    private PriceRepository priceRepository;
    @Resource
    private QuantityRepository quantityRepository;

    @Autowired
    private IDAO dao;

    final String BRAND_NAME_ONE = "ninefold";
    final Brand brandOne = new Brand(BRAND_NAME_ONE);

    final String PRODUCT_NAME_ONE = "Watch";
    final String PRODUCT_NAME_TWO = "Monitor";

    @Before
    public void setUp() {
        brandRepository.save(brandOne);
        Product product = new Product(PRODUCT_NAME_ONE, brandOne);
        productRepository.save(product);
        priceRepository.save(new Price(product, 123));
        quantityRepository.save(new Quantity(product, 55));
    }

    @Test
    public void test_getLeftovers() {
        assertEquals(dao.getLeftovers().size(), 0);
        dao.addProduct("Wow", BRAND_NAME_ONE, 4, 88);
        assertEquals(dao.getLeftovers().size(), 1);
    }

    @Test
    public void test_getProductId() {
        assertNotNull(dao.getProductId(PRODUCT_NAME_ONE, BRAND_NAME_ONE));
        assertNull(dao.getProductId("gar", "bage"));
    }

    @Test(expected = BrandNotFoundException.class)
    public void test_addProduct_BrandNotFound() {
        dao.addProduct(PRODUCT_NAME_TWO, "Wow", null, null);
    }

    @Test(expected = ProductAlreadyExistsException.class)
    public void test_addProduct_ProductAlreadyExists() {
        dao.addProduct(PRODUCT_NAME_ONE, BRAND_NAME_ONE, null, null);
    }

    @Test
    public void test_addProduct() {
        Product product = dao.addProduct(PRODUCT_NAME_TWO, BRAND_NAME_ONE, null, 144);
        Long productId = product.getProductId();
        assertEquals(quantityRepository.findByProductId(productId).size(), 0);
        assertEquals(priceRepository.findByProductId(productId).size(), 1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void test_updateProduct_ProductNotFound() {
        dao.updateProduct(111L, 77, 199);
    }

    @Test
    public void test_updateProduct() {
        Long productId = dao.getProductId(PRODUCT_NAME_ONE, BRAND_NAME_ONE);
        dao.updateProduct(productId, 77, null);
        assertEquals(quantityRepository.findByProductId(productId).size(), 2);
        assertEquals(priceRepository.findByProductId(productId).size(), 1);
        dao.updateProduct(productId, 44, 99);
        assertEquals(quantityRepository.findByProductId(productId).size(), 3);
        assertEquals(priceRepository.findByProductId(productId).size(), 2);
    }

    @Test(expected = ProductNotFoundException.class)
    public void test_removeProduct_ProductNotFound() {
        dao.removeProduct(111L);
    }

    @Test
    public void test_removeProduct() {
        Long productId = dao.getProductId(PRODUCT_NAME_ONE, BRAND_NAME_ONE);
        assertNotNull(productRepository.findById(productId).orElse(null));
        dao.removeProduct(productId);
        assertNull(productRepository.findById(productId).orElse(null));
    }
}