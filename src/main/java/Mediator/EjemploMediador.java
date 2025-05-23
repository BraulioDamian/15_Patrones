package Mediator;

import login.SesionManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de ejemplo que muestra cómo utilizar el patrón mediador en la aplicación
 */
public class EjemploMediador {
    
    private static final Logger LOGGER = Logger.getLogger(EjemploMediador.class.getName());
    
    /**
     * Inicializa el sistema de mediador conectando los componentes
     */
    public static void inicializarMediador() {
        LOGGER.log(Level.INFO, "Inicializando sistema de mediador");
        
        // Obtener la instancia del mediador
        MediadorConcreto mediador = MediadorConcreto.getInstance();
        
        // Crear y registrar componentes
        ComponenteInventario inventario = new ComponenteInventario("Principal");
        ComponenteVenta venta = new ComponenteVenta("Principal");
        
        // Registrar con el mediador
        mediador.registrarComponente(inventario);
        mediador.registrarComponente(venta);
        
        LOGGER.log(Level.INFO, "Sistema de mediador inicializado correctamente");
    }
    
    /**
     * Ejemplo de navegación entre pantallas usando el mediador
     * @param nombrePantalla Nombre de la pantalla a mostrar
     */
    public static void navegarAPantalla(String nombrePantalla) {
        MediadorConcreto mediador = MediadorConcreto.getInstance();
        mediador.iniciarPantalla(nombrePantalla, SesionManager.getInstance().getUsuarioLogueado());
    }
    
}
