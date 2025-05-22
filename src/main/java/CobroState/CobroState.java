package CobroState;

public interface CobroState {
    void seleccionarMetodoPago(Venta.Cobro cobro, String metodoPago);
    void ingresarMonto(Venta.Cobro cobro, double monto);
    boolean procesarPago(Venta.Cobro cobro);
    void cancelarOperacion(Venta.Cobro cobro);
    String getNombreEstado();
}