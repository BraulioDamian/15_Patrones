/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author riosr
 */
import Venta.ServicioEnvio;

public class MandarCorreosAdapter implements ServicioEnvio{
    private MandarCorreos mandarCorreos;
    
    public MandarCorreosAdapter(){
        this.mandarCorreos=new MandarCorreos();
    }
    @Override
    public void enviar(String emailDestino, String asunto, String contenido, String pdfPath){
     mandarCorreos.enviarArchivo(emailDestino, asunto, contenido, pdfPath);
        //throw new RuntimeException("Error simulado: Servicio de correo no disponible");
    }
}