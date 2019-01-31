package ru.ninefoldcomplex.align.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import ru.ninefoldcomplex.align.entity.Product;

import javax.transaction.Transactional;

@Transactional
@Repository
public class TextDAO implements ITextDAO {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void getSomething() {
        System.out.println(hibernateTemplate.get(Product.class, 1L).toString());
        new Thread(() -> {
            System.out.println("rrr");
        });
    }
}
