package Integracion;

import PatronBridge.NotificacionManager;
import PatronBridge.NotificacionInventario;
import PatronComposite.CatalogoManager;
import PatronComposite.ProductoLeaf;
import DBObjetos.Producto;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase integradora que conecta el patrón Bridge (Notificaciones) con el patrón Composite (Catálogo).
 * Permite enviar notificaciones basadas en eventos del catálogo de productos.
 */
public class CatalogoNotificacionIntegrador {
    
    private static final Logger LOGGER = Logger.getLogger(CatalogoNotificacionIntegrador.class.getName());
    private static CatalogoNotificacionIntegrador instance;
    
    private final CatalogoManager catalogoManager;
    private final NotificacionManager notificacionManager;
    private boolean notificacionesActivas;
    private String contactoAdmin;
    private int umbralStockBajo;
    
    /**
     * Constructor privado para implementar Singleton.
     */
    private CatalogoNotificacionIntegrador() {
        catalogoManager = CatalogoManager.getInstance();
        notificacionManager = NotificacionManager.getInstance();
        notificacionesActivas = true;
        contactoAdmin = "admin@tienda.com"; // Valor por defecto
        umbralStockBajo = 10; // Valor por defecto
    }
    
    /**
     * Obtiene la instancia única de CatalogoNotificacionIntegrador (patrón Singleton).
     * 
     * @return Instancia única de CatalogoNotificacionIntegrador
     */
    public static synchronized CatalogoNotificacionIntegrador getInstance() {
        if (instance == null) {
            instance = new CatalogoNotificacionIntegrador();
        }
        return instance;
    }
    
    /**
     * Activa o desactiva el envío de notificaciones.
     * 
     * @param activas true para activar, false para desactivar
     */
    public void setNotificacionesActivas(boolean activas) {
        this.notificacionesActivas = activas;
        LOGGER.log(Level.INFO, "Notificaciones {0}", activas ? "activadas" : "desactivadas");
    }
    
    /**
     * Establece el contacto del administrador para notificaciones.
     * 
     * @param contacto Email o número de teléfono del administrador
     */
    public void setContactoAdmin(String contacto) {
        this.contactoAdmin = contacto;
        LOGGER.log(Level.INFO, "Contacto de administrador actualizado: {0}", contacto);
    }
    
    /**
     * Establece el umbral para notificar stock bajo.
     * 
     * @param umbral Cantidad mínima de unidades antes de notificar
     */
    public void setUmbralStockBajo(int umbral) {
        this.umbralStockBajo = umbral;
        LOGGER.log(Level.INFO, "Umbral de stock bajo actualizado: {0}", umbral);
    }
    
    /**
     * Obtiene el umbral configurado para stock bajo.
     * 
     * @return Umbral de stock bajo
     */
    public int getUmbralStockBajo() {
        return this.umbralStockBajo;
    }
    
    /**
     * Notifica cuando un producto ha sido agregado al catálogo.
     * 
     * @param producto Producto agregado
     * @param areaId ID del área a la que se agregó
     * @return true si la notificación se envió correctamente
     */
    public boolean notificarProductoAgregado(Producto producto, int areaId) {
        if (!notificacionesActivas) {
            return false;
        }
        
        try {
            String mensaje = String.format("Nuevo producto agregado:\nID: %d\nNombre: %s\nÁrea: %d\nPrecio: $%.2f\nUnidades: %d",
                    producto.getProductoID(), producto.getNombre(), areaId, 
                    producto.getPrecio(), producto.getUnidadesDisponibles());
            
            return notificacionManager.getNotificacionInventario().enviar(contactoAdmin, mensaje);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al notificar producto agregado", e);
            return false;
        }
    }
    
    /**
     * Verifica y notifica sobre productos con stock bajo.
     * 
     * @return Número de productos con stock bajo encontrados
     */
    public int verificarYNotificarStockBajo() {
        if (!notificacionesActivas) {
            return 0;
        }
        
        try {
            List<ProductoLeaf> productos = catalogoManager.obtenerTodosLosProductos();
            int count = 0;
            
            for (ProductoLeaf producto : productos) {
                if (producto.getUnidadesDisponibles() <= umbralStockBajo) {
                    NotificacionInventario notif = notificacionManager.getNotificacionInventario();
                    Producto prod = producto.getProducto();
                    
                    notif.notificarStockBajo(
                            contactoAdmin,
                            prod.getProductoID(),
                            prod.getNombre(),
                            prod.getUnidadesDisponibles(),
                            umbralStockBajo
                    );
                    
                    count++;
                }
            }
            
            if (count > 0) {
                LOGGER.log(Level.INFO, "Se encontraron {0} productos con stock bajo", count);
            }
            
            return count;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al verificar stock bajo", e);
            return 0;
        }
    }
    
    /**
     * Notifica el valor total del inventario.
     * 
     * @return true si la notificación se envió correctamente
     */
    public boolean notificarValorInventario() {
        if (!notificacionesActivas) {
            return false;
        }
        
        try {
            double valorTotal = catalogoManager.calcularValorTotalInventario();
            List<ProductoLeaf> productos = catalogoManager.obtenerTodosLosProductos();
            
            String mensaje = String.format(
                    "Resumen de Inventario:\n- Total de productos: %d\n- Valor total: $%.2f",
                    productos.size(), valorTotal);
            
            return notificacionManager.getNotificacionInventario().enviar(contactoAdmin, mensaje);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al notificar valor de inventario", e);
            return false;
        }
    }
    
    /**
     * Cambia el tipo de notificación para mensajes de inventario.
     * 
     * @param tipoNotificacion Tipo de notificación (email, sms, alerta)
     * @return true si se cambió correctamente
     */
    public boolean cambiarTipoNotificacionInventario(String tipoNotificacion) {
        boolean resultado = notificacionManager.cambiarImplementador("inventario", tipoNotificacion);
        if (resultado) {
            LOGGER.log(Level.INFO, "Tipo de notificación de inventario cambiado a: {0}", tipoNotificacion);
        } else {
            LOGGER.log(Level.WARNING, "No se pudo cambiar el tipo de notificación de inventario");
        }
        return resultado;
    }
}
