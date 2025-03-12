/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author braul
 */
import ConexionDB.Conexion_DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdatePassword {
    private static final Logger LOGGER = Logger.getLogger(UpdatePassword.class.getName());

    public static boolean updatePassword(int userId, String newPassword) {
        String hashedPassword = HashingUtil.hashPassword(newPassword);  // Asumimos que existe y es accesible
        String sql = "UPDATE Usuario SET Contraseña = ? WHERE UsuarioID = ?";
        
        try (Connection conn = Conexion_DB.getConexion();  // Usar la clase Conexion_DB para obtener la conexión
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hashedPassword);
            pstmt.setInt(2, userId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                LOGGER.log(Level.INFO, "Contraseña actualizada correctamente para el usuario ID: {0}", userId);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No se actualizó ninguna fila, verifique el ID del usuario: {0}", userId);
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar la contraseña", e);
            return false;
        }
    }

    
public static boolean updatePasswordToken(int userId, String newToken) {
    // Hashea el token antes de guardarlo
    String hashedToken = HashingUtil.hashPassword(newToken);
    
    String sql = "UPDATE Usuario SET ContraseñaToken = ? WHERE UsuarioID = ?";
    
    try (Connection conn = Conexion_DB.getConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, hashedToken);
        pstmt.setInt(2, userId);

        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Error al actualizar el token hasheado", e);
        return false;
    }
}



    
    public static void main(String[] args) {
        boolean result = updatePasswordToken(17, "123");
        if (result) {
            System.out.println("Actualización de contraseña exitosa.");
        } else {
            System.out.println("Falló la actualización de contraseña.");
        }
    }
}

