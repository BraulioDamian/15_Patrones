package DBObjetos;

public class ProductoMetadata {
    private final String nombre;
    private final String descripcion;
    private final String marca;
    private final String contenido;
    private final String tamañoNeto;
    private final String codigoBarras;
    
    public ProductoMetadata(String nombre, String descripcion, String marca, 
                          String contenido, String tamañoNeto, String codigoBarras) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.contenido = contenido;
        this.tamañoNeto = tamañoNeto;
        this.codigoBarras = codigoBarras;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getMarca() { return marca; }
    public String getContenido() { return contenido; }
    public String getTamañoNeto() { return tamañoNeto; }
    public String getCodigoBarras() { return codigoBarras; }
}