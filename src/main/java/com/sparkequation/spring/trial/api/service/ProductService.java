package com.sparkequation.spring.trial.api.service;

import com.sparkequation.spring.trial.api.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> GetProducts();

    Optional<Product> get(Integer id);

    Product create(Product product);

    void update(Integer id, Product newProduct);

    void delete(Integer id);
}
