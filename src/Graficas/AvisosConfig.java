/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Graficas;

import DBObjetos.Producto;
import java.time.LocalDate;

/**
 *
 * @author Luis
 */
public class AvisosConfig {
    
    AvisosFrame a = new AvisosFrame();
    
    public void esReabastecible(Producto p){
        LocalDate fechaCaducidad = p.getFechaCaducidad();
        LocalDate unMesAntes = fechaCaducidad.minusMonths(1);
        LocalDate hoy = LocalDate.now();
        
        boolean r = p.getUnidadesDisponibles() <= 20;
        int nivel = p.getNivelReorden();
        boolean reabastecible = p.getUnidadesDisponibles() <= nivel; 
        
        boolean estaCercaCaducidad = !hoy.isBefore(unMesAntes);
        System.out.println("PRUEBAAA");
        if (reabastecible) {
            System.out.println("Reabastecer " + p.getNombre());
            
        }
        if (estaCercaCaducidad) {
            System.out.println("Se sugiere poner en oferta el producto " + p.getNombre() + ", con fecha de caducidad " + p.getFechaCaducidad());
        }
    }
    
    
    
}
