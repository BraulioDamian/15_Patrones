package Mediator;

import Mediator.InventarioMediator;
import DBObjetos.Producto;
import DBObjetos.Usuario;
import Principal.MenuPrincipal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import login.SesionManager;

/**
 * Clase para demostrar el funcionamiento del patrón mediador
 */
public class DemoMediador {
    
    private static final Logger LOGGER = Logger.getLogger(DemoMediador.class.getName());
    
    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "Iniciando demostración del patrón mediador");
        
        // Crear el mediador
        MediadorConcreto mediador = MediadorConcreto.getInstance();
        
        // Crear componentes
        ComponenteInventario inventario = new ComponenteInventario("Demo");
        ComponenteVenta venta = new ComponenteVenta("Demo");
        ComponenteMenuPrincipal menuPrincipal = new ComponenteMenuPrincipal();
        
        // Registrar componentes con el mediador
        mediador.registrarComponente(inventario);
        mediador.registrarComponente(venta);
        mediador.registrarComponente(menuPrincipal);
        
        // Demostrar comunicación del menú principal
        menuPrincipal.enviarNotificacion("Bienvenido a la demostración del patrón mediador");
        
        LOGGER.log(Level.INFO, "Componentes registrados correctamente");
        
        // Simular una actualización de inventario usando Builder pattern
        Producto producto = new Producto.ProductoBuilder()
            .productoID(1)
            .nombre("Producto de prueba")
            .build();
        
        InventarioMediator inv = new InventarioMediator();
        inv.setId(1);
        inv.setProducto(producto);
        inv.setCantidad(10);
        
        LOGGER.log(Level.INFO, "Simulando actualización de inventario para producto: {0}", producto.getNombre());
        inventario.notificarCambioInventario(inv);
        
        // La salida en consola mostrará el flujo de comunicación entre los componentes a través del mediador
        
        // Para ver cómo funciona en la aplicación real, debes iniciar sesión en la aplicación normalmente
        LOGGER.log(Level.INFO, "Para probar con la aplicación real, inicia sesión en la aplicación normalmente");
    }
    
    /**
     * Este método puede ser usado para integrar el mediador en la aplicación actual
     * @param usuario El usuario que ha iniciado sesión
     */
    public static void iniciarConMediador(Usuario usuario) {
        if (usuario == null) {
            LOGGER.log(Level.SEVERE, "No se puede iniciar con mediador: usuario es null");
            return;
        }
        
        LOGGER.log(Level.INFO, "Iniciando aplicación con mediador para usuario: {0}", usuario.getNombreUsuario());
        
        // Crear y configurar el mediador
        MediadorConcreto mediador = MediadorConcreto.getInstance();
        
        // Crear componentes
        ComponenteInventario inventario = new ComponenteInventario("Principal");
        ComponenteVenta venta = new ComponenteVenta("Principal");
        ComponenteMenuPrincipal menuPrincipal = new ComponenteMenuPrincipal();
        
        // Registrar componentes con el mediador
        mediador.registrarComponente(inventario);
        mediador.registrarComponente(venta);
        mediador.registrarComponente(menuPrincipal);
        
        // Mostrar notificación para demostrar el funcionamiento del mediador
        menuPrincipal.enviarNotificacion("Sistema iniciado con patrón mediador");
        
        // Mostrar mensaje informativo
        JOptionPane.showMessageDialog(
            null,
            "La aplicación se ha iniciado con el patrón mediador.\n" +
            "Ahora los componentes se comunican a través del mediador.\n" +
            "Revisa la consola para ver los mensajes de log del mediador.",
            "Modo Mediador Activo",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Almacenar el usuario en la sesión
        SesionManager.getInstance().login(usuario);
        
        // Iniciar la pantalla principal a través del mediador
        mediador.iniciarPantalla("MENU_PRINCIPAL", usuario);
        
        // Simulación de cambio en inventario (para demostración)
        simularCambioInventario(inventario);
    }
    
    /**
     * Simula un cambio en el inventario para demostrar el funcionamiento del mediador
     * @param inventario El componente de inventario
     */
    private static void simularCambioInventario(ComponenteInventario inventario) {
        // Simular un cambio después de 5 segundos (para que dé tiempo a que la interfaz esté lista)
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                
                // Crear un producto y un inventario para la simulación usando Builder pattern
                Producto producto = new Producto.ProductoBuilder()
                    .productoID(1)
                    .nombre("Producto Demo")
                    .build();
                
                InventarioMediator inv = new InventarioMediator();
                inv.setId(1);
                inv.setProducto(producto);
                inv.setCantidad(5);
                
                LOGGER.log(Level.INFO, "Simulando cambio en inventario...");
                inventario.notificarCambioInventario(inv);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.log(Level.SEVERE, "Error en la simulación", e);
            }
        }).start();
    }
}
