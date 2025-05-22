package PatronBridge;

import Consultas.CONSULTASDAO;
import ConexionDB.Conexion_DB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestor de correos administrativos para el sistema de notificaciones.
 * Se encarga de obtener automáticamente los correos de los administradores
 * y facilitar el envío masivo de notificaciones.
 */
public class AdminEmailManager {
    
    private static final Logger LOGGER = Logger.getLogger(AdminEmailManager.class.getName());
    private static AdminEmailManager instance;
    private CONSULTASDAO consultasDAO;
    private List<String> correosAdminCache;
    private long ultimaActualizacion;
    private static final long TIEMPO_CACHE = 300000; // 5 minutos en milisegundos
    
    /**
     * Constructor privado para patrón Singleton
     */
    private AdminEmailManager() {
        try {
            Connection conexion = Conexion_DB.getConexion();
            consultasDAO = new CONSULTASDAO(conexion);
            correosAdminCache = new ArrayList<>();
            ultimaActualizacion = 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar AdminEmailManager", e);
        }
    }
    
    /**
     * Obtiene la instancia única de AdminEmailManager (patrón Singleton)
     * 
     * @return Instancia única de AdminEmailManager
     */
    public static synchronized AdminEmailManager getInstance() {
        if (instance == null) {
            instance = new AdminEmailManager();
        }
        return instance;
    }
    
    /**
     * Obtiene la lista de correos de administradores.
     * Utiliza caché para evitar consultas frecuentes a la base de datos.
     * 
     * @return Lista de correos de administradores
     */
    public List<String> obtenerCorreosAdministradores() {
        return obtenerCorreosAdministradores(false);
    }
    
    /**
     * Obtiene la lista de correos de administradores.
     * 
     * @param forzarActualizacion Si es true, fuerza la actualización del caché
     * @return Lista de correos de administradores
     */
    public List<String> obtenerCorreosAdministradores(boolean forzarActualizacion) {
        long tiempoActual = System.currentTimeMillis();
        
        // Verificar si necesita actualizar el caché
        if (forzarActualizacion || 
            correosAdminCache.isEmpty() || 
            (tiempoActual - ultimaActualizacion) > TIEMPO_CACHE) {
            
            actualizarCacheCorreos();
        }
        
        return new ArrayList<>(correosAdminCache);
    }
    
    /**
     * Obtiene correos de usuarios con roles administrativos (ADMINISTRADOR, GERENTE, SUPERVISOR)
     * 
     * @return Lista de correos de usuarios con roles administrativos
     */
    public List<String> obtenerCorreosRolesAdministrativos() {
        try {
            return consultasDAO.obtenerCorreosRolesAdministrativos();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener correos de roles administrativos", e);
            return new ArrayList<>();
        }
    }
    
    /**
     * Actualiza el caché de correos de administradores
     */
    private void actualizarCacheCorreos() {
        try {
            List<String> nuevosCorreos = consultasDAO.obtenerCorreosAdministradores();
            correosAdminCache.clear();
            correosAdminCache.addAll(nuevosCorreos);
            ultimaActualizacion = System.currentTimeMillis();
            
            LOGGER.log(Level.INFO, "Caché de correos actualizado. Total: {0} administradores", 
                      correosAdminCache.size());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el caché de correos", e);
        }
    }
    
    /**
     * Envía una notificación a todos los administradores utilizando el sistema de notificaciones
     * 
     * @param mensaje Mensaje a enviar
     * @param tipoNotificacion Tipo de notificación (sistema, inventario, venta)
     * @return Número de correos enviados exitosamente
     */
    public int enviarNotificacionATodosLosAdmins(String mensaje, String tipoNotificacion) {
        List<String> correos = obtenerCorreosAdministradores();
        return enviarNotificacionACorreos(correos, mensaje, tipoNotificacion);
    }
    
    /**
     * Envía una notificación a usuarios con roles administrativos
     * 
     * @param mensaje Mensaje a enviar
     * @param tipoNotificacion Tipo de notificación (sistema, inventario, venta)
     * @return Número de correos enviados exitosamente
     */
    public int enviarNotificacionARolesAdministrativos(String mensaje, String tipoNotificacion) {
        List<String> correos = obtenerCorreosRolesAdministrativos();
        return enviarNotificacionACorreos(correos, mensaje, tipoNotificacion);
    }
    
    /**
     * Envía notificación a una lista específica de correos
     * 
     * @param correos Lista de correos destinatarios
     * @param mensaje Mensaje a enviar
     * @param tipoNotificacion Tipo de notificación
     * @return Número de correos enviados exitosamente
     */
    private int enviarNotificacionACorreos(List<String> correos, String mensaje, String tipoNotificacion) {
        if (correos.isEmpty()) {
            LOGGER.log(Level.WARNING, "No hay correos de administradores disponibles");
            return 0;
        }
        
        NotificacionManager notificationManager = NotificacionManager.getInstance();
        int enviosExitosos = 0;
        
        for (String correo : correos) {
            try {
                boolean exito = false;
                
                switch (tipoNotificacion.toLowerCase()) {
                    case "sistema":
                        exito = notificationManager.getNotificacionSistema().enviar(correo, mensaje);
                        break;
                    case "inventario":
                        exito = notificationManager.getNotificacionInventario().enviar(correo, mensaje);
                        break;
                    case "venta":
                        exito = notificationManager.getNotificacionVenta().enviar(correo, mensaje);
                        break;
                    default:
                        LOGGER.log(Level.WARNING, "Tipo de notificación no válido: {0}", tipoNotificacion);
                        continue;
                }
                
                if (exito) {
                    enviosExitosos++;
                    LOGGER.log(Level.INFO, "Notificación enviada exitosamente a: {0}", correo);
                } else {
                    LOGGER.log(Level.WARNING, "Error al enviar notificación a: {0}", correo);
                }
                
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Excepción al enviar notificación a: " + correo, e);
            }
        }
        
        LOGGER.log(Level.INFO, "Resumen de envío: {0}/{1} correos enviados exitosamente", 
                  new Object[]{enviosExitosos, correos.size()});
        
        return enviosExitosos;
    }
    
    /**
     * Envía notificación específica de stock bajo a administradores
     * 
     * @param productoID ID del producto
     * @param nombreProducto Nombre del producto
     * @param stockActual Stock actual
     * @param stockMinimo Stock mínimo
     * @return Número de correos enviados exitosamente
     */
    public int notificarStockBajoAAdministradores(int productoID, String nombreProducto, 
                                                  int stockActual, int stockMinimo) {
        List<String> correos = obtenerCorreosAdministradores();
        NotificacionManager notificationManager = NotificacionManager.getInstance();
        int enviosExitosos = 0;
        
        for (String correo : correos) {
            try {
                boolean exito = notificationManager.getNotificacionInventario()
                    .notificarStockBajo(correo, productoID, nombreProducto, stockActual, stockMinimo);
                
                if (exito) {
                    enviosExitosos++;
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al notificar stock bajo a: " + correo, e);
            }
        }
        
        return enviosExitosos;
    }
    
    /**
     * Envía notificación de nuevo producto a administradores
     * 
     * @param productoID ID del producto
     * @param nombreProducto Nombre del producto
     * @param cantidad Cantidad recibida
     * @param fecha Fecha de recepción
     * @return Número de correos enviados exitosamente
     */
    public int notificarNuevoProductoAAdministradores(int productoID, String nombreProducto, 
                                                      int cantidad, String fecha) {
        List<String> correos = obtenerCorreosAdministradores();
        NotificacionManager notificationManager = NotificacionManager.getInstance();
        int enviosExitosos = 0;
        
        for (String correo : correos) {
            try {
                boolean exito = notificationManager.getNotificacionInventario()
                    .notificarNuevoProducto(correo, productoID, nombreProducto, cantidad, fecha);
                
                if (exito) {
                    enviosExitosos++;
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al notificar nuevo producto a: " + correo, e);
            }
        }
        
        return enviosExitosos;
    }
    
    /**
     * Obtiene información sobre el estado del gestor de correos
     * 
     * @return String con información del estado
     */
    public String obtenerEstadoSistema() {
        StringBuilder estado = new StringBuilder();
        estado.append("=== Estado del Gestor de Correos Administrativos ===\\n");
        estado.append("Correos en caché: ").append(correosAdminCache.size()).append("\\n");
        estado.append("Última actualización: ");
        
        if (ultimaActualizacion > 0) {
            long tiempoTranscurrido = (System.currentTimeMillis() - ultimaActualizacion) / 1000;
            estado.append(tiempoTranscurrido).append(" segundos atrás\\n");
        } else {
            estado.append("Nunca\\n");
        }
        
        estado.append("Correos registrados:\\n");
        for (String correo : correosAdminCache) {
            estado.append("  - ").append(correo).append("\\n");
        }
        
        return estado.toString();
    }
    
    /**
     * Limpia el caché y fuerza una nueva consulta en el próximo acceso
     */
    public void limpiarCache() {
        correosAdminCache.clear();
        ultimaActualizacion = 0;
        LOGGER.log(Level.INFO, "Caché de correos limpiado");
    }
}
