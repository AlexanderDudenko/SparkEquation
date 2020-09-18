package com.sparkequation.spring.trial.api.controller;

import com.sparkequation.spring.trial.api.dto.ProductDto;
import com.sparkequation.spring.trial.api.model.Product;
import com.sparkequation.spring.trial.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.GetProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Integer id) {
        Optional<Product> product = productService.get(id);
        Optional<ProductDto> response = product.map(value -> conversionService.convert(value, ProductDto.class));
        return ResponseEntity.of(response);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        productDto.setId(null);
        Product productToCreate = conversionService.convert(productDto, Product.class);
        Product newProduct = productService.create(productToCreate);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newProduct.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Integer id, @Valid @RequestBody ProductDto productDto) {
        Product product = conversionService.convert(productDto, Product.class);
        productService.update(id, product);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
