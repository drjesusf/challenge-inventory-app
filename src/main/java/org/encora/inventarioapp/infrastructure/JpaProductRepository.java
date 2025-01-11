package org.encora.inventarioapp.infrastructure;

import org.encora.inventarioapp.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, String> {
}
