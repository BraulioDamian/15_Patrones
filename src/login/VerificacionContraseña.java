/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import java.util.Random;

/**
 *
 * @author braul
 */

public class VerificacionContraseña {
    // Variable pública y estática para que pueda ser accedida desde otras clases
    public static int codigoVerificacion;

    // Constructor de la clase
    public VerificacionContraseña() {
        generarCodigo();
    }

    // Método para generar un código de 4 dígitos aleatorio
    private void generarCodigo() {
        Random random = new Random();
        // Genera un número de 4 cifras (1000 a 9999)
        codigoVerificacion = 1000 + random.nextInt(9000);
    }

    // Método público estático para obtener el código de verificación desde otras clases
    public static int getCodigoVerificacion() {
        return codigoVerificacion;
    }

    // Punto de entrada para prueba
    public static void main(String[] args) {
        VerificacionContraseña vc = new VerificacionContraseña();
        System.out.println("El código de verificación generado es: " + VerificacionContraseña.getCodigoVerificacion());
    }
}

