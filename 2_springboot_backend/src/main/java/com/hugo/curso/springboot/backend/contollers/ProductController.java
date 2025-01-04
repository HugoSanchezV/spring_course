package com.hugo.curso.springboot.backend.contollers;

import com.hugo.curso.springboot.backend.entities.Product;
import com.hugo.curso.springboot.backend.services.ProductService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    final private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable Long id) {
        Optional<Product> optionalProduct = service.findById(id);

        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product productOb = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productOb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Long id) {
        Optional<Product> optionalProduct = service.findById(id);
        if (optionalProduct.isPresent()) {
            Product productOb = optionalProduct.orElseThrow();

            productOb.setDescription(product.getDescription());
            productOb.setName(product.getName());
            productOb.setPrice(product.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(productOb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        Optional<Product> optionalProduct = service.findById(id);

        if (optionalProduct.isPresent()) {
            Product productDeleted = service.deleteById(id).orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(productDeleted);
        }

        return ResponseEntity.notFound().build();
    }

}
