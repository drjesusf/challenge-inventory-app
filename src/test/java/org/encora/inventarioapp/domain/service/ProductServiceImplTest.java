package org.encora.inventarioapp.domain.service;

import org.encora.inventarioapp.domain.exception.IllegalQuantityException;
import org.encora.inventarioapp.domain.exception.ProductNotFoundException;
import org.encora.inventarioapp.domain.model.Product;
import org.encora.inventarioapp.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private Product product;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        product = new Product();

    }

    @Test
    void whenAddProductThenReturnOk() {
        Product newProduct = new Product();
        Mockito.when(productRepository.add(newProduct)).thenReturn(newProduct);

        Product actualProduct = productService.add(newProduct);

        assertNotNull(actualProduct);
        assertNotNull(UUID.fromString(actualProduct.getId()));
    }

    @Test
    void whenUpdateInventoryAndQuantityIsCorrect()
            throws ProductNotFoundException, IllegalQuantityException {

        String id = UUID.randomUUID().toString();
        int initialQuantity = 50;
        int increasedQuantity = 50;
        product.setId(id);
        product.setQuantity(initialQuantity);
        Mockito.when(productRepository.getById(id)).thenReturn(product);
        Mockito.when(productRepository.add(any())).thenReturn(product);

        productService.add(product);
        Product updateProduct = productService.updateInventory(id,increasedQuantity);
        assertEquals(initialQuantity + increasedQuantity, updateProduct.getQuantity());
    }

    @Test
    void whenGetByIdIfIdNotExistThenShouldReturnException() throws ProductNotFoundException {
        String id = UUID.randomUUID().toString();
        Mockito.when(productRepository.getById(id)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> productService.getById(id));
    }

    @Test
    void whenGetAllThenShouldReturnListOfProducts() throws ProductNotFoundException {
        List<Product> productList = new ArrayList<Product>();
        productList.add(product);

        Mockito.when(productRepository.getAll()).thenReturn(productList);

        assertEquals(productList.size(), productService.getAll().size());

    }
}