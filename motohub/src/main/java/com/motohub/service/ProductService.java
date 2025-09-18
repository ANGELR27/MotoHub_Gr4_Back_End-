package com.motohub.service;

import com.motohub.model.Product;
import com.motohub.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Guardar un producto
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Obtener todos los productos
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Buscar un producto por ID
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    // Actualizar un producto
    public Product update(Product product) {
        return productRepository.save(product);
    }

    // Eliminar un producto
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
