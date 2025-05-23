package Mediator;

import Mediator.InventarioMediator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Componente concreto que representa la parte de inventario del sistema
 */
public class ComponenteInventario implements Componente {
    
    private static final Logger LOGGER = Logger.getLogger(ComponenteInventario.class.getName());
    private Mediador mediador;
    private final String nombre;
    
    /**
     * Constructor del componente de inventario
     * @param nombre Identificador único para el componente
     */
    public ComponenteInventario(String nombre) {
        this.nombre = "INVENTARIO_" + nombre;
    }
    
    @Override
    public void setMediador(Mediador mediador) {
        this.mediador = mediador;
    }
    
    @Override
    public void recibirNotificacion(String evento, Map<String, Object> datos) {
        LOGGER.log(Level.INFO, "ComponenteInventario {0} recibió evento: {1}", new Object[]{nombre, evento});
        
        switch (evento) {
            case "NUEVA_VENTA":
                // Actualizar el inventario en respuesta a una venta
                if (datos.containsKey("producto_id") && datos.containsKey("cantidad")) {
                    int productoId = (int) datos.get("producto_id");
                    int cantidad = (int) datos.get("cantidad");
                    actualizarStockProducto(productoId, cantidad);
                }
                break;
                
            case "PRODUCTO_ACTUALIZADO":
                // Refrescar la vista de inventario si es necesario
                LOGGER.log(Level.INFO, "Actualizando vista de inventario por cambio en producto");
                break;
                
            // Manejar otros eventos
        }
    }
    
    /**
     * Ejemplo de método para actualizar el stock de un producto
     */
    private void actualizarStockProducto(int productoId, int cantidadVendida) {
        LOGGER.log(Level.INFO, "Actualizando stock de producto {0}, reduciendo {1} unidades", 
                new Object[]{productoId, cantidadVendida});
        
        // Lógica para actualizar el stock
        // En un escenario real, esto implicaría acceder a la base de datos
    }
    
    /**
     * Notifica sobre un cambio en el inventario
     * @param inventario El inventario actualizado
     */
    public void notificarCambioInventario(InventarioMediator inventario) {
        if (mediador != null) {
            Map<String, Object> datos = Map.of(
                "inventario_id", inventario.getId(),
                "producto_id", inventario.getProducto().getProductoID(),
                "stock_actual", inventario.getCantidad()
            );
            
            mediador.notificar(this, "ACTUALIZAR_INVENTARIO", datos);
        }
    }
    
    @Override
    public String getNombre() {
        return nombre;
    }
}
