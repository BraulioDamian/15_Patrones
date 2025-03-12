/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Venta;

/**
 *
 * @author Luis
 */
public class InventarioVenta {
    private int Codigo;
    private double Unidades;
    private String Nombre;
    private double PrecioU;
    private double Importe;

    public InventarioVenta(int Codigo, double Unidades, String Nombre, double PrecioU, double Importe) {
        this.Codigo = Codigo;
        this.Unidades = Unidades;
        this.Nombre = Nombre;
        this.PrecioU = PrecioU;
        this.Importe = Importe;
    }

    public int getCodigo() {
        return Codigo;
    }

    /*package Venta;

public class InventarioVenta implements Cloneable { Aquie implementamos un clone, esto nos es util, pues en vez de crear otra venta con los mismos datos solo que cambie por ejmeplo un producto, copiamos uno ya existente
    private int Codigo;
    private double Unidades;
    private String Nombre;
    private double PrecioU;
    private double Importe;

    public InventarioVenta(int Codigo, double Unidades, String Nombre, double PrecioU, double Importe) {
        this.Codigo = Codigo;
        this.Unidades = Unidades;
        this.Nombre = Nombre;
        this.PrecioU = PrecioU;
        this.Importe = Importe;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public double getUnidades() {
        return Unidades;
    }

    public void setUnidades(double Unidades) {
        this.Unidades = Unidades;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public double getPrecioU() {
        return PrecioU;
    }

    public void setPrecioU(double PrecioU) {
        this.PrecioU = PrecioU;
    }

    public double getImporte() {
        return Importe;
    }

    public void setImporte(double Importe) {
        this.Importe = Importe;
    }

    @Override
    public InventarioVenta clone() {
        try {
            return (InventarioVenta) super.clone(); De esta manera ya esta implementado el clonade para la venta,aqui solo nos regresara que si se creo un clon,pero por ejemplo en la clase main,es donde podremos modificar la venta
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}*/

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public double getUnidades() {
        return Unidades;
    }

    public void setUnidades(double Unidades) {
        this.Unidades = Unidades;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public double getPrecioU() {
        return PrecioU;
    }

    public void setPrecioU(double PrecioU) {
        this.PrecioU = PrecioU;
    }

    public double getImporte() {
        return Importe;
    }

    public void setImporte(double Importe) {
        this.Importe = Importe;
    }
    
    
}
