package PatronBridge;

import javax.swing.*;

/**
 * Clase principal para ejecutar la demostración del patrón Bridge
 * con interfaz gráfica integrada.
 */
public class BridgeMainDemo {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Configurar Look and Feel del sistema
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                // Usar Look and Feel por defecto si hay problemas
                System.out.println("Usando Look and Feel por defecto");
            }
            
            // Mostrar mensaje de bienvenida
            JOptionPane.showMessageDialog(null, 
                "¡Bienvenido al Sistema de Notificaciones!\\n\\n" +
                "Este sistema demuestra el patrón Bridge implementado\\n" +
                "para gestionar diferentes tipos de notificaciones:\\n\\n" +
                "• Notificaciones de Sistema\\n" +
                "• Notificaciones de Inventario\\n" +
                "• Notificaciones de Ventas\\n\\n" +
                "Con múltiples implementadores:\\n" +
                "• Email\\n" +
                "• SMS\\n" +
                "• Alertas Internas\\n\\n" +
                "¡Explore todas las funcionalidades!",
                "Sistema de Notificaciones - Patrón Bridge",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Inicializar y mostrar la interfaz gráfica
            NotificacionBridgeGUI gui = new NotificacionBridgeGUI();
            gui.setVisible(true);
            
            // Centrar en pantalla después de mostrar
            gui.setLocationRelativeTo(null);
        });
    }
}
