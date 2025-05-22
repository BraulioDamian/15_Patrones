package DBObjetos;

import java.time.LocalDateTime;

public class VentaProxy implements IVenta {
    private Venta ventaReal;

    public VentaProxy() {
        this.ventaReal = null; 
    }

    @Override
    public void realizarVenta(int usuarioID, LocalDateTime fechaVenta, double precioTotal) {
        if (!tienePermiso(usuarioID)) {
            System.out.println("Acceso denegado: El usuario no tiene permisos para realizar la venta.");
            return;
        }

        System.out.println("El Rol De Usuario...");
        System.out.println("El Ro, De Uusuario Es...");

        
        if (ventaReal == null) {
            System.out.println("Creando instancia de Venta...");
            ventaReal = new Venta();
        }

        
        ventaReal.realizarVenta(usuarioID, fechaVenta, precioTotal);
    }

    
    private boolean tienePermiso(int usuarioID) {
        
        return usuarioID > 0 && usuarioID < 1; 
    }
}
