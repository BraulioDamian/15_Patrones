package DBObjetos;

import java.time.LocalDateTime;

public class VentaProxy implements IVenta {
    private Venta ventaReal;

    public VentaProxy() {
        this.ventaReal = null;
    }

    @Override
    public void realizarVenta(int usuarioID, LocalDateTime fechaVenta, double precioTotal) {
        // Verificar permisos antes de proceder
        if (!tienePermiso(usuarioID)) {
            System.out.println("Acceso denegado: El usuario ID " + usuarioID + " no tiene permisos para realizar la venta.");
            return;
        }

        // Validaciones adicionales
        if (precioTotal <= 0) {
            System.out.println("Error: El precio total debe ser mayor a 0.");
            return;
        }

        if (fechaVenta == null) {
            System.out.println("Error: La fecha de venta no puede ser nula.");
            return;
        }

        System.out.println("Verificando permisos del usuario...");
        System.out.println("Usuario autorizado. Procediendo con la venta...");

        // Crear instancia de Venta real si no existe (Lazy initialization)
        if (ventaReal == null) {
            System.out.println("Creando instancia de Venta...");
            ventaReal = new Venta(); // Ahora funciona con el constructor por defecto
        }

        // Delegar la operación a la venta real
        ventaReal.realizarVenta(usuarioID, fechaVenta, precioTotal);
        
        // Log adicional del proxy
        System.out.println("Proxy: Venta procesada correctamente a través del proxy.");
    }

    /**
     * Verifica si el usuario tiene permisos para realizar una venta
     * @param usuarioID ID del usuario
     * @return true si tiene permisos, false en caso contrario
     */
    private boolean tienePermiso(int usuarioID) {
        // Lógica de permisos corregida
        // Un usuario válido debe tener ID mayor a 0
        return usuarioID > 0;
    }

    /**
     * Método adicional para obtener información de la venta sin crearla
     */
    public boolean tieneVentaActiva() {
        return ventaReal != null;
    }

    /**
     * Método para obtener información de la venta real (si existe)
     */
    public Venta getVentaReal() {
        return ventaReal;
    }
}
