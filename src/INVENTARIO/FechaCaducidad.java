/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package INVENTARIO;

/**
 *
 * @author braul
 */
import ConexionDB.Conexion_DB;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;

public class FechaCaducidad {
        private static final Logger LOGGER = Logger.getLogger(Conexion_DB.class.getName());

    private static final String URL = "jdbc:mysql://localhost:3307/dbtienda";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "123456";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "No se pudo cargar el driver de la base de datos MySQL", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }

    public static void actualizarFechaCaducidad() {
        String sqlSelect = "SELECT ProductoID FROM productos WHERE FechaCaducidad IS NULL";
        String sqlUpdate = "UPDATE productos SET FechaCaducidad = ? WHERE ProductoID = ?";

        try (Connection conn = getConexion();
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
             ResultSet rs = pstmtSelect.executeQuery()) {
             
            while (rs.next()) {
                int productoID = rs.getInt("ProductoID");
                LocalDate nuevaFechaCaducidad = generateRandomExpirationDate(LocalDate.now(), 365);

                pstmtUpdate.setDate(1, java.sql.Date.valueOf(nuevaFechaCaducidad));
                pstmtUpdate.setInt(2, productoID);
                pstmtUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar la fecha de caducidad", e);
        }
    }

    private static LocalDate generateRandomExpirationDate(LocalDate start, int days) {
        Random random = new Random();
        long randomDays = random.nextInt(days);
        return start.plus(randomDays, ChronoUnit.DAYS);
    }

    public static void main(String[] args) {
        try (Connection conn = getConexion()) {
            System.out.println("Conexión a la base de datos exitosa.");
            actualizarFechaCaducidad();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos", e);
        }
    }
    
}

