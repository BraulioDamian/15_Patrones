package Integracion;

import DBObjetos.Producto;
import PatronComposite.CatalogoManager;
import PatronComposite.ProductoLeaf;
import PatronBridge.NotificacionManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase demostrativa que muestra la integración entre los patrones Bridge y Composite.
 */
public class IntegracionDemo {
    
    private static final Logger LOGGER = Logger.getLogger(IntegracionDemo.class.getName());
    
    /**
     * Método principal para probar la integración entre los patrones.
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        System.out.println("===== INTEGRACIÓN DE PATRONES BRIDGE Y COMPOSITE =====");
        
        try {
            // Obtener instancia del integrador
            CatalogoNotificacionIntegrador integrador = CatalogoNotificacionIntegrador.getInstance();
            
            // Configurar el integrador
            integrador.setContactoAdmin("administrador@tiendaabarrotes.com");
            integrador.setUmbralStockBajo(5);
            
            // Cambiar el tipo de notificación para el inventario (usando patrón Bridge)
            System.out.println("\n1. Configurando notificaciones por email:");
            integrador.cambiarTipoNotificacionInventario("email");
            
            // Obtener instancia del catálogo (usando patrón Composite)
            CatalogoManager catalogoManager = CatalogoManager.getInstance();
            
            // Mostrar productos con stock bajo
            System.out.println("\n2. Verificando productos con stock bajo:");
            int stockBajoCount = integrador.verificarYNotificarStockBajo();
            System.out.println("   Se encontraron " + stockBajoCount + " productos con stock bajo");
            
            // Mostrar valor total del inventario
            System.out.println("\n3. Notificando valor total del inventario:");
            boolean notificado = integrador.notificarValorInventario();
            System.out.println("   Valor total notificado: " + (notificado ? "Sí" : "No"));
            
            // Agregar un nuevo producto (simulación)
            System.out.println("\n4. Agregando un nuevo producto:");
            Producto nuevoProducto = new Producto.ProductoBuilder()
                    .nombre("Cereal Integral")
                    .descripcion("Cereal de trigo integral 500g")
                    .precio(32.50)
                    .unidadesDisponibles(15)
                    .areaID(2) // Establecer directamente el área ID
                    .build();
            
            // Simular la asignación de un ID al producto
            try {
                java.lang.reflect.Field field = nuevoProducto.getClass().getDeclaredField("productoID");
                field.setAccessible(true);
                field.set(nuevoProducto, 999);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "No se pudo asignar ID al producto: {0}", e.getMessage());
            }
            
            // Notificar la adición del nuevo producto
            boolean notificacionExitosa = integrador.notificarProductoAgregado(nuevoProducto, 2); // Área 2: Cereales
            System.out.println("   Notificación de nuevo producto: " + (notificacionExitosa ? "Exitosa" : "Fallida"));
            
            // Cambiar a notificaciones por SMS
            System.out.println("\n5. Cambiando a notificaciones por SMS:");
            boolean cambioExitoso = integrador.cambiarTipoNotificacionInventario("sms");
            System.out.println("   Cambio a notificaciones SMS: " + (cambioExitoso ? "Exitoso" : "Fallido"));
            
            // Notificar nuevamente el valor del inventario (ahora por SMS)
            notificado = integrador.notificarValorInventario();
            System.out.println("   Valor total notificado por SMS: " + (notificado ? "Sí" : "No"));
            
            // Mostrar estadísticas finales
            System.out.println("\n===== ESTADÍSTICAS DEL CATÁLOGO =====");
            List<ProductoLeaf> todosProductos = catalogoManager.obtenerTodosLosProductos();
            System.out.println("Total de productos: " + todosProductos.size());
            System.out.println("Valor total: $" + String.format("%.2f", catalogoManager.calcularValorTotalInventario()));
            
            // Cerrar la conexión al finalizar
            catalogoManager.cerrarConexion();
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error durante la ejecución de la demostración: {0}", e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\nLa demostración ha finalizado.");
        }
    }
}