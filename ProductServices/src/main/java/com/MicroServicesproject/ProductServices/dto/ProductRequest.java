package com.MicroServicesproject.ProductServices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/* One of the common design patterns used to achieve this is called a Data Transfer Object (DTO).
 A DTO is a plain Java object that is used to transfer data between different layers of an application.
 Instead of doing this in repository or service layer. To separate the entities from model so that we don't expose it.
  */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;

    private String description;

    private BigDecimal price;
}
