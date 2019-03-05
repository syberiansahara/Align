package ru.ninefoldcomplex.align.business.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.ninefoldcomplex.align.business.entity.Brand;
import ru.ninefoldcomplex.align.business.entity.repository.BrandRepository;
import ru.ninefoldcomplex.align.business.service.IService;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Belyalova1-MS
 */
@RunWith(SpringRunner.class)
//@WebMvcTest(ProductController.class)
//@ContextConfiguration(
//        classes = {JpaConfig.class,
//                BeanConfig.class},
//        loader = AnnotationConfigContextLoader.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //    @MockBean
    @Autowired
    private IService productService;

    @Resource
    private BrandRepository brandRepository;

    private final String BRAND_NAME_ONE = "ninefold";
    private final Brand brandOne = new Brand(BRAND_NAME_ONE);

    private final String PRODUCT_NAME_ONE = "Watch";

    @Before
    public void setUp() throws Exception {
        brandRepository.save(brandOne);
        productService.addProduct(PRODUCT_NAME_ONE, BRAND_NAME_ONE, 3, 200);
    }

    @Test
    public void getLeftovers() throws Exception {
        mockMvc.perform(get("/product/leftovers")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(BRAND_NAME_ONE)));
    }

    @Test
    public void removeProduct() {
    }
}