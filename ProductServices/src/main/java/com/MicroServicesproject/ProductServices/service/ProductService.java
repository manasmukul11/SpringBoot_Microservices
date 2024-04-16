package com.MicroServicesproject.ProductServices.service;


import com.MicroServicesproject.ProductServices.dto.ProductRequest;
import com.MicroServicesproject.ProductServices.dto.ProductResponse;
import com.MicroServicesproject.ProductServices.model.Product;
import com.MicroServicesproject.ProductServices.repository.ProductRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository; // to call .save()

    public void createProduct (ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build(); //creating an object product of type product

        productRepository.save(product); // saving the object in repository
        log.info("Product {} is saved", product.getId());
        //The @Slf4j (Simple Logging Facade for Java ) is used to automatically generate a logger field in your class.
    }

    public List<ProductResponse> getAllProducts() {
        //mapping for each product then collecting to the list
        /*  the getAllProducts method fetches a list of Product objects from a repository and transforms each of them
         into a ProductResponse using the mapToProductResponse method. The result is a list of ProductResponse objects
          representing the products retrieved from the repository. */

        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {

        /* This private method takes a Product object as a parameter and creates a corresponding ProductResponse object
         using a builder pattern. It extracts various properties (id, name, description, and price) from the input
         Product and sets them in the ProductResponse object. */

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
