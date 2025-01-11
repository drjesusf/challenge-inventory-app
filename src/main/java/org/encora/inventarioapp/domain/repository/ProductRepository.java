package org.encora.inventarioapp.domain.repository;

import org.encora.inventarioapp.domain.model.Product;
import org.encora.inventarioapp.domain.exception.ProductNotFoundException;

import java.util.List;

public interface ProductRepository {
    Product add(Product product);
    Product update(Product product) throws ProductNotFoundException;
    Product getById(String id) throws ProductNotFoundException;
    List<Product> getAll();
}
