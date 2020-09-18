package com.sparkequation.spring.trial.api.service;

import com.sparkequation.spring.trial.api.model.Brand;
import com.sparkequation.spring.trial.api.model.Product;
import com.sparkequation.spring.trial.api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void getProductsTest() {
        List<Product> products = productService.GetProducts();
        assertThat(products).hasSize(29);
    }

    @Test
    void getProductPositiveTest() {
        Brand expectedBrand = new Brand();
        expectedBrand.setId(4);
        expectedBrand.setName("Bordeaux");
        expectedBrand.setCountry("France");

        Product expectedProduct = new Product();
        expectedProduct.setId(1);
        expectedProduct.setName("La Vieille Ferme Rouge");
        expectedProduct.setFeatured(false);
        expectedProduct.setItemsInStock(0);
        expectedProduct.setRating(5.0);
        expectedProduct.setBrand(expectedBrand);

        Optional<Product> product = productService.get(1);
        assertThat(product).isPresent();
        assertThat(product.get().getId()).isEqualTo(1);
        assertThat(product.get().getName()).isEqualTo("La Vieille Ferme Rouge");
        assertThat(product.get().isFeatured()).isFalse();
        assertThat(product.get().getItemsInStock()).isEqualTo(0);
        assertThat(product.get().getRating()).isEqualTo(5.0);
        assertThat(product.get().getBrand().getId()).isEqualTo(4);
        assertThat(product.get().getBrand().getName()).isEqualTo("Bordeaux");
        assertThat(product.get().getBrand().getCountry()).isEqualTo("France");
    }

    @Test
    void getProductNegativeTst() {
        Optional<Product> product = productService.get(99);
        assertThat(product).isEmpty();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void simpleCreateProductTest() {
        Product productToCreate = new Product();
        productToCreate.setName("myProduct");
        productToCreate.setRating(8.0);

        // check prerequisites
        Optional<Product> product = productRepository.findOne(Example.of(productToCreate));
        assertThat(product).isEmpty();

        productService.create(productToCreate);
        Optional<Product> product2 = productRepository.findOne(Example.of(productToCreate));
        assertThat(product2).isPresent();
        assertThat(product2.get().getName()).isEqualTo("myProduct");
        assertThat(product2.get().isFeatured()).isFalse();

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createWithHighRatingProductTest() {
        Product productToCreate = new Product();
        productToCreate.setName("myProduct");
        productToCreate.setRating(8.1);

        Optional<Product> product = productRepository.findOne(Example.of(productToCreate));
        assertThat(product).isEmpty();

        productService.create(productToCreate);
        Optional<Product> product2 = productRepository.findOne(Example.of(productToCreate));
        assertThat(product2).isPresent();
        assertThat(product2.get().getName()).isEqualTo("myProduct");
        assertThat(product2.get().isFeatured()).isTrue();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateProductTest() {
        Brand brand = new Brand();
        brand.setId(8);
        brand.setCountry("Japan");
        brand.setName("Toyota");

        Product productToUpdate = new Product();
        productToUpdate.setName("Cube2");
        productToUpdate.setFeatured(false);
        productToUpdate.setItemsInStock(0);
        productToUpdate.setRating(4.0);
        productToUpdate.setBrand(brand);

        Optional<Product> product = productService.get(29);
        assertThat(product).isPresent();
        assertThat(product.get().getName()).isEqualTo("Cube");

        productService.update(29, productToUpdate);

        Optional<Product> productAfterUpdate = productService.get(29);
        assertThat(productAfterUpdate).isPresent();
        assertThat(productAfterUpdate.get().getName()).isNotEqualTo("Cube");
        assertThat(productAfterUpdate.get().getName()).isEqualTo("Cube2");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void delete() {
        Optional<Product> product = productService.get(1);
        assertThat(product).isPresent();

        productService.delete(1);
        Optional<Product> productAfterDeleteOperation = productService.get(1);
        assertThat(productAfterDeleteOperation).isEmpty();
    }
}