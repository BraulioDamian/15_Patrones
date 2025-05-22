/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBObjetos;

/**
 *
 * @author Luis
 */
public class Inventario {
    private int codBarras;
    private String nombre;
    private String marca;
    private int unidadesDisp;
    private String contenido;
    private String area;
    private double precio;

    // Constructor
    public Inventario(int codBarras, String nombre, String marca, int unidadesDisp, 
                      String contenido, String area, double precio) {
        this.codBarras = codBarras;
        this.nombre = nombre;
        this.marca = marca;
        this.unidadesDisp = unidadesDisp;
        this.contenido = contenido;
        this.area = area;
        this.precio = precio;
    }

    // Getters y setters
    public int getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(int codBarras) {
        this.codBarras = codBarras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getUnidadesDisp() {
        return unidadesDisp;
    }

    public void setUnidadesDisp(int unidadesDisp) {
        this.unidadesDisp = unidadesDisp;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // toString para imprimir la información del objeto si es necesario
    @Override
    public String toString() {
        return "Inventario{" +
                "codBarras=" + codBarras +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", unidadesDisp=" + unidadesDisp +
                ", contenido='" + contenido + '\'' +
                ", area='" + area + '\'' +
                ", precio=" + precio +
                '}';
    }
}

