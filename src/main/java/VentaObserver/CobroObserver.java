package VentaObserver;

public interface CobroObserver {
    void onMetodoPagoSeleccionado(String metodoPago);
    void onMontoRecibido(double monto);
    void onCambioCalculado(double cambio);
    void onVentaCompleta(double totalVenta, double montoRecibido, double cambio);
    void onVentaCancelada();
}
