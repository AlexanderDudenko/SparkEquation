package com.sparkequation.spring.trial.api.conversion.converter;

import com.sparkequation.spring.trial.api.dto.ProductDto;
import com.sparkequation.spring.trial.api.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;

public class ProductDto2Product implements Converter<ProductDto, Product> {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Product convert(ProductDto source) {
        return modelMapper.map(source, Product.class);
    }
}
