/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Venta;

import login.MandarCorreos;

/**
 *
 * @author braul
 */
public class EnvioTicket {

    // Método para enviar un correo con un archivo adjunto
    public static void enviarConArchivo(String emailDestino, String pdfPath) {
        // Crear una instancia de la clase MandarCorreos
        MandarCorreos mandarCorreos = new MandarCorreos();

        // Definir el asunto del correo
        String subject = "Su Ticket de Compra";

        // Definir el cuerpo del correo
        String content = "Tu ticket es este. Encuentra adjunto el PDF con los detalles.";

        // Llamar al método enviarArchivo para enviar el correo con el PDF adjunto
        mandarCorreos.enviarArchivo(emailDestino, subject, content, pdfPath);
    }
}
