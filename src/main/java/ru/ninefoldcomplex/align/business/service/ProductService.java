package ru.ninefoldcomplex.align.business.service;

import org.springframework.stereotype.Service;
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
import java.util.Optional;

@Service
public class ProductService implements IService {
    @Resource
    private ProductRepository productRepository;
    @Resource
    private BrandRepository brandRepository;

    final static int LEFTOVER_MARGIN = 5;

    @Override
    public List<Product> getLeftovers() {
        return productRepository.findByQuantity_QuantityLessThan(LEFTOVER_MARGIN);
    }

    @Override
    public Long getProductId(String productName, String brandName) {
        final Optional<Product> product = productRepository.findByProductNameAndBrand_BrandName(productName, brandName);
        return (product.isPresent()) ? product.get().getProductId() : null;
    }

    @Override
    public Product addProduct(String productName, String brandName, Integer quantity, Integer price) {
        Optional<Brand> brand = brandRepository.findByBrandName(brandName);
        if (!brand.isPresent()) throw new BrandNotFoundException();
        if (productRepository.findByProductNameAndBrand_BrandName(productName, brandName).isPresent())
            throw new ProductAlreadyExistsException();
        Product product = new Product(productName, brand.get());
        productRepository.save(product);
        return updateProduct(product, quantity, price);
    }

    @Override
    public Product updateProduct(Long productId, Integer quantity, Integer price) {
        final Optional<Product> productById = productRepository.findById(productId);
        if (!productById.isPresent()) throw new ProductNotFoundException();
        return updateProduct(productById.get(), quantity, price);
    }

    private Product updateProduct(Product product, Integer quantity, Integer price) {
        if (quantity != null) product.setQuantity(new Quantity(product, quantity));
        if (price != null) product.setPrice(new Price(product, price));
        return product;
    }

    @Override
    public void removeProduct(Long productId) {
        final Optional<Product> productById = productRepository.findById(productId);
        if (!productById.isPresent()) throw new ProductNotFoundException();
        productRepository.delete(productById.get());
    }
}
