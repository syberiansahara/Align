package ru.ninefoldcomplex.align.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ninefoldcomplex.align.business.dao.IDAO;
import ru.ninefoldcomplex.align.business.entity.Product;

import java.util.List;

@Service
public class ProductService implements IService {
    @Autowired
    private IDAO dao;

    @Override
    public List<Product> getLeftovers() {
        return dao.getLeftovers();
    }

    @Override
    public Long getProductId(String productName, String brandName) {
        return dao.getProductId(productName, brandName);
    }

    @Override
    public Product addProduct(String productName, String brandName, Integer quantity, Integer price) {
        return dao.addProduct(productName, brandName, quantity, price);
    }

    @Override
    public Product updateProduct(Long productId, Integer quantity, Integer price) {
        return dao.updateProduct(productId, quantity, price);
    }

    @Override
    public void removeProduct(Long productId) {
        dao.removeProduct(productId);
    }
}
