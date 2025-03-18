package Icons;

import javax.swing.ImageIcon;
import java.net.URL;

public class prueba {

    public static ImageIcon getIconCerrarOjo() {
        // Ruta absoluta porque el recurso está en src/main/resources/Icons
        URL iconURL = prueba.class.getResource("/Icons/cerrarOjo.png");
        if (iconURL != null) {
            return new ImageIcon(iconURL);
        } else {
            System.err.println("No se pudo encontrar el recurso: cerrarOjo.png");
            return null;
        }
    }

    public static ImageIcon getIconAbrirOjo() {
        URL iconURL = prueba.class.getResource("/Icons/abrirOjo.png");
        if (iconURL != null) {
            return new ImageIcon(iconURL);
        } else {
            System.err.println("No se pudo encontrar el recurso: abrirOjo.png");
            return null;
        }
    }

    // Método main para probar la carga de los iconos
    public static void main(String[] args) {
        ImageIcon icon1 = getIconCerrarOjo();
        ImageIcon icon2 = getIconAbrirOjo();

        System.out.println("Icon cerrarOjo: " + (icon1 != null ? "encontrado" : "no encontrado"));
        System.out.println("Icon abrirOjo: " + (icon2 != null ? "encontrado" : "no encontrado"));
    }
}
