package Mediator;

import java.util.Map;

/**
 * Interfaz que deben implementar todos los componentes que se comunicarán a través del mediador
 */
public interface Componente {
    
    /**
     * Establece el mediador para este componente
     * @param mediador El mediador a establecer
     */
    void setMediador(Mediador mediador);
    
    /**
     * Recibe una notificación del mediador
     * @param evento El tipo de evento recibido
     * @param datos Datos adicionales relacionados con el evento
     */
    void recibirNotificacion(String evento, Map<String, Object> datos);
    
    /**
     * Obtiene el nombre único del componente
     * @return Nombre del componente
     */
    String getNombre();
}
