package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para gestionar la conexión a la base de datos.
 * @author braul
 */
public class Conexion_DB {

    // Configuración para conexión local (descomentar para usar local)
    private static final String URL = "jdbc:mysql://localhost:3307/dbtienda" +
            "?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "123456"; // Cambia esto según tu configuración
    
    /*
    // Configuración para conexión con variables de entorno (descomentar para usar variables)
    private static final String URL = "jdbc:mysql://" +
            System.getenv("DB_HOST") + ":" +
            System.getenv("DB_PORT") + "/" +
            System.getenv("DB_NAME") +
            "?useUnicode=true&characterEncoding=UTF-8";
    private static final String USUARIO = System.getenv("DB_USER");
    private static final String CONTRASENA = System.getenv("DB_PASSWORD");
    */

    // Logger para registrar información y errores
    private static final Logger LOGGER = Logger.getLogger(Conexion_DB.class.getName());

    static {
        try {
            // Cargar el driver de MySQL. Este paso es opcional en las versiones modernas de JDBC.
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.log(Level.INFO, "Driver de MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "No se pudo cargar el driver de la base de datos MySQL", e);
            throw new ExceptionInInitializerError(e); // No continuar si el driver no está disponible
        }
    }

    /**
     * Obtiene una conexión a la base de datos con reintentos en caso de fallo.
     * 
     * @return Conexión a la base de datos
     * @throws SQLException Si no se puede establecer la conexión después de varios intentos
     */
    public static Connection getConexion() throws SQLException {
        int retries = 3; // Reducido a 3 intentos para no esperar demasiado
        int retryDelayMs = 2000; // 2 segundos entre intentos
        
        SQLException lastException = null;
        
        while (retries > 0) {
            try {
                Connection conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                LOGGER.log(Level.INFO, "Conexión establecida correctamente a {0}", URL);
                return conn;
            } catch (SQLException e) {
                lastException = e;
                LOGGER.log(Level.WARNING, "Intento de conexión fallido ({0} intentos restantes): {1}", 
                        new Object[]{retries, e.getMessage()});
                retries--;
                
                if (retries > 0) {
                    try { 
                        Thread.sleep(retryDelayMs); 
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        // Si llegamos aquí, todos los intentos fallaron
        LOGGER.log(Level.SEVERE, "No se pudo conectar a la base de datos después de varios intentos", lastException);
        throw new SQLException("No se pudo conectar a la base de datos: " + 
                (lastException != null ? lastException.getMessage() : "razón desconocida"));
    }

    /**
     * Prueba la conexión a la base de datos.
     * 
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean probarConexion() {
        try (Connection conn = getConexion()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al probar la conexión con la base de datos", e);
            return false;
        }
    }

    /**
     * Método principal para probar la conexión.
     */
    public static void main(String[] args) {
        try (Connection conn = getConexion()) {
            System.out.println("Conexión a la base de datos exitosa.");
            System.out.println("URL: " + URL);
            System.out.println("Usuario: " + USUARIO);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}