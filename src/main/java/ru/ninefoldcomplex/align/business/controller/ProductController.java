package ru.ninefoldcomplex.align.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ninefoldcomplex.align.business.service.IService;
import ru.ninefoldcomplex.align.business.utils.exceptions.ProductNotFoundException;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IService productService;

    @GetMapping(value = "/leftovers")
    public ResponseEntity<?> getLeftovers() {
        return new ResponseEntity<>(productService.getLeftovers(), HttpStatus.OK);
    }

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
