package org.encora.inventarioapp.infrastructure;

import org.encora.inventarioapp.domain.exception.ProductNotFoundException;
import org.encora.inventarioapp.domain.model.Product;
import org.encora.inventarioapp.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("hashmap")
public class HashMapProductRepository implements ProductRepository{
    private final ConcurrentHashMap<String, Product> concurrentHashMap;

    public HashMapProductRepository() {
        concurrentHashMap = new ConcurrentHashMap<String, Product>();
    }

    @Override
    public Product add(Product product) {
        String newUUID = UUID.randomUUID().toString();
        product.setId(newUUID);

        concurrentHashMap.put(newUUID,product);

        return product;
    }

    @Override
    public Product update(Product product) throws ProductNotFoundException {
        Product currentProduct = getById(product.getId());

        currentProduct.setName(product.getName());
        currentProduct.setQuantity(product.getQuantity());
        currentProduct.setPrice(product.getPrice());

        concurrentHashMap.remove(product.getId());
        concurrentHashMap.put(currentProduct.getId(),currentProduct);

        return currentProduct;
    }

    @Override
    public Product getById(String id) throws ProductNotFoundException {
        Product currentProduct = concurrentHashMap.get(id);
        if (currentProduct == null) throw new ProductNotFoundException("Producto no encontrado.");

        return concurrentHashMap.get(id);
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<Product>();
        concurrentHashMap.forEach((s, product) -> {
            products.add(product);
        });

        return products;
    }
}
