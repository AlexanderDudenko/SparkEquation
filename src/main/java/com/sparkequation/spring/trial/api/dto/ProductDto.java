package com.sparkequation.spring.trial.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProductDto {
    private Integer id;

    @Size(max = 255, message = "Product 'name' field must be less than 256 characters")
    private String name;

    private Boolean featured; //TODO backlog: validation

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date expirationDate;

    private Integer itemsInStock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date receiptDate;

    private Double rating;

    private BrandDto brand;

    @NotNull(message = "The product must have from 1 to 5 categories")
    private Set<CategoryDto> categories;
}
