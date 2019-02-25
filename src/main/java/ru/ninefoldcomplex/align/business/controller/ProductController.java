package ru.ninefoldcomplex.align.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ninefoldcomplex.align.business.service.IService;
import ru.ninefoldcomplex.align.business.utils.exceptions.ProductNotFoundException;

@RestController
public class ProductController {
    @Autowired
    private IService productService;

    @DeleteMapping()
    public ResponseEntity<?> removeProduct(Long productId) {
        try {
            productService.removeProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
