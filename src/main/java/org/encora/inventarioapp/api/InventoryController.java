package org.encora.inventarioapp.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.encora.inventarioapp.api.request.UpdateInventoryRequest;
import org.encora.inventarioapp.domain.exception.IllegalQuantityException;
import org.encora.inventarioapp.domain.model.Product;
import org.encora.inventarioapp.domain.service.ProductServiceImpl;
import org.encora.inventarioapp.domain.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API Controller que permite realizar operaciones sonre
 * el inventario de productos.
 * Las excepciones de la aplicacion son manejadas en dos
 * metodos del controlador handleException
 * @author Jesus Ramirez
 */
@RestController
@RequestMapping("api/products")
public class InventoryController {
    private final ProductServiceImpl productService;

    public InventoryController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    /**
    *
    * @param product el objeto de producto que se desea crear.
    * Debe contener todos los detalles necesarios del producto.
    * @return un {@link ResponseEntity} que contiene un mensaje de éxito
    * y el código de estado HTTP {@code 201 Created}.
    * @throws IllegalArgumentException si los datos del producto son inválidos.
    */
    @Operation(summary = "Agregar un producto", description = "Crea un nuevo producto en el inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    @PostMapping()
    public ResponseEntity<String> addProduct(@RequestBody Product product) {

        Product newProduct = productService.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado exitosamente");

    }

    /**
     *
     * @param id
     * @param inventoryRequest
     * @return un {@link ResponseEntity} que contiene el producto actualizado
     * y el código de estado HTTP {@code 200 OK}.
     * @throws ProductNotFoundException
     * @throws IllegalQuantityException
     */
    @Operation(summary = "Actualizar inventario de un producto", description = "Actualiza la cantidad de un producto en el inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Cantidad inválida"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PatchMapping("{id}")
    public  ResponseEntity<Product> updateInventory (
            @PathVariable("id") String id,
            @RequestBody UpdateInventoryRequest inventoryRequest)
            throws ProductNotFoundException, IllegalQuantityException {

        Product updateProduct = productService.updateInventory(id, inventoryRequest.getQuantity());

        return  ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    /**
     *
     * @param id
     * @return un {@link ResponseEntity} que contiene los detalles del producto
     * y el código de estado HTTP {@code 200 OK}.
     * @throws ProductNotFoundException
     */
    @Operation(summary = "Obtener un producto por su ID", description = "Recupera los detalles de un producto utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("{id}")
    public  ResponseEntity<Product> getProductById (
            @PathVariable("id") String id) throws ProductNotFoundException {

        Product product = productService.getById(id);

        return  ResponseEntity.status(HttpStatus.OK).body(product);
    }

    /**
     * Obtiene la lista de todos los productos agregados a la Base de datos
     * @return un {@link ResponseEntity} que contiene la lista de productos
     * y el código de estado HTTP {@code 200 OK}.
     */
    @Operation(summary = "Obtener todos los productos", description = "Recupera todos los productos del inventario")
    @ApiResponse(responseCode = "200", description = "Lista de productos recuperada exitosamente")

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts (){
        List<Product> products = productService.getAll();

        return  ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @ExceptionHandler(IllegalQuantityException.class)
    public ResponseEntity<String> handleException (IllegalQuantityException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public  ResponseEntity<String> handleException (ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
