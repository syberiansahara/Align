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
import ru.ninefoldcomplex.align.utils.exceptions.ProductAlreadyExistsException;

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
    public Long getProductId(String productName, String brandName) {
        final Product product = productRepository.findByProductNameAndBrand_BrandName(productName, brandName);
        return (product != null) ? product.getProductId() : null;
    }

    @Override
    public Product addProduct(String productName, String brandName, Integer quantity, Integer price) {
        Brand brand = brandRepository.findByBrandName(brandName);
        if (brand == null) throw new BrandNotFoundException();
        if (getProductId(productName, brandName) != null) throw new ProductAlreadyExistsException();
        Product product = new Product(productName, brand);
        productRepository.save(product);
        updateProduct(product, quantity, price);
        return product;
    }

    @Override
    public Product updateProduct(Long productId, Integer quantity, Integer price) {
        final Product product = productRepository.getOne(productId);
        updateProduct(product, quantity, price);
        return product;
    }

    public void updateProduct(Product product, Integer quantity, Integer price) {
        if (quantity != null) quantityRepository.save(new Quantity(product, quantity));
        if (price != null) priceRepository.save(new Price(product, quantity));
    }

    @Override
    public void removeProduct(Long productId) {
        productRepository.delete(productId);
    }
}
