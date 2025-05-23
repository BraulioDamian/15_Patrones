/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import DBObjetos.Producto;
import java.time.LocalDate;

/**
 *
 * @author carlosupreme
 */
public class ProductoPerecederoFactory extends ProductoFactory {
    
    @Override
    public Producto crearProducto(String nombre, int areaID, double precio, int unidades) {
        // For perishable products, we set a default expiration date of 30 days from now
        LocalDate fechaCaducidad = LocalDate.now().plusDays(30);
        
        return new Producto.ProductoBuilder()
                .nombre(nombre)
                .areaID(areaID)
                .precio(precio)
                .unidadesDisponibles(unidades)
                .fechaCaducidad(fechaCaducidad)
                .build();
    }
    
    /**
     * Crea un producto con la fecha de caducidad
     */
    public Producto crearProducto(String nombre, int areaID, double precio, int unidades, 
                                  LocalDate fechaCaducidad, String codigoBarras, String marca, String contenido) {
        return new Producto.ProductoBuilder()
                .nombre(nombre)
                .areaID(areaID)
                .precio(precio)
                .unidadesDisponibles(unidades)
                .fechaCaducidad(fechaCaducidad)
                .codigoBarras(codigoBarras)
                .marca(marca)
                .contenido(contenido)
                .build();
    }
}