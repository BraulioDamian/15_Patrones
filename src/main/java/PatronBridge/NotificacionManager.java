package PatronBridge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestor de notificaciones que utiliza el patrón Bridge.
 * Actúa como una fachada para el sistema de notificaciones.
 * Incluye funcionalidad automática para enviar notificaciones a administradores.
 */
public class NotificacionManager {
    
    private static final Logger LOGGER = Logger.getLogger(NotificacionManager.class.getName());
    private static NotificacionManager instance;
    private Map<String, NotificacionImplementor> implementadores;
    private Map<String, Notificacion> notificaciones;
    private AdminEmailManager adminEmailManager;
    private boolean envioAutomaticoAdmins;
    
    /**
     * Constructor privado para Singleton
     */
    private NotificacionManager() {
        // Inicializar implementadores disponibles
        implementadores = new HashMap<>();
        implementadores.put("email", new EmailNotificacion());
        implementadores.put("sms", new SMSNotificacion());
        implementadores.put("alerta", new AlertaInternaNotiticacion());
        
        // Inicializar notificaciones con implementadores por defecto
        notificaciones = new HashMap<>();
        notificaciones.put("sistema", new NotificacionSistema(implementadores.get("email")));
        notificaciones.put("inventario", new NotificacionInventario(implementadores.get("email")));
        notificaciones.put("venta", new NotificacionVenta(implementadores.get("email")));
        
        // Inicializar gestor de correos administrativos
        adminEmailManager = AdminEmailManager.getInstance();
        envioAutomaticoAdmins = true; // Por defecto activado
        
        LOGGER.log(Level.INFO, "NotificacionManager inicializado con envío automático a administradores");
    }
    
    /**
     * Obtiene la instancia única de NotificacionManager (patrón Singleton)
     * 
     * @return Instancia única de NotificacionManager
     */
    public static synchronized NotificacionManager getInstance() {
        if (instance == null) {
            instance = new NotificacionManager();
        }
        return instance;
    }
    
    /**
     * Cambia el implementador para un tipo de notificación.
     * 
     * @param tipoNotificacion Tipo de notificación (sistema, inventario, venta)
     * @param implementador Tipo de implementador (email, sms, alerta)
     * @return true si se cambió correctamente, false en caso contrario
     */
    public boolean cambiarImplementador(String tipoNotificacion, String implementador) {
        if (!notificaciones.containsKey(tipoNotificacion)) {
            System.err.println("Tipo de notificación no válido: " + tipoNotificacion);
            return false;
        }
        
        if (!implementadores.containsKey(implementador)) {
            System.err.println("Implementador no válido: " + implementador);
            return false;
        }
        
        notificaciones.get(tipoNotificacion).setImplementor(implementadores.get(implementador));
        return true;
    }
    
    /**
     * Obtiene la notificación de sistema.
     * 
     * @return Notificación de sistema
     */
    public NotificacionSistema getNotificacionSistema() {
        return (NotificacionSistema) notificaciones.get("sistema");
    }
    
    /**
     * Obtiene la notificación de inventario.
     * 
     * @return Notificación de inventario
     */
    public NotificacionInventario getNotificacionInventario() {
        return (NotificacionInventario) notificaciones.get("inventario");
    }
    
    /**
     * Obtiene la notificación de venta.
     * 
     * @return Notificación de venta
     */
    public NotificacionVenta getNotificacionVenta() {
        return (NotificacionVenta) notificaciones.get("venta");
    }
    
    /**
     * Activa o desactiva el envío automático a administradores
     * 
     * @param activar true para activar, false para desactivar
     */
    public void setEnvioAutomaticoAdministradores(boolean activar) {
        this.envioAutomaticoAdmins = activar;
        LOGGER.log(Level.INFO, "Envío automático a administradores: {0}", 
                  activar ? "ACTIVADO" : "DESACTIVADO");
    }
    
    /**
     * Verifica si el envío automático a administradores está activado
     * 
     * @return true si está activado, false en caso contrario
     */
    public boolean isEnvioAutomaticoAdministradores() {
        return envioAutomaticoAdmins;
    }
    
    /**
     * Envía una notificación a administradores automáticamente si está habilitado
     * 
     * @param mensaje Mensaje a enviar
     * @param tipoNotificacion Tipo de notificación
     * @return Número de administradores notificados
     */
    private int notificarAdministradoresAutomaticamente(String mensaje, String tipoNotificacion) {
        if (!envioAutomaticoAdmins) {
            return 0;
        }
        
        try {
            int enviados = adminEmailManager.enviarNotificacionATodosLosAdmins(mensaje, tipoNotificacion);
            LOGGER.log(Level.INFO, "Notificación automática enviada a {0} administradores", enviados);
            return enviados;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en envío automático a administradores", e);
            return 0;
        }
    }
    
    /**
     * Envía notificación de stock bajo a administradores automáticamente
     * 
     * @param productoID ID del producto
     * @param nombreProducto Nombre del producto
     * @param stockActual Stock actual
     * @param stockMinimo Stock mínimo
     * @return Número de administradores notificados
     */
    public int notificarStockBajoAAdministradores(int productoID, String nombreProducto, 
                                                   int stockActual, int stockMinimo) {
        if (!envioAutomaticoAdmins) {
            return 0;
        }
        
        try {
            int enviados = adminEmailManager.notificarStockBajoAAdministradores(
                productoID, nombreProducto, stockActual, stockMinimo);
            LOGGER.log(Level.INFO, "Alerta de stock bajo enviada a {0} administradores", enviados);
            return enviados;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al notificar stock bajo a administradores", e);
            return 0;
        }
    }
    
    /**
     * Envía notificación de nuevo producto a administradores automáticamente
     * 
     * @param productoID ID del producto
     * @param nombreProducto Nombre del producto
     * @param cantidad Cantidad recibida
     * @param fecha Fecha de recepción
     * @return Número de administradores notificados
     */
    public int notificarNuevoProductoAAdministradores(int productoID, String nombreProducto, 
                                                       int cantidad, String fecha) {
        if (!envioAutomaticoAdmins) {
            return 0;
        }
        
        try {
            int enviados = adminEmailManager.notificarNuevoProductoAAdministradores(
                productoID, nombreProducto, cantidad, fecha);
            LOGGER.log(Level.INFO, "Notificación de nuevo producto enviada a {0} administradores", enviados);
            return enviados;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al notificar nuevo producto a administradores", e);
            return 0;
        }
    }
    
    /**
     * Obtiene la lista de correos de administradores registrados
     * 
     * @return Lista de correos de administradores
     */
    public List<String> obtenerCorreosAdministradores() {
        return adminEmailManager.obtenerCorreosAdministradores();
    }
    
    /**
     * Obtiene el estado del sistema de correos administrativos
     * 
     * @return String con el estado del sistema
     */
    public String obtenerEstadoSistemaAdministradores() {
        return adminEmailManager.obtenerEstadoSistema();
    }
    
    /**
     * Envía una notificación de prueba para verificar el funcionamiento.
     * 
     * @param tipoNotificacion Tipo de notificación a probar
     * @param destinatario Destinatario de la notificación
     * @return true si se envió correctamente, false en caso contrario
     */
    public boolean enviarNotificacionPrueba(String tipoNotificacion, String destinatario) {
        if (!notificaciones.containsKey(tipoNotificacion)) {
            System.err.println("Tipo de notificación no válido: " + tipoNotificacion);
            return false;
        }
        
        Notificacion notificacion = notificaciones.get(tipoNotificacion);
        String mensaje = "Esta es una notificación de prueba. Si la recibe, el sistema de notificaciones está funcionando correctamente.";
        
        boolean exitoDestinatario = notificacion.enviar(destinatario, mensaje);
        
        // Enviar automáticamente a administradores si está habilitado
        if (exitoDestinatario) {
            notificarAdministradoresAutomaticamente("[PRUEBA] " + mensaje, tipoNotificacion);
        }
        
        return exitoDestinatario;
    }
    
    /**
     * Registra un nuevo implementador en el sistema.
     * 
     * @param nombre Nombre del implementador
     * @param implementador Implementador a registrar
     */
    public void registrarImplementador(String nombre, NotificacionImplementor implementador) {
        implementadores.put(nombre, implementador);
    }
    
    /**
     * Obtiene los tipos de implementadores disponibles.
     * 
     * @return Array con los nombres de los implementadores
     */
    public String[] getImplementadoresDisponibles() {
        return implementadores.keySet().toArray(new String[0]);
    }
    
    /**
     * Obtiene los tipos de notificaciones disponibles.
     * 
     * @return Array con los nombres de las notificaciones
     */
    public String[] getNotificacionesDisponibles() {
        return notificaciones.keySet().toArray(new String[0]);
    }
}