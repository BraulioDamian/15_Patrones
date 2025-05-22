package VentaMemento;

public class CobroMemento {
    private final double pre;
    private final double cambio;
    private final String precioEnLetras;
    private final String recibi;
    private final boolean efectivoSeleccionado;
    private final boolean tarjetaSeleccionada;
    private final String correo;

    public CobroMemento(double pre, double cambio, String precioEnLetras, String recibi, boolean efectivoSeleccionado, boolean tarjetaSeleccionada, String correo) {
        this.pre = pre;
        this.cambio = cambio;
        this.precioEnLetras = precioEnLetras;
        this.recibi = recibi;
        this.efectivoSeleccionado = efectivoSeleccionado;
        this.tarjetaSeleccionada = tarjetaSeleccionada;
        this.correo = correo;
    }

    public double getPre() {
        return pre;
    }

    public double getCambio() {
        return cambio;
    }

    public String getPrecioEnLetras() {
        return precioEnLetras;
    }

    public String getRecibi() {
        return recibi;
    }

    public boolean isEfectivoSeleccionado() {
        return efectivoSeleccionado;
    }

    public boolean isTarjetaSeleccionada() {
        return tarjetaSeleccionada;
    }

    public String getCorreo() {
        return correo;
    }
}