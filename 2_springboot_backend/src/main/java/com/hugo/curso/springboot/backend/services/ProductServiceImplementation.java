package com.hugo.curso.springboot.backend.services;

import com.hugo.curso.springboot.backend.entities.Product;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {
    final private ProductServiceImplementation repository;

    public ProductServiceImplementation(ProductServiceImplementation repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return this.repository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> deleteById(Long id) {
        Optional<Product> productOptional = this.repository.findById(id);

        if (productOptional.isPresent()) {
            this.deleteById(id);
            return productOptional;
        }
        return Optional.empty();
    }
}
