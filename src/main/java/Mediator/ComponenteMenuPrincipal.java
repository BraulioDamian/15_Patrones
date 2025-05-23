package Mediator;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Componente concreto que representa el menú principal del sistema
 */
public class ComponenteMenuPrincipal implements Componente {
    
    private static final Logger LOGGER = Logger.getLogger(ComponenteMenuPrincipal.class.getName());
    private Mediador mediador;
    private final String nombre;
    
    /**
     * Constructor del componente del menú principal
     */
    public ComponenteMenuPrincipal() {
        this.nombre = "MENU_PRINCIPAL";
    }
    
    @Override
    public void setMediador(Mediador mediador) {
        this.mediador = mediador;
    }
    
    @Override
    public void recibirNotificacion(String evento, Map<String, Object> datos) {
        LOGGER.log(Level.INFO, "MenuPrincipal recibió evento: {0}", evento);
        
        switch (evento) {
            case "ACTUALIZAR_INVENTARIO":
                LOGGER.log(Level.INFO, "Actualizando estado de inventario en menú principal");
                // Aquí podríamos actualizar algún elemento visual del menú principal
                // como un indicador de estado, número de productos, etc.
                break;
                
            case "NUEVA_VENTA":
                LOGGER.log(Level.INFO, "Actualizando información de ventas en menú principal");
                // Aquí podríamos actualizar un contador de ventas o información similar
                break;
                
            case "NOTIFICACION":
                if (datos.containsKey("mensaje")) {
                    LOGGER.log(Level.INFO, "Notificación recibida: {0}", datos.get("mensaje"));
                    // Aquí podríamos mostrar una notificación al usuario
                    mostrarNotificacion((String) datos.get("mensaje"));
                }
                break;
        }
    }
    
    /**
     * Simula mostrar una notificación al usuario
     * @param mensaje El mensaje a mostrar
     */
    private void mostrarNotificacion(String mensaje) {
        LOGGER.log(Level.INFO, "NOTIFICACIÓN: {0}", mensaje);
        // En un caso real, aquí se mostraría una notificación visual al usuario
    }
    
    /**
     * Navega a una pantalla específica
     * @param nombrePantalla Nombre de la pantalla
     */
    public void navegarA(String nombrePantalla) {
        if (mediador != null) {
            LOGGER.log(Level.INFO, "Solicitando navegación a: {0}", nombrePantalla);
            Map<String, Object> datos = Map.of("destino", nombrePantalla);
            mediador.notificar(this, "NAVEGACION", datos);
        }
    }
    
    /**
     * Envía una notificación general a todos los componentes
     * @param mensaje El mensaje a enviar
     */
    public void enviarNotificacion(String mensaje) {
        if (mediador != null) {
            LOGGER.log(Level.INFO, "Enviando notificación: {0}", mensaje);
            Map<String, Object> datos = Map.of("mensaje", mensaje);
            mediador.notificar(this, "NOTIFICACION", datos);
        }
    }
    
    @Override
    public String getNombre() {
        return nombre;
    }
}
