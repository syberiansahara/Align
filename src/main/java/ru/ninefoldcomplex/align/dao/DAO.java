package ru.ninefoldcomplex.align.dao;

import org.springframework.stereotype.Repository;
import ru.ninefoldcomplex.align.entity.Product;
import ru.ninefoldcomplex.align.entity.repository.BrandRepository;
import ru.ninefoldcomplex.align.entity.repository.PriceRepository;
import ru.ninefoldcomplex.align.entity.repository.ProductRepository;
import ru.ninefoldcomplex.align.entity.repository.QuantityRepository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class DAO implements IDAO {
    @Resource
    private ProductRepository productRepository;
    @Resource
    private BrandRepository brandRepository;
    @Resource
    private PriceRepository priceRepository;
    @Resource
    private QuantityRepository quantityRepository;

    @Override
    public List<Product> getLeftovers() {
        final int LEFTOVER_MARGIN = 5;
        return productRepository.findByQuantity_QuantityLessThan(LEFTOVER_MARGIN);
    }
}
