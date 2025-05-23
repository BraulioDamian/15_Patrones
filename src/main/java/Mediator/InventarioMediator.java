package Mediator;

import DBObjetos.Producto;

/**
 * Clase de inventario específica para el patrón Mediator
 * Esta clase actúa como adaptador entre el sistema Mediator y la clase DBObjetos.Inventario
 */
public class InventarioMediator {
    private int id;
    private Producto producto;
    private int cantidad;
    
    public InventarioMediator() {
    }
    
    public InventarioMediator(int id, Producto producto, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString() {
        return "InventarioMediator{" +
                "id=" + id +
                ", producto=" + (producto != null ? producto.getNombre() : "null") +
                ", cantidad=" + cantidad +
                '}';
    }
}
