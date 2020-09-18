package com.sparkequation.spring.trial.api.configuration;

import com.sparkequation.spring.trial.api.conversion.converter.Product2ProductDto;
import com.sparkequation.spring.trial.api.conversion.converter.ProductDto2Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionConfiguration {

    @Bean
    ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new ProductDto2Product());
        converters.add(new Product2ProductDto());
        bean.setConverters(converters);
        return bean;
    }
}
