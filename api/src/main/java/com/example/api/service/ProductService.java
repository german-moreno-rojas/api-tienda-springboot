package com.example.api.service;

import com.example.api.model.Product;
import com.example.api.model.ProductResponse;
import com.example.api.repository.ProductRepository;
import com.example.api.utils.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getProducts(){
        return  productRepository.findAll().stream().map(
                ProductResponse::from
        ).toList();
    }

    public ProductResponse getProductById(Long id){
        return productRepository.findById(id).map(
                ProductResponse::from
        ).orElseThrow(() -> new ProductNotFoundException("No encontramos ese ID"));
    }

    public ProductResponse createProduct(String name, BigDecimal price){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("El valor del nombre es incorrecto");
        }
        Product productSave = productRepository.save( new Product(null, name, price));
        return  ProductResponse.from(productSave);
    }
}
