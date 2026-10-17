package com.example.api.controller;
import com.example.api.model.Product;
import com.example.api.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping("")
    public List<Product> getProduct(){
        return productService.getProducts();
    }
    @GetMapping("{id}")
    public Optional<Product> getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }
    @PostMapping
    public Product createProduct(@RequestParam  String name,@RequestParam double price){
        return productService.createProduct(name, price);
    }
    //@PutMapping("{id}")
    //public Optional<Product> updateProduct(@PathVariable Long id, @RequestParam String name, @RequestParam double price){
    //    return productService.updateProduct(id, name, price);
    //}
    //@DeleteMapping("{id}")
    //public boolean deleteProduct(@PathVariable Long id){
    //    return productService.deleteProduct(id);
    //}
}
