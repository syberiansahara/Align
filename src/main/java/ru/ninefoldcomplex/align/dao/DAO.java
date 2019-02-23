package ru.ninefoldcomplex.align.dao;

import org.springframework.stereotype.Repository;
import ru.ninefoldcomplex.align.entity.Brand;
import ru.ninefoldcomplex.align.entity.Price;
import ru.ninefoldcomplex.align.entity.Product;
import ru.ninefoldcomplex.align.entity.Quantity;
import ru.ninefoldcomplex.align.entity.repository.BrandRepository;
import ru.ninefoldcomplex.align.entity.repository.PriceRepository;
import ru.ninefoldcomplex.align.entity.repository.ProductRepository;
import ru.ninefoldcomplex.align.entity.repository.QuantityRepository;
import ru.ninefoldcomplex.align.utils.exceptions.BrandNotFoundException;

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

    @Override
    public Product addProduct(String productName, String brandName) {
        Brand brand = brandRepository.findByBrandName(brandName);
        if (brand == null) throw new BrandNotFoundException();
        Product product = new Product(productName, brand);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product addProduct(String productName, String brandName, Integer quantity, Integer price) {
        Product product = addProduct(productName, brandName);
        product.setQuantity(new Quantity(product, quantity));
        product.setPrice(new Price(product, price));
        productRepository.save(product);
        return product;
    }

//    @Override
//    public Product addProductWithQuantity(String productName, String brandName, Integer quantity) {
//
//    }
//
//    @Override
//    public Product addProductWithPrice(String productName, String brandName, Integer price) {
//
//    }
}
