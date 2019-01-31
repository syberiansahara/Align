import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import ru.ninefoldcomplex.align.config.JpaConfig;
import ru.ninefoldcomplex.align.entity.Product;
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

    @Test
    public void givenUser_whenSave_thenGetOk() {
        Product product = new Product(1, "john");
        productRepository.save(product);

        Product product2 = productRepository.findOne(1L);
        assertEquals("john", product2.getName());
    }
}