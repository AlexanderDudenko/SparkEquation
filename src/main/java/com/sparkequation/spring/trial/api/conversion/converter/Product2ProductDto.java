package com.sparkequation.spring.trial.api.conversion.converter;

import com.sparkequation.spring.trial.api.dto.ProductDto;
import com.sparkequation.spring.trial.api.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;

public class Product2ProductDto implements Converter<Product, ProductDto> {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ProductDto convert(Product source) {
        return modelMapper.map(source, ProductDto.class);
    }
}
