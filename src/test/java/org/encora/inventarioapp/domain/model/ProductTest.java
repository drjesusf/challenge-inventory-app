package org.encora.inventarioapp.domain.model;

import org.encora.inventarioapp.domain.exception.IllegalQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;

    @BeforeEach
    public void setUpBeforeClass() {
        product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName("Test");
        product.setPrice(50);
        product.setQuantity(100);
    }

    @Test
    public void whenIncreaseQuantityAndValueIsNegativeShouldException() {

        product.setQuantity(10);

        assertThrows(IllegalQuantityException.class, () -> product.increaseQuantity(-1));

    }

    @Test
    public void whenIncreaseQuantityAndValueIsCorrectShouldFinishOk() throws IllegalQuantityException {
        int increaseQuantity = 10;
        int initialQuantity = 5;
        product.setQuantity(initialQuantity);

        product.increaseQuantity(increaseQuantity);

        assertEquals(initialQuantity + increaseQuantity, product.getQuantity());

    }
    @Test
    void whenDecreaseQuantityAndActualValueIsInsufficientShouldThrowException() {
        int decreaseQuantity = 10;
        int initialQuantity = 5;

        product.setQuantity(initialQuantity);

        assertThrows(IllegalQuantityException.class, () -> product.decreaseQuantity(decreaseQuantity));
    }

    @Test
    void whenDecreaseQuantityAndActualValueIsCorrectShouldFinishOk() throws IllegalQuantityException {
        int decreaseQuantity = 10;
        int initialQuantity = 50;
        product.setQuantity(initialQuantity);

        product.decreaseQuantity(decreaseQuantity);

        assertEquals(initialQuantity - decreaseQuantity, product.getQuantity());
    }
}