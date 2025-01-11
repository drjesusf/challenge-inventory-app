package org.encora.inventarioapp.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.encora.inventarioapp.domain.exception.IllegalQuantityException;

@Getter
@Setter
@Entity(name = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private int quantity;
    private float price;

    public void increaseQuantity(int quantity) throws IllegalQuantityException {
        if (quantity < 0) throw new IllegalQuantityException("La cantidad a incrementar no debe ser menor que cero");

        this.quantity += quantity;
    }
    public void decreaseQuantity(int quantity) throws IllegalQuantityException {
        if(this.quantity < quantity) throw new IllegalQuantityException("La cantidad existente del producto es menor a la cantidad que se quiere decrementar");
        this.quantity -= quantity;
    }
}
