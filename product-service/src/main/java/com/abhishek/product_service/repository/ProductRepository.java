package com.abhishek.product_service.repository;

import com.abhishek.product_service.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {


}
