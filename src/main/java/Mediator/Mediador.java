package Mediator;

import DBObjetos.Usuario;
import java.util.Map;

/**
 * Interfaz Mediador que define las operaciones de comunicación entre componentes
 */
public interface Mediador {
    
    /**
     * Notifica al mediador sobre un evento ocurrido en un componente
     * @param remitente El componente que envía la notificación
     * @param evento El tipo de evento que ocurrió
     * @param datos Datos adicionales relacionados con el evento
     */
    void notificar(Componente remitente, String evento, Map<String, Object> datos);
    
    /**
     * Registra un componente con el mediador
     * @param componente El componente a registrar
     */
    void registrarComponente(Componente componente);
    
    /**
     * Inicia una pantalla específica y establece el usuario actual
     * @param nombrePantalla El nombre de la pantalla a iniciar
     * @param usuario El usuario actualmente autenticado
     */
    void iniciarPantalla(String nombrePantalla, Usuario usuario);
}
