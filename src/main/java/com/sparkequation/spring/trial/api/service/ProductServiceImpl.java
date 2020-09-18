package com.sparkequation.spring.trial.api.service;

import com.sparkequation.spring.trial.api.model.Product;
import com.sparkequation.spring.trial.api.repository.ProductRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@CommonsLog
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Product> GetProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public Optional<Product> get(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Product create(Product product) {
        if (product.getRating() > 8) {
            product.setFeatured(true);
        }

        Product newProduct = productRepository.save(product);
        log.info("Product #" + newProduct.getId() + " has been created");

        return newProduct;
    }

    @Override
    public void update(Integer id, Product newProduct) {
        if (get(id).isEmpty()) {
            log.info("Product #" + id + " to update not found");
        } else {
            newProduct.setId(id);
            productRepository.save(newProduct);
            log.info("Product #" + id + " has been updated");
        }
    }

    @Override
    public void delete(Integer id) {
        if (get(id).isEmpty()) {
            log.info("Product #" + id + " to remove not found");
        } else {
            productRepository.deleteById(id);
            log.info("Product #" + id + " has been removed");
        }
    }

}
