package com.example.api.repository;

import com.example.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    boolean existsByNameIgnoreCase(String name);
    List<Product> findByNameContainingIgnoreCase(String name);
}


























/*
* package com.example.api.repository;
import com.example.api.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    //incremenetador de id
    private List<Product> products = new ArrayList<Product>();

    private AtomicLong counter = new AtomicLong(0);

    public List<Product> getProducts(){
        return products;
    }

    public Optional<Product> getProductById(Long id){
        return products.stream().filter(
                (product ) -> product.id().equals(id)
        ).findFirst();
    }

    public Product createProduct(String name, double price){
        Product newProduct = new Product(counter.getAndIncrement(), name, price);
        products.add(newProduct);
        return newProduct;
    }

    //update
    //public Optional <Product> updateProduct(Long id, String name, double price){
     //   for (int i = 0; i < products.size(); i++){
       //     if (products.get(i).id().equals(id)){
         //       Product updated = new Product(id, name, price);
           //     products.set(i, updated);
             //   return Optional.of(updated);
            //}
        //}
        //eturn Optional.empty();
    //}
    //delete
    //ublic boolean deleteProduct(Long id){
    //    return products.removeIf(product -> product.id().equals(id));
    //}
}
* */


