package ru.ninefoldcomplex.align.business.dao;

import org.springframework.stereotype.Repository;
import ru.ninefoldcomplex.align.business.entity.Brand;
import ru.ninefoldcomplex.align.business.entity.Price;
import ru.ninefoldcomplex.align.business.entity.Product;
import ru.ninefoldcomplex.align.business.entity.Quantity;
import ru.ninefoldcomplex.align.business.entity.repository.BrandRepository;
import ru.ninefoldcomplex.align.business.entity.repository.ProductRepository;
import ru.ninefoldcomplex.align.business.utils.exceptions.BrandNotFoundException;
import ru.ninefoldcomplex.align.business.utils.exceptions.ProductAlreadyExistsException;
import ru.ninefoldcomplex.align.business.utils.exceptions.ProductNotFoundException;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class DAO implements IDAO {
    @Resource
    private ProductRepository productRepository;
    @Resource
    private BrandRepository brandRepository;

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
        return updateProduct(product, quantity, price);
    }

    @Override
    public Product updateProduct(Long productId, Integer quantity, Integer price) {
        final Product productById = getProductById(productId);
        if (productById == null) throw new ProductNotFoundException();
        return updateProduct(productById, quantity, price);
    }

    private Product updateProduct(Product product, Integer quantity, Integer price) {
        if (quantity != null) product.setQuantity(new Quantity(product, quantity));
        if (price != null) product.setPrice(new Price(product, quantity));
        productRepository.save(product);
        return product;
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public void removeProduct(Long productId) {
        final Product productById = getProductById(productId);
        if (productById == null) throw new ProductNotFoundException();
        productRepository.delete(productById);
    }
}
