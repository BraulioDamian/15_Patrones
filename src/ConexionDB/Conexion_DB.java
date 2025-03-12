/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDB;

/**
 *
 * @author braul
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author braul
 */




public class Conexion_DB {
    private static final String URL = "jdbc:mysql://localhost:3307/dbtienda";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "123456"; // Asume que no hay contraseña configurada

    // Logger para registrar información y errores
    private static final Logger LOGGER = Logger.getLogger(Conexion_DB.class.getName());

    
    //lalmada al sietma DB from 
    static {
        try {
            // Cargar el driver de MySQL. Este paso es opcional en las versiones modernas de JDBC.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "No se pudo cargar el driver de la base de datos MySQL", e);
            throw new ExceptionInInitializerError(e); // No continuar si el driver no está disponible
        }
    }

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
    
        public static boolean validarUsuario(String usuario, String contraseña) {
        String sql = "SELECT COUNT(*) AS count FROM Usuarios WHERE Username = ? AND PasswordHash = ?";
        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario);
            pstmt.setString(2, contraseña);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al validar el usuario", e);
        }
        return false;
    }
        
        
    public static void main(String[] args) {
        try (Connection conn = getConexion()) {
            System.out.println("Conexión a la base de datos exitosa.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos", e);
        }
    }
}
