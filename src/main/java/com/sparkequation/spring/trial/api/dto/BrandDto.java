package com.sparkequation.spring.trial.api.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class BrandDto {
    private Integer id;

    @Size(max = 255, message = "Brand 'name' field must be less than 256 characters")
    private String name;

    @Size(max = 255, message = "Brand 'country' field must be less than 256 characters")
    private String country;
}
