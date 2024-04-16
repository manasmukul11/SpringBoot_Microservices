package com.MicroServicesproject.ProductServices.repository;


import com.MicroServicesproject.ProductServices.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
