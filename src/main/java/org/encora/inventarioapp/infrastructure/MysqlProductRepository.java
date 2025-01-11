package org.encora.inventarioapp.infrastructure;

import org.encora.inventarioapp.domain.exception.ProductNotFoundException;
import org.encora.inventarioapp.domain.model.Product;
import org.encora.inventarioapp.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("mysql")
public class MysqlProductRepository implements ProductRepository {

    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Override
    public Product add(Product product) {
        return jpaProductRepository.save(product);
    }

    @Override
    public Product update(Product product) throws ProductNotFoundException {
        Product existingProduct = getById(product.getId());
        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());

        jpaProductRepository.save(existingProduct);

        return existingProduct;
    }

    @Override
    public Product getById(String id) throws ProductNotFoundException {
        return jpaProductRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public List<Product> getAll() {
        return jpaProductRepository.findAll();
    }
}
