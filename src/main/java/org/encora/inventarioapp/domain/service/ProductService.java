package org.encora.inventarioapp.domain.service;

import org.encora.inventarioapp.domain.exception.IllegalQuantityException;
import org.encora.inventarioapp.domain.model.Product;
import org.encora.inventarioapp.domain.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    Product add(Product product);
    Product updateInventory(String id, int quantity) throws ProductNotFoundException, IllegalQuantityException;

    Product getById(String id) throws ProductNotFoundException;

    List<Product> getAll();
}
