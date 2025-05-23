package Factory;

import DBObjetos.Producto;

public abstract class ProductoFactory {
    
    /**
     * Crea un producto con detalles basicos
     * @param nombre Product name
     * @param areaID Area ID where the product belongs
     * @param precio Product price
     * @param unidades Available units
     * @return A new Producto instance
     */
    public abstract Producto crearProducto(String nombre, int areaID, double precio, int unidades);
    
    /**
     * Crea la abstract factory dado el tipo de producto
     * @param tipoProducto The type of product factory to create
     * @return A concrete factory instance
     */
    public static ProductoFactory getFactory(String tipoProducto) {
        if (tipoProducto.equalsIgnoreCase("perecedero")) {
            return new ProductoPerecederoFactory();
        } else if (tipoProducto.equalsIgnoreCase("noPerecedero")) {
            return new ProductoNoPerecederoFactory();
        } else {
            throw new IllegalArgumentException("Tipo de producto no soportado: " + tipoProducto);
        }
    }
}