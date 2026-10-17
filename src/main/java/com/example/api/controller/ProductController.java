package com.example.api.controller;

import com.example.api.model.ProductRequest;
import com.example.api.model.ProductResponse;
import com.example.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST del módulo de productos.
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Lista todos los productos.
     *
     * @return 200 con la lista de productos
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProduct() {
        return ResponseEntity.ok(productService.getProducts());
    }

    /**
     * Consulta un producto por id.
     *
     * @param id identificador del producto
     * @return 200 con el producto, o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Crea un producto.
     *
     * @param request cuerpo JSON con name y price
     * @return 201 con el producto creado
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    /**
     * Actualiza un producto.
     *
     * @param id      identificador del producto
     * @param request cuerpo JSON con name y price
     * @return 200 con el producto actualizado, o 404 si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    /**
     * Elimina un producto.
     *
     * @param id identificador del producto
     * @return 204 sin contenido, o 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}