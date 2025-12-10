package com.abhishek.product_service.service;


import com.abhishek.product_service.dto.ProductRequest;
import com.abhishek.product_service.dto.ProductResponse;
import com.abhishek.product_service.models.Product;
import com.abhishek.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;


    public void createProduct(ProductRequest productDto){
        Product product=Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is Saved", product.getId());
    }

    public List<ProductResponse> getAll(){
        List<Product> products=productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();

    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .id(product.getId())
                .price(product.getPrice())
                .build();
    }
}
