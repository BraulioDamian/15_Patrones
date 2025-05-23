/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import DBObjetos.Producto;

/**
 *
 * @author carlosupreme
 */
public class ProductoNoPerecederoFactory extends ProductoFactory {
    
    @Override
    public Producto crearProducto(String nombre, int areaID, double precio, int unidades) {
        // Non-perishable products don't have an expiration date
        return new Producto.ProductoBuilder()
                .nombre(nombre)
                .areaID(areaID)
                .precio(precio)
                .unidadesDisponibles(unidades)
                .build();
    }
    
    /**
     * Crea un producto sin fecha de caducidad
     * @param nombre
     * @param areaID
     * @param precio
     * @param unidades
     * @param codigoBarras
     * @param marca
     * @param contenido
     * @return 
     */
    public Producto crearProducto(String nombre, int areaID, double precio, int unidades, 
                                 String codigoBarras, String marca, String contenido) {
        return new Producto.ProductoBuilder()
                .nombre(nombre)
                .areaID(areaID)
                .precio(precio)
                .unidadesDisponibles(unidades)
                .codigoBarras(codigoBarras)
                .marca(marca)
                .contenido(contenido)
                .build();
    }
}