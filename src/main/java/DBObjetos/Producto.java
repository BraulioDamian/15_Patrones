package DBObjetos;

import java.time.LocalDate;

public class Producto implements Cloneable, Producto2 {
    private int productoID;
    private String nombre;
    private String descripcion;
    private int areaID;
    private double precio;
    private int unidadesDisponibles;
    private int nivelReorden;
    private LocalDate fechaCaducidad;
    private String codigoBarras;
    private String tamañoNeto;
    private String marca;
    private String contenido;
    private String nombreArea;
    private int cantidad;
    private ProductoMetadata metadata; // Estado intrínseco compartido


    public Producto(int productoID, String nombre, double precio, int cantidad) {
        this.productoID = productoID;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto(int productoID, String codigoBarras, String nombre, double precio, int cantidad) {
        this.productoID = productoID;
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto(int productoID, ProductoMetadata metadata, int areaID, double precio,
                   int unidadesDisponibles, int nivelReorden, LocalDate fechaCaducidad,
                   String nombreArea, int cantidad) {
        this.productoID = productoID;
        this.metadata = metadata;
        this.areaID = areaID;
        this.precio = precio;
        this.unidadesDisponibles = unidadesDisponibles;
        this.nivelReorden = nivelReorden;
        this.fechaCaducidad = fechaCaducidad;
        this.nombreArea = nombreArea;
        this.cantidad = cantidad;
    }

    private Producto(ProductoBuilder builder) {
        this.productoID = builder.productoID;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.areaID = builder.areaID;
        this.precio = builder.precio;
        this.unidadesDisponibles = builder.unidadesDisponibles;
        this.nivelReorden = builder.nivelReorden;
        this.fechaCaducidad = builder.fechaCaducidad;
        this.codigoBarras = builder.codigoBarras;
        this.tamañoNeto = builder.tamañoNeto;
        this.marca = builder.marca;
        this.contenido = builder.contenido;
        this.nombreArea = builder.nombreArea;
        this.cantidad = builder.cantidad;
    }

    private Producto() {
    }

    @Override
    public Producto clone() {
        try {
            return (Producto) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // No debería ocurrir
        }
    }
    
   
    public Producto crearProductoNoEditable() {
        Producto noEditable = this.clone();
        // Configurar noEditable para que no se pueda modificar...
        return noEditable;
    }

    // Getters
    @Override
    public int getProductoID() { return productoID; }
    @Override
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getAreaID() { return areaID; }
    @Override
    public double getPrecio() { return precio; }
    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public int getNivelReorden() { return nivelReorden; }
    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public String getCodigoBarras() { return codigoBarras; }
    public String getTamañoNeto() { return tamañoNeto; }
    public String getMarca() { return marca; }
    public String getContenido() { return contenido; }
    public String getNombreArea() { return nombreArea; }
    @Override
    public int getCantidad() { return cantidad; }


    // Setters 
    public void setProductoID(int productoID) { this.productoID = productoID; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setAreaID(int areaID) { this.areaID = areaID; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setUnidadesDisponibles(int unidadesDisponibles) { this.unidadesDisponibles = unidadesDisponibles; }
    public void setNivelReorden(int nivelReorden) { this.nivelReorden = nivelReorden; }
    public void setFechaCaducidad(LocalDate fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    public void setTamañoNeto(String tamañoNeto) { this.tamañoNeto = tamañoNeto; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public void setNombreArea(String nombreArea) { this.nombreArea = nombreArea; }
    @Override
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    // No hay setters para los campos del metadata ya que son inmutables

    @Override
    public String toString() {
        return "Producto{" +
                "productoID=" + productoID +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", areaID=" + areaID +
                ", precio=" + precio +
                ", unidadesDisponibles=" + unidadesDisponibles +
                ", nivelReorden=" + nivelReorden +
                ", fechaCaducidad=" + fechaCaducidad +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", tamañoNeto='" + tamañoNeto + '\'' +
                ", marca='" + marca + '\'' +
                ", contenido='" + contenido + '\'' +
                ", nombreArea='" + nombreArea + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }

    // Clase Builder modificada para usar Flyweight
    public static class ProductoBuilder {
        private int productoID;
        private String nombre;
        private String descripcion;
        private int areaID;
        private double precio;
        private int unidadesDisponibles;
        private int nivelReorden;
        private LocalDate fechaCaducidad;
        private String codigoBarras;
        private String tamañoNeto;
        private String marca;
        private String contenido;
        private String nombreArea;
        private int cantidad;
        private ProductoMetadata metadata;

        public ProductoBuilder productoID(int productoID) { this.productoID = productoID; return this; }
        public ProductoBuilder nombre(String nombre) { this.nombre = nombre; return this; }
        public ProductoBuilder descripcion(String descripcion) { this.descripcion = descripcion; return this; }
        public ProductoBuilder areaID(int areaID) { this.areaID = areaID; return this; }
        public ProductoBuilder precio(double precio) { this.precio = precio; return this; }
        public ProductoBuilder unidadesDisponibles(int unidadesDisponibles) { this.unidadesDisponibles = unidadesDisponibles; return this; }
        public ProductoBuilder nivelReorden(int nivelReorden) { this.nivelReorden = nivelReorden; return this; }
        public ProductoBuilder fechaCaducidad(LocalDate fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; return this; }
        public ProductoBuilder codigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; return this; }
        public ProductoBuilder tamañoNeto(String tamañoNeto) { this.tamañoNeto = tamañoNeto; return this; }
        public ProductoBuilder marca(String marca) { this.marca = marca; return this; }
        public ProductoBuilder contenido(String contenido) { this.contenido = contenido; return this; }
        public ProductoBuilder nombreArea(String nombreArea) { this.nombreArea = nombreArea; return this; }
        public ProductoBuilder cantidad(int cantidad) { this.cantidad = cantidad; return this; }
        public ProductoBuilder metadata(String nombre, String descripcion, String marca,
                                  String contenido, String tamañoNeto, String codigoBarras) {
        this.metadata = ProductoMetadataFactory.getMetadata(
            nombre, descripcion, marca, contenido, tamañoNeto, codigoBarras);
        return this;
        }

        public Producto build() {
            return new Producto(this);
        }
    }
}