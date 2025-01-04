package com.hugo.curso.springboot.backend.repositories;

import com.hugo.curso.springboot.backend.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
