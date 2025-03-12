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
