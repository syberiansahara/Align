package ru.ninefoldcomplex.align.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import ru.ninefoldcomplex.align.config.BeanConfig;
import ru.ninefoldcomplex.align.config.JpaConfig;
import ru.ninefoldcomplex.align.config.TestBeanConfig;
import ru.ninefoldcomplex.align.entity.RepositoryTest;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class,
                BeanConfig.class,
                TestBeanConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
public class DAOTest {
    @Autowired
    private RepositoryTest repositoryTest;

    @Autowired
    private IDAO dao;

    @Before
    public void setUp() {
        repositoryTest.setUp();
    }

    @Test
    public void getLeftovers() {
        assertEquals(dao.getLeftovers().size(), 0);
    }
}