package com.sparkequation.spring.trial.api.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CategoryDto {
    private Integer id;

    @Size(max = 255, message = "Category 'name' field must be less than 256 characters")
    private String name;
}
