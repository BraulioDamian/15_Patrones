package Mediator;

import Configuraciones.Configuraciones;
import DBObjetos.Usuario;
import Graficas.AvisosFrame;
import INVENTARIO.Principal2_0;
import PanelUsuarios.UsuariosPanel;
import Principal.MenuPrincipal;
import Venta.Venta;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación concreta del mediador que coordina la comunicación entre los componentes
 */
public class MediadorConcreto implements Mediador {
    
    private static final Logger LOGGER = Logger.getLogger(MediadorConcreto.class.getName());
    private static MediadorConcreto instance;
    private final Map<String, Componente> componentes;
    
    /**
     * Constructor privado para implementar el patrón Singleton
     */
    private MediadorConcreto() {
        componentes = new HashMap<>();
    }
    
    /**
     * Obtiene la instancia única del mediador (Singleton)
     * @return Instancia del mediador
     */
    public static MediadorConcreto getInstance() {
        if (instance == null) {
            instance = new MediadorConcreto();
        }
        return instance;
    }

    @Override
    public void notificar(Componente remitente, String evento, Map<String, Object> datos) {
        LOGGER.log(Level.INFO, "Mediador recibió evento: {0} de {1}", new Object[]{evento, remitente.getNombre()});
        
        // Dependiendo del evento, notificar a los componentes apropiados
        switch (evento) {
            case "ACTUALIZAR_INVENTARIO":
                // Notificar a componentes relacionados con el inventario
                notificarComponentesExcepto(remitente, "INVENTARIO", evento, datos);
                break;
                
            case "NUEVA_VENTA":
                // Notificar sobre una nueva venta
                notificarComponentesExcepto(remitente, "VENTA", evento, datos);
                break;
                
            case "ACTUALIZAR_USUARIO":
                // Notificar sobre cambios en los usuarios
                notificarComponentesExcepto(remitente, "USUARIOS", evento, datos);
                break;
                
            case "CAMBIO_CONFIGURACION":
                // Notificar sobre cambios en la configuración
                notificarATodos(evento, datos);
                break;
                
            default:
                // Para otros eventos, notificar a todos los componentes
                notificarATodos(evento, datos);
                break;
        }
    }
    
    /**
     * Notifica a todos los componentes excepto al remitente
     */
    private void notificarComponentesExcepto(Componente remitente, String tipoComponente, String evento, Map<String, Object> datos) {
        for (Componente componente : componentes.values()) {
            if (componente != remitente && componente.getNombre().contains(tipoComponente)) {
                componente.recibirNotificacion(evento, datos);
            }
        }
    }
    
    /**
     * Notifica a todos los componentes registrados
     */
    private void notificarATodos(String evento, Map<String, Object> datos) {
        for (Componente componente : componentes.values()) {
            componente.recibirNotificacion(evento, datos);
        }
    }

    @Override
    public void registrarComponente(Componente componente) {
        componentes.put(componente.getNombre(), componente);
        componente.setMediador(this);
        LOGGER.log(Level.INFO, "Componente registrado: {0}", componente.getNombre());
    }

    @Override
    public void iniciarPantalla(String nombrePantalla, Usuario usuario) {
        LOGGER.log(Level.INFO, "Iniciando pantalla: {0}", nombrePantalla);
        
        // Ocultar todas las pantallas primero
        ocultarTodasLasPantallas();
        
        // Iniciar la pantalla solicitada
        switch (nombrePantalla) {
            case "MENU_PRINCIPAL":
                MenuPrincipal menuPrincipal = MenuPrincipal.getInstance();
                menuPrincipal.initialize(usuario);
                menuPrincipal.setVisible(true);
                break;
                
            case "INVENTARIO":
                Principal2_0 inventario = Principal2_0.getInstance();
                inventario.initialize(usuario);
                inventario.setVisible(true);
                break;
                
            case "VENTA":
                Venta venta = Venta.getInstance();
                venta.initialize(usuario);
                venta.setVisible(true);
                break;
                
            case "USUARIOS":
                UsuariosPanel usuarios = UsuariosPanel.getInstance();
                usuarios.initialize(usuario);
                usuarios.setVisible(true);
                break;
                
            case "CONFIGURACIONES":
                Configuraciones config = Configuraciones.getInstance();
                config.setVisible(true);
                break;
                
            case "GRAFICAS":
                AvisosFrame graficas = AvisosFrame.getInstance();
                graficas.initialize(usuario);
                graficas.setVisible(true);
                break;
                
            default:
                LOGGER.log(Level.WARNING, "Pantalla no reconocida: {0}", nombrePantalla);
                break;
        }
    }
    
    /**
     * Oculta todas las pantallas principales de la aplicación
     */
    private void ocultarTodasLasPantallas() {
        MenuPrincipal.getInstance().setVisible(false);
        
        // Verificar si existen instancias antes de intentar ocultarlas
        try {
            Principal2_0.getInstance().setVisible(false);
        } catch (Exception e) {
            // La instancia podría no existir aún
        }
        
        try {
            Venta.getInstance().setVisible(false);
        } catch (Exception e) {
            // La instancia podría no existir aún
        }
        
        try {
            UsuariosPanel.getInstance().setVisible(false);
        } catch (Exception e) {
            // La instancia podría no existir aún
        }
        
        try {
            Configuraciones.getInstance().setVisible(false);
        } catch (Exception e) {
            // La instancia podría no existir aún
        }
        
        try {
            AvisosFrame.getInstance().setVisible(false);
        } catch (Exception e) {
            // La instancia podría no existir aún
        }
    }
}
