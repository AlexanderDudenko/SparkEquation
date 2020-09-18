package com.sparkequation.spring.trial.api.controller;

import com.sparkequation.spring.trial.api.conversion.converter.Product2ProductDto;
import com.sparkequation.spring.trial.api.conversion.converter.ProductDto2Product;
import com.sparkequation.spring.trial.api.model.Category;
import com.sparkequation.spring.trial.api.model.Product;
import com.sparkequation.spring.trial.api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private GenericConversionService conversionService;

    @MockBean
    private ProductService service;

    @Test
    void getProductsTest() throws Exception {
        mvc.perform(get("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void getProductOkTest() throws Exception {
        conversionService.addConverter(new Product2ProductDto());
        Mockito.when(service.get(anyInt())).thenReturn(Optional.of(new Product()));

        mvc.perform(get("/api/product/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void getProductNotFoundTest() throws Exception {
        mvc.perform(get("/api/product/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void createProductSimpleTest() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("Drinks");

        Product product = new Product();
        product.setName("myProduct");
        product.setCategories(Collections.singleton(category));

        conversionService.addConverter(new ProductDto2Product());

        Mockito.when(service.create(any(Product.class))).thenReturn(new Product());

        mvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"myProduct\", \"categories\":[{\"id\":1,\"name\":\"Drinks\"}]}")
        ).andExpect(status().isCreated());
    }

    @Test
    void createProductWithoutCategoriesTest() throws Exception {
        mvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"myProduct\"}")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createProductWithLongNameTest() throws Exception {
        mvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\", \"categories\":[{\"id\":1,\"name\":\"Drinks\"}]}")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateProductSimpleTest() throws Exception {
        mvc.perform(put("/api/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"myProduct\", \"categories\":[{\"id\":1,\"name\":\"Drinks\"}]}")
        ).andExpect(status().isNoContent());
    }

    @Test
    void updateProductWithoutCategoryTest() throws Exception {
        mvc.perform(put("/api/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"myProduct\"}")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateProductByLongNameTest() throws Exception {
        mvc.perform(put("/api/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\", \"categories\":[{\"id\":1,\"name\":\"Drinks\"}]}")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deleteProduct() throws Exception {
        mvc.perform(delete("/api/product/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }
}