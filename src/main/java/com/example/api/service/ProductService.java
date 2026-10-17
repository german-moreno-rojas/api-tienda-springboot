package com.example.api.service;

import com.example.api.model.Product;
import com.example.api.model.ProductRequest;
import com.example.api.model.ProductResponse;
import com.example.api.repository.ProductRepository;
import com.example.api.utils.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lógica de negocio del módulo de productos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Lista todos los productos.
     *
     * @return lista de productos como {@link ProductResponse}
     */
    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::from)
                .toList();
    }

    /**
     * Consulta un producto por su identificador.
     *
     * @param id identificador del producto
     * @return el producto encontrado
     * @throws ProductNotFoundException si el id no existe
     */
    public ProductResponse getProductById(Long id) {
        return ProductResponse.from(findOrThrow(id));
    }

    /**
     * Crea un nuevo producto.
     *
     * @param request datos del producto a crear
     * @return el producto creado con su id asignado
     */
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creando producto: {}", request.getName());
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
        return ProductResponse.from(productRepository.save(product));
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id      identificador del producto a actualizar
     * @param request nuevos datos del producto
     * @return el producto actualizado
     * @throws ProductNotFoundException si el id no existe
     */
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existing = findOrThrow(id);
        existing.setName(request.getName());
        existing.setPrice(request.getPrice());
        return ProductResponse.from(productRepository.save(existing));
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param id identificador del producto a eliminar
     * @throws ProductNotFoundException si el id no existe
     */
    public void deleteProduct(Long id) {
        productRepository.delete(findOrThrow(id));
    }

    private Product findOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No encontramos ese ID"));
    }
}