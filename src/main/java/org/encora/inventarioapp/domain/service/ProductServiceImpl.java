package org.encora.inventarioapp.domain.service;

import org.encora.inventarioapp.domain.exception.IllegalQuantityException;
import org.encora.inventarioapp.domain.model.Product;
import org.encora.inventarioapp.domain.repository.ProductRepository;
import org.encora.inventarioapp.domain.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    @Qualifier("mysql")
    private ProductRepository productRepository;

    public ProductServiceImpl() {

    }

    /**
     *
     * @param product
     * @return
     */
    @Override
    public Product add(Product product) {
        String newUUID = UUID.randomUUID().toString();
        product.setId(newUUID);

        return productRepository.add(product);
    }

    public Product updateInventory(String id, int quantity)
            throws ProductNotFoundException, IllegalQuantityException {

        Product product = productRepository.getById(id);
        if(quantity > 0) {
            product.increaseQuantity(quantity);
        } else {
            product.decreaseQuantity(quantity);
        }

        productRepository.update(product);

        return product;

    }

    @Override
    public Product getById(String id) throws ProductNotFoundException {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> getAll() {

        return productRepository.getAll();
    }
}
