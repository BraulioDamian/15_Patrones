package Mediator;

import DBObjetos.DetalleVenta;
import DBObjetos.Venta;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Componente concreto que representa la parte de ventas del sistema
 */
public class ComponenteVenta implements Componente {
    
    private static final Logger LOGGER = Logger.getLogger(ComponenteVenta.class.getName());
    private Mediador mediador;
    private final String nombre;
    
    /**
     * Constructor del componente de ventas
     * @param nombre Identificador único para el componente
     */
    public ComponenteVenta(String nombre) {
        this.nombre = "VENTA_" + nombre;
    }
    
    @Override
    public void setMediador(Mediador mediador) {
        this.mediador = mediador;
    }
    
    @Override
    public void recibirNotificacion(String evento, Map<String, Object> datos) {
        LOGGER.log(Level.INFO, "ComponenteVenta {0} recibió evento: {1}", new Object[]{nombre, evento});
        
        switch (evento) {
            case "ACTUALIZAR_INVENTARIO":
                // Si se actualizó el inventario, verificar si afecta a productos en venta
                if (datos.containsKey("producto_id") && datos.containsKey("stock_actual")) {
                    int productoId = (int) datos.get("producto_id");
                    int stockActual = (int) datos.get("stock_actual");
                    verificarDisponibilidadProducto(productoId, stockActual);
                }
                break;
                
            case "USUARIO_CAMBIO_ROL":
                // Si cambia el rol de un usuario, podría afectar permisos de venta
                LOGGER.log(Level.INFO, "Actualizando permisos de venta por cambio de rol de usuario");
                break;
                
            // Manejar otros eventos relevantes para ventas
        }
    }
    
    /**
     * Verifica si un producto tiene suficiente stock disponible
     */
    private void verificarDisponibilidadProducto(int productoId, int stockActual) {
        LOGGER.log(Level.INFO, "Verificando disponibilidad de producto {0}, stock actual: {1}", 
                new Object[]{productoId, stockActual});
        
        if (stockActual <= 5) {
            LOGGER.log(Level.WARNING, "Producto {0} con stock bajo: {1} unidades", 
                    new Object[]{productoId, stockActual});
        }
    }
    
    /**
     * Notifica sobre una nueva venta
     * @param venta La venta realizada
     * @param detalles Los detalles de la venta
     */
    public void notificarNuevaVenta(Venta venta, DetalleVenta[] detalles) {
        if (mediador != null) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("venta_id", venta.getVentaID());
            datos.put("empleado_id", venta.getUsuarioID());
            datos.put("fecha", venta.getFechaVenta());
            datos.put("total", venta.getPrecioTotal());
            
            // Añadir detalles de productos vendidos
            for (int i = 0; i < detalles.length; i++) {
                DetalleVenta detalle = detalles[i];
                datos.put("producto_id_" + i, detalle.getProductoID());
                datos.put("cantidad_" + i, detalle.getCantidad());
                datos.put("precio_" + i, detalle.getPrecioUnitario());
            }
            
            mediador.notificar(this, "NUEVA_VENTA", datos);
        }
    }
    
    @Override
    public String getNombre() {
        return nombre;
    }
}
