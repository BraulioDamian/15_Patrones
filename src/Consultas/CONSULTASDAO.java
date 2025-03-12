/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Consultas;
import ConexionDB.Conexion_DB;
import DBObjetos.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import login.HashingUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Map;


/**
 *
 * @author braul
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



public class CONSULTASDAO {
    private Connection con;
    private static final Logger LOGGER = Logger.getLogger(CONSULTASDAO.class.getName());

    public CONSULTASDAO(Connection con) {
        this.con = con;
    }

    
    
    
    
    
    public List<Producto> obtenerProductosConNombre(String texto) {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = "SELECT p.*, a.Nombre AS NombreArea FROM productos p " +
                     "INNER JOIN area a ON p.AreaID = a.AreaID " +
                     "WHERE p.Nombre LIKE ?"; // Filtrar por nombre

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + texto + "%"); // Configurar el parámetro del LIKE
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto();  // Uso del constructor vacío
                    producto.setProductoID(rs.getInt("ProductoID"));
                    producto.setNombre(rs.getString("Nombre"));
                    producto.setDescripcion(rs.getString("Descripcion"));
                    producto.setAreaID(rs.getInt("AreaID"));
                    producto.setPrecio(rs.getDouble("Precio"));
                    producto.setUnidadesDisponibles(rs.getInt("UnidadesDisponibles"));
                    producto.setNivelReorden(rs.getInt("NivelReorden"));
                    producto.setFechaCaducidad(rs.getDate("FechaCaducidad") != null ? rs.getDate("FechaCaducidad").toLocalDate() : null);
                    producto.setCodigoBarras(rs.getString("CodigoBarras"));
                    producto.setTamañoNeto(rs.getString("TamañoNeto"));
                    producto.setMarca(rs.getString("Marca"));
                    producto.setContenido(rs.getString("Contenido"));
                    producto.setNombreArea(rs.getString("NombreArea"));
                    // No necesitas setear cantidad aquí si no es relevante
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // O maneja la excepción como consideres adecuado
        }

        return listaProductos;
    }

    
    

//nueva PRUEBA
    public List<Producto> obtenerProductosConNombreArea()  {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = "SELECT p.*, a.Nombre AS NombreArea FROM productos p " +
                     "INNER JOIN area a ON p.AreaID = a.AreaID";

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Producto producto = new Producto(); // Uso del constructor vacío
                producto.setProductoID(rs.getInt("ProductoID"));
                producto.setNombre(rs.getString("Nombre"));
                producto.setDescripcion(rs.getString("Descripcion"));
                producto.setAreaID(rs.getInt("AreaID"));
                producto.setPrecio(rs.getDouble("Precio"));
                producto.setUnidadesDisponibles(rs.getInt("UnidadesDisponibles"));
                producto.setNivelReorden(rs.getInt("NivelReorden"));
                producto.setFechaCaducidad(rs.getDate("FechaCaducidad") != null ? rs.getDate("FechaCaducidad").toLocalDate() : null);
                producto.setCodigoBarras(rs.getString("CodigoBarras"));
                producto.setTamañoNeto(rs.getString("TamañoNeto"));
                producto.setMarca(rs.getString("Marca"));
                producto.setContenido(rs.getString("Contenido"));
                producto.setNombreArea(rs.getString("NombreArea")); // Nombre del área directamente de la consulta SQL
                listaProductos.add(producto);


            }
        } catch (SQLException e) {
            e.printStackTrace(); // O maneja la excepción como consideres adecuado
        }

        return listaProductos;
    }

    public List<Producto> obtenerProductosPorArea(String areaNombre) throws SQLException {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = "SELECT p.ProductoID, p.Nombre, p.Descripcion, p.AreaID, p.Precio, "
                     + "p.UnidadesDisponibles, p.NivelReorden, p.FechaCaducidad, "
                     + "p.CodigoBarras, p.TamañoNeto, p.Marca, p.Contenido, a.Nombre AS NombreArea "
                     + "FROM productos p INNER JOIN area a ON p.AreaID = a.AreaID "
                     + "WHERE a.Nombre = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, areaNombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto(); // Uso del constructor vacío
                    producto.setProductoID(rs.getInt("ProductoID"));
                    producto.setNombre(rs.getString("Nombre"));
                    producto.setDescripcion(rs.getString("Descripcion"));
                    producto.setAreaID(rs.getInt("AreaID"));
                    producto.setPrecio(rs.getDouble("Precio"));
                    producto.setUnidadesDisponibles(rs.getInt("UnidadesDisponibles"));
                    producto.setNivelReorden(rs.getInt("NivelReorden"));
                    producto.setFechaCaducidad(rs.getObject("FechaCaducidad") != null ? rs.getDate("FechaCaducidad").toLocalDate() : null);
                    producto.setCodigoBarras(rs.getString("CodigoBarras"));
                    producto.setTamañoNeto(rs.getString("TamañoNeto"));
                    producto.setMarca(rs.getString("Marca"));
                    producto.setContenido(rs.getString("Contenido"));
                    producto.setNombreArea(rs.getString("NombreArea"));
                    listaProductos.add(producto);
                }
            }
        }
        return listaProductos;
    }

    
    public boolean eliminarProducto(int productoID) {
        String sql = "DELETE FROM Productos WHERE ProductoID = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, productoID);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    
    // Método para insertar un nuevo producto
    public boolean insertarProducto(Producto producto) {
        String sql = "INSERT INTO productos (Nombre, Descripcion, AreaID, Precio, UnidadesDisponibles, NivelReorden, FechaCaducidad, CodigoBarras, TamañoNeto, Marca) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setInt(3, producto.getAreaID());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getUnidadesDisponibles());
            stmt.setInt(6, producto.getNivelReorden());
            stmt.setDate(7, Date.valueOf(producto.getFechaCaducidad()));  // Asegúrate de convertir LocalDate a sql.Date
            stmt.setString(8, producto.getCodigoBarras());
            stmt.setString(9, producto.getTamañoNeto());
            stmt.setString(10, producto.getMarca());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar el producto", e);
            return false;
        }
    }
    
    //loggin metodos


    public boolean cambiarContraseña(String nombreUsuario, String nuevaContrasena) {
        String sql = "UPDATE Usuario SET Contraseña = SHA2(?, 256) WHERE NombreUsuario = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nuevaContrasena); // Asumiendo que quieres hashear la contraseña
            pstmt.setString(2, nombreUsuario);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Verificar si se cambió la contraseña correctamente
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // En caso de error
    }
    
    
     // Método para crear un nuevo empleado
    
        public boolean crearEmpleadosinToken(String nombreUsuario, String contraseña, Usuario.Rol rol,
                             String correo, String nombre, String apellido) {
    String nombreCompleto = nombre + " " + apellido;
    String sql = "INSERT INTO Usuario (NombreUsuario, Contraseña, Rol, Email, NombreCompleto) " +
                 "VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, nombreUsuario);
        pstmt.setString(2, contraseña);
        pstmt.setString(3, rol.name());
        pstmt.setString(4, correo);
        pstmt.setString(5, nombreCompleto);

        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException e) {
        if (e.getSQLState().equals("23000")) {
            System.out.println("El nombre de usuario ya existe.");
        } else {
            e.printStackTrace();
        }
        return false;
    }
}
        
    
    public boolean crearEmpleado(String nombreUsuario, String contraseña, Usuario.Rol rol,
                             String contraseñaToken, String correo, String nombre, String apellido) {
    String nombreCompleto = nombre + " " + apellido;
    String sql = "INSERT INTO Usuario (NombreUsuario, Contraseña, Rol, ContraseñaToken, Email, NombreCompleto) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, nombreUsuario);
        pstmt.setString(2, contraseña);
        pstmt.setString(3, rol.name());
        pstmt.setString(4, contraseñaToken); // Incluir el token
        pstmt.setString(5, correo);
        pstmt.setString(6, nombreCompleto);

        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException e) {
        if (e.getSQLState().equals("23000")) {
            System.out.println("El nombre de usuario ya existe.");
        } else {
            e.printStackTrace();
        }
        return false;
    }
}

    
public boolean existeAdministrador() throws SQLException {
    String sql = "SELECT COUNT(*) FROM Usuario WHERE Rol = 'ADMINISTRADOR'";
    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    }
    return false;
}




    
    
    /*
    public boolean crearEmpleado(String nombreUsuario, String contraseña, Usuario.Rol rol,
                                 ArrayList<String> preguntas, ArrayList<String> respuestas,
                                 String correo, String nombre, String apellido) {

        String nombreCompleto = nombre + " " + apellido;
        String preguntasConcatenadas = String.join("|", preguntas);
        String respuestasConcatenadas = String.join("|", respuestas);

               String sql = "INSERT INTO Usuario (NombreUsuario, Contraseña, Rol, Email, NombreCompleto, PreguntaSeguridad, RespuestaSeguridad) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, nombreUsuario);
                pstmt.setString(2, contraseña);
                pstmt.setString(3, rol.name());
                pstmt.setString(4, correo);
                pstmt.setString(5, nombreCompleto);
                pstmt.setString(6, preguntasConcatenadas);
                pstmt.setString(7, respuestasConcatenadas);


            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) { // Código de estado para violación de restricciones únicas
                System.out.println("El nombre de usuario ya existe.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }
    */
    

    public boolean insertarToken(String token, String rol) {
        // Suponemos que el token ya está hasheado antes de llamar a este método
        String sql = "INSERT INTO configuracionroles (Rol, ContrasenaHash) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, rol);
            stmt.setString(2, token);
            int affectedRows = stmt.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar el token en la base de datos", e);
            return false;
        }
    }

public String obtenerContraseñaTokenPorRol(String rol) {
    String sql = "SELECT ContraseñaToken FROM usuario WHERE Rol = ? AND ContraseñaToken IS NOT NULL LIMIT 1";
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, rol);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("ContraseñaToken");
            } else {
                Logger.getLogger(CONSULTASDAO.class.getName()).log(Level.INFO, "No se encontró token para el rol: {0}", rol);
            }
        }
    } catch (SQLException e) {
        Logger.getLogger(CONSULTASDAO.class.getName()).log(Level.SEVERE, "Error al obtener la contraseña token", e);
    }
    return null;
}




 


      
      // Método para obtener el rol de un usuario dado su nombre de usuario
    public String obtenerRolPorNombreUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT Rol FROM Usuario WHERE NombreUsuario = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Rol");
                }
            }
        }
        return null; // Retornar null o lanzar una excepción si el usuario no se encuentra
    }     
    
    
    
public Usuario validarUsuario(String nombreUsuario, String contraseñaPlana) throws SQLException {
    String sql = "SELECT UsuarioID, NombreUsuario, Contraseña, Rol, Email, NombreCompleto, UltimoLogin FROM usuario WHERE NombreUsuario = ?";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, nombreUsuario);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                String contraseñaHash = rs.getString("Contraseña");
                String contraseñaVerificar = HashingUtil.hashPassword(contraseñaPlana);

                if (contraseñaHash != null && contraseñaHash.equals(contraseñaVerificar)) {
                    Usuario usuario = new Usuario(); // Usar el constructor vacío
                    
                    usuario.setUsuarioID(rs.getInt("UsuarioID"));
                    usuario.setNombreUsuario(nombreUsuario);
                    usuario.setRol(Usuario.Rol.valueOf(rs.getString("Rol")));
                    usuario.setEmail(rs.getString("Email"));
                    usuario.setNombreCompleto(rs.getString("NombreCompleto"));
                    usuario.setUltimoLogin(rs.getTimestamp("UltimoLogin") != null ? rs.getTimestamp("UltimoLogin").toLocalDateTime() : null);
                    
                    return usuario;
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

      
      
      
      
    // Método para actualizar la última hora de inicio de sesión del usuario
    public boolean updateLastLogin(int userId) {
        String sql = "UPDATE usuario SET UltimoLogin = NOW() WHERE UsuarioID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar la última hora de inicio de sesión", e);
            return false;
        }
    }
      
      
      //Consultas a Usuarios
    public List<Usuario> obtenerUsuariosSimplificado() throws SQLException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT UsuarioID, NombreUsuario, Rol, Email, NombreCompleto FROM usuario"; // Asegúrate de que el nombre de la tabla y columnas sean correctos

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(); // Instancia con constructor vacío
                usuario.setUsuarioID(rs.getInt("UsuarioID"));
                usuario.setNombreUsuario(rs.getString("NombreUsuario"));
                usuario.setRol(Usuario.Rol.valueOf(rs.getString("Rol"))); // Suponiendo que 'Rol' es un enum en la clase Usuario
                usuario.setEmail(rs.getString("Email"));
                usuario.setNombreCompleto(rs.getString("NombreCompleto"));
                // Añadir el objeto usuario a la lista
                listaUsuarios.add(usuario);
            }
        }
        return listaUsuarios;
    }

    public String obtenerEmailPorNombreUsuario(String nombreUsuarioBuscado) throws SQLException {
        String email = null;
        String sql = "SELECT Email FROM usuario WHERE NombreUsuario = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuarioBuscado);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                email = rs.getString("Email");
            }
            rs.close();
        }
        return email;
    }



    // Método para actualizar los datos de un usuario
    public boolean actualizarUsuario(int usuarioId, String nombreUsuario, String contraseña, String rol, String email, String nombreCompleto, String preguntaSeguridad, String respuestaSeguridad) {
        String query = "UPDATE usuario SET NombreUsuario = ?, Contraseña = ?, Rol = ?, Email = ?, NombreCompleto = ?, PreguntaSeguridad = ?, RespuestaSeguridad = ? WHERE UsuarioID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contraseña);
            stmt.setString(3, rol);
            stmt.setString(4, email);
            stmt.setString(5, nombreCompleto);
            stmt.setString(6, preguntaSeguridad);
            stmt.setString(7, respuestaSeguridad);
            stmt.setInt(8, usuarioId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un usuario
    public boolean eliminarUsuario(int usuarioId) {
        String query = "DELETE FROM usuario WHERE UsuarioID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, usuarioId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario obtenerDetallesUsuario(int usuarioId) throws SQLException {
        String query = "SELECT UsuarioID, NombreCompleto, Email, Rol, NombreUsuario FROM usuario WHERE UsuarioID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuarioID(rs.getInt("UsuarioID"));
                usuario.setNombreCompleto(rs.getString("NombreCompleto"));
                usuario.setEmail(rs.getString("Email"));
                usuario.setRol(Usuario.Rol.valueOf(rs.getString("Rol"))); // Suponiendo que 'Rol' es un enum en la clase Usuario
                usuario.setNombreUsuario(rs.getString("NombreUsuario"));
                return usuario;
            }
            return null;
        }
    }

    
    public boolean completarVenta(int usuarioId, List<Producto> productos) {
        String sqlVenta = "INSERT INTO ventas (UsuarioID, FechaVenta, TotalVenta) VALUES (?, NOW(), ?)";
        String sqlDetalleVenta = "INSERT INTO detallesventa (VentaID, ProductoID, Cantidad, PrecioUnitario, TotalLinea) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmtVenta = null, stmtDetalleVenta = null;
        ResultSet rs = null;

        try {
            con.setAutoCommit(false);  // Iniciar transacción
            double totalVenta = productos.stream().mapToDouble(p -> p.getPrecio() * p.getCantidad()).sum();

            // Insertar en la tabla ventas
            stmtVenta = con.prepareStatement(sqlVenta, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtVenta.setInt(1, usuarioId);
            stmtVenta.setDouble(2, totalVenta);
            int affectedRows = stmtVenta.executeUpdate();

            if (affectedRows == 0) {
                con.rollback();
                return false;
            }

            int ventaId;
            rs = stmtVenta.getGeneratedKeys();
            if (rs.next()) {
                ventaId = rs.getInt(1);
            } else {
                con.rollback();
                return false;
            }

            
            // Insertar en la tabla detalleventa
            stmtDetalleVenta = con.prepareStatement(sqlDetalleVenta);
            for (Producto producto : productos) {
                stmtDetalleVenta.setInt(1, ventaId);
                stmtDetalleVenta.setInt(2, producto.getProductoID());
                stmtDetalleVenta.setInt(3, producto.getCantidad());
                stmtDetalleVenta.setDouble(4, producto.getPrecio());
                stmtDetalleVenta.setDouble(5, producto.getCantidad() * producto.getPrecio());
                stmtDetalleVenta.addBatch();
                
                  // Actualizar inventario
            if (!actualizarInventario(producto.getProductoID(), -producto.getCantidad())) {
                    con.rollback(); // Si falla la actualización del inventario, revierte todo
                    return false;
                }
            }

            
            stmtDetalleVenta.executeBatch();
            con.commit();  // Finalizar transacción
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmtVenta != null) stmtVenta.close();
                if (stmtDetalleVenta != null) stmtDetalleVenta.close();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean actualizarInventario(int productoID, int cantidad) {
    String sql = "UPDATE productos SET UnidadesDisponibles = UnidadesDisponibles + ? WHERE ProductoID = ?";
    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, cantidad);
        stmt.setInt(2, productoID);
        int affectedRows = stmt.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Error al actualizar inventario", e);
        return false;
    }
}


    public List<Area> obtenerAreas() throws SQLException {
        List<Area> areas = new ArrayList<>();
        String sql = "SELECT AreaID, Nombre FROM area";
        try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Area area = new Area();
                area.setAreaID(rs.getInt("AreaID"));
                area.setNombreArea(rs.getString("Nombre"));
                areas.add(area);
            }
        }
        return areas;
    }

        public boolean actualizarPorcentajeGanancia(int areaID, double nuevoPorcentaje) {
        String sql = "UPDATE area SET GananciaPorcentaje = ? WHERE AreaID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDouble(1, nuevoPorcentaje);
            stmt.setInt(2, areaID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el porcentaje de ganancia", e);
            return false;
        }
    }


        public Map<Integer, Double> obtenerPorcentajesGanancia() throws SQLException {
        Map<Integer, Double> porcentajes = new HashMap<>();
        String sql = "SELECT AreaID, GananciaPorcentaje FROM area";
        try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                porcentajes.put(rs.getInt("AreaID"), rs.getDouble("GananciaPorcentaje"));
            }
        }
        return porcentajes;
    }

   
            public boolean crearProd(String nombre, int areaID, double precio, int unidades, String fecha, int codB, String marca, 
                            String cont) {
        
        String sql = "INSERT INTO productos (Nombre, AreaID, Precio, UnidadesDisponibles, FechaCaducidad, CodigoBarras, "
                    + "Marca, Contenido) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, areaID);
            pstmt.setDouble(3, precio);
            pstmt.setInt(4, unidades);
            pstmt.setString(5, fecha);
            pstmt.setInt(6, codB);
            pstmt.setString(7, marca);
            pstmt.setString(8, cont);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            } catch (SQLException e) {
                e.printStackTrace();  // Imprimir detalles de error
                return false;
            }
            
    }
    
    public boolean actualizarProd(int codigoB, double precio, int unidades){        
        String sql = "UPDATE productos SET Precio=? , UnidadesDisponibles=? WHERE CodigoBarras = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setDouble(1, precio);
            pstmt.setInt(2, unidades);
            pstmt.setInt(3, codigoB);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            //Logger.getLogger(CONSULTASDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo al actualizar");
            return false;
        }
    }
    
    public boolean eliminarProd(int codigoB){
        String sql = "DELETE from productos WHERE CodigoBarras = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, codigoB);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            //Logger.getLogger(CONSULTASDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo al eliminar");
            return false;
        }
    }
    
    
        public int getCod(int x){
        int max = 0;
        String query = "SELECT MAX(CodigoBarras) AS max_valor FROM productos WHERE CAST(CodigoBarras AS CHAR) LIKE '" + x + "%'";
        
        try (PreparedStatement stmt = con.prepareStatement(query)){
            try (ResultSet rs = stmt .executeQuery()){
                if (rs.next()) { // Si hay resultados
                    max = rs.getInt("max_valor"); // Obtenemos el valor máximo
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(CONSULTASDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("QUE WEY SOY");
        }
        
        return max;
    }   
        
        
       public int getUltimaVentaIdPorUsuario(int usuarioId) throws SQLException {
    String query = "SELECT MAX(VentaID) AS UltimaVenta FROM ventas WHERE UsuarioID = ?";
    try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("UltimaVenta");
        } else {
            throw new SQLException("No se encontró ninguna venta para el usuario con ID: " + usuarioId);
        }
    }
}

        
        
    public List<Producto> obtenerDetallesVenta(int ventaId) throws SQLException {
    List<Producto> productos = new ArrayList<>();
    String sql = "SELECT p.ProductoID, p.Nombre, p.Precio, dv.Cantidad, dv.TotalLinea FROM productos p JOIN detallesventa dv ON p.ProductoID = dv.ProductoID WHERE dv.VentaID = ?";
    
    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, ventaId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Producto producto = new Producto();
            producto.setProductoID(rs.getInt("ProductoID"));
            producto.setNombre(rs.getString("Nombre"));
            producto.setPrecio(rs.getDouble("Precio"));
            producto.setCantidad(rs.getInt("Cantidad"));
            productos.add(producto);
        }
    }
    return productos;
}

    
public Producto obtenerProductoPorNombreYContenido(String nombre, String contenido) throws SQLException {
    String query = "SELECT * FROM productos WHERE nombre = ? AND contenido = ?";
    Producto producto = null;

    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setString(1, nombre);
        pstmt.setString(2, contenido);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                producto = new Producto(); // Uso del constructor vacío
                producto.setProductoID(rs.getInt("ProductoID"));
                producto.setNombre(rs.getString("Nombre"));
                producto.setContenido(rs.getString("Contenido"));
            }
        }
    }

    return producto; // Devuelve el producto encontrado o null si no se encontró
}


  //Conultas para las graficas 

    public Map<String, Integer> obtenerVentasPorProducto(Date fechaInicio, Date fechaFin) {
        Map<String, Integer> ventas = new HashMap<>();
        String sql = "SELECT p.Nombre, SUM(d.Cantidad) AS TotalVendido " +
                     "FROM ventas v " +
                     "JOIN detallesventa d ON v.VentaID = d.VentaID " +
                     "JOIN productos p ON p.ProductoID = d.ProductoID " +
                     "WHERE v.FechaVenta BETWEEN ? AND ? " +
                     "GROUP BY p.Nombre";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDate(1, fechaInicio);
            pstmt.setDate(2, fechaFin);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nombreProducto = rs.getString("Nombre");
                    int totalVendido = rs.getInt("TotalVendido");
                    ventas.put(nombreProducto, totalVendido);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener las ventas por producto", e);
        }

        return ventas;
    }
    
    public Map<String, Integer> obtenerVentasPorEmpleado(Date fechaInicio, Date fechaFin) {
        Map<String, Integer> ventasEmpleado = new HashMap<>();
        String sql = "SELECT u.NombreUsuario, SUM(d.Cantidad * d.PrecioUnitario) AS TotalVentas " +
                     "FROM ventas v " +
                     "JOIN detallesventa d ON v.VentaID = d.VentaID " +
                     "JOIN usuario u ON v.UsuarioID = u.UsuarioID " +
                     "WHERE v.FechaVenta BETWEEN ? AND ? " +
                     "GROUP BY u.NombreUsuario " +
                     "ORDER BY TotalVentas DESC";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            pstmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nombreEmpleado = rs.getString("NombreUsuario"); // Corregido para usar el alias correcto
                    int totalVentas = rs.getInt("TotalVentas");
                    ventasEmpleado.put(nombreEmpleado, totalVentas);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener las ventas por empleado", e);
        }
        return ventasEmpleado;
    }

    public Map<String, Integer> obtenerProductosMenosVendidos(Date fechaInicio, Date fechaFin) {
        Map<String, Integer> productosMenosVendidos = new HashMap<>();
        String sql = "SELECT p.Nombre, SUM(d.Cantidad) AS TotalVendido " +
                     "FROM detallesventa d " +
                     "JOIN productos p ON d.ProductoID = p.ProductoID " +
                     "JOIN ventas v ON d.VentaID = v.VentaID " +
                     "WHERE v.FechaVenta BETWEEN ? AND ? " +
                     "GROUP BY p.Nombre " +
                     "ORDER BY SUM(d.Cantidad) ASC " + // Orden ascendente para los menos vendidos
                     "LIMIT 10"; // Limitar a 10 para no sobrecargar el gráfico

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            pstmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nombreProducto = rs.getString("Nombre");
                    int totalVendido = rs.getInt("TotalVendido");
                    productosMenosVendidos.put(nombreProducto, totalVendido);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener los productos menos vendidos", e);
        }
        return productosMenosVendidos;
    }

    
    public LocalDate obtenerCaducidad(String codBarras) {
        // Consulta SQL para obtener la fecha de caducidad del producto según su código de barras
        String sql = "select FechaCaducidad from productos where CodigoBarras = ?";
        LocalDate fecha = null;

        try (PreparedStatement psmt = con.prepareStatement(sql)) {
            psmt.setString(1, codBarras);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                // Obtener la fecha de caducidad del ResultSet y convertirla a LocalDate
                Date sqlDate = rs.getDate("FechaCaducidad");
                if (sqlDate != null) {
                    fecha = sqlDate.toLocalDate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Maneja la excepción como consideres adecuado
        }
        return fecha;
    }


    // ]]cOSNULTAS PARA REPORTE 
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuarioID(rs.getInt("UsuarioID"));
                usuario.setNombreUsuario(rs.getString("NombreUsuario"));
                usuario.setRol(Usuario.Rol.valueOf(rs.getString("Rol")));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
    
    
public Map<String, Integer> obtenerVentasDiariasPorEmpleado(Date fecha) {
    Map<String, Integer> ventasDiarias = new HashMap<>();
    String sql = "SELECT u.NombreUsuario, SUM(d.Cantidad * d.PrecioUnitario) AS TotalVentas " +
                 "FROM ventas v " +
                 "JOIN detallesventa d ON v.VentaID = d.VentaID " +
                 "JOIN usuario u ON v.UsuarioID = u.UsuarioID " +
                 "WHERE DATE(v.FechaVenta) = ? " +
                 "GROUP BY u.NombreUsuario";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setDate(1, new java.sql.Date(fecha.getTime()));
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ventasDiarias.put(rs.getString("NombreUsuario"), rs.getInt("TotalVentas"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ventasDiarias;
}


public Map<String, Integer> obtenerVentasSemanalesPorEmpleado(Date fechaInicio, Date fechaFin) {
    Map<String, Integer> ventasSemanales = new HashMap<>();
    String sql = "SELECT u.NombreUsuario, SUM(d.Cantidad * d.PrecioUnitario) AS TotalVentas " +
                 "FROM ventas v " +
                 "JOIN detallesventa d ON v.VentaID = d.VentaID " +
                 "JOIN usuario u ON v.UsuarioID = u.UsuarioID " +
                 "WHERE v.FechaVenta BETWEEN ? AND ? " +
                 "GROUP BY u.NombreUsuario";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
        pstmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ventasSemanales.put(rs.getString("NombreUsuario"), rs.getInt("TotalVentas"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ventasSemanales;
}


public Map<String, Integer> obtenerVentasMensualesPorEmpleado(Date fechaInicio, Date fechaFin) {
    Map<String, Integer> ventasMensuales = new HashMap<>();
    String sql = "SELECT u.NombreUsuario, SUM(d.Cantidad * d.PrecioUnitario) AS TotalVentas " +
                 "FROM ventas v " +
                 "JOIN detallesventa d ON v.VentaID = d.VentaID " +
                 "JOIN usuario u ON v.UsuarioID = u.UsuarioID " +
                 "WHERE v.FechaVenta BETWEEN ? AND ? " +
                 "GROUP BY u.NombreUsuario";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
        pstmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ventasMensuales.put(rs.getString("NombreUsuario"), rs.getInt("TotalVentas"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ventasMensuales;
}

    






   public Map<String, Integer> obtenerProductosVendidosDiariosPorEmpleado(Date fecha) {
        Map<String, Integer> productosVendidosDiarios = new HashMap<>();
        String sql = "SELECT u.NombreUsuario, SUM(d.Cantidad) AS TotalProductos " +
                     "FROM ventas v " +
                     "JOIN detallesventa d ON v.VentaID = d.VentaID " +
                     "JOIN usuario u ON v.UsuarioID = u.UsuarioID " +
                     "WHERE DATE(v.FechaVenta) = ? " +
                     "GROUP BY u.NombreUsuario";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(fecha.getTime()));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    productosVendidosDiarios.put(rs.getString("NombreUsuario"), rs.getInt("TotalProductos"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener los productos vendidos diarios por empleado", e);
        }

        return productosVendidosDiarios;
    }

    public Map<String, Integer> obtenerProductosVendidosSemanalesPorEmpleado(Date fechaInicio, Date fechaFin) {
        Map<String, Integer> productosVendidosSemanales = new HashMap<>();
        String sql = "SELECT u.NombreUsuario, SUM(d.Cantidad) AS TotalProductos " +
                     "FROM ventas v " +
                     "JOIN detallesventa d ON v.VentaID = d.VentaID " +
                     "JOIN usuario u ON v.UsuarioID = u.UsuarioID " +
                     "WHERE v.FechaVenta BETWEEN ? AND ? " +
                     "GROUP BY u.NombreUsuario";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            pstmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    productosVendidosSemanales.put(rs.getString("NombreUsuario"), rs.getInt("TotalProductos"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener los productos vendidos semanales por empleado", e);
        }

        return productosVendidosSemanales;
    }

    public Map<String, Integer> obtenerProductosVendidosMensualesPorEmpleado(Date fechaInicio, Date fechaFin) {
        Map<String, Integer> productosVendidosMensuales = new HashMap<>();
        String sql = "SELECT u.NombreUsuario, SUM(d.Cantidad) AS TotalProductos " +
                     "FROM ventas v " +
                     "JOIN detallesventa d ON v.VentaID = d.VentaID " +
                     "JOIN usuario u ON v.UsuarioID = u.UsuarioID " +
                     "WHERE v.FechaVenta BETWEEN ? AND ? " +
                     "GROUP BY u.NombreUsuario";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            pstmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    productosVendidosMensuales.put(rs.getString("NombreUsuario"), rs.getInt("TotalProductos"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener los productos vendidos mensuales por empleado", e);
        }

        return productosVendidosMensuales;
    }



    
   public static void main(String[] args) {
        try {
            Connection conn = Conexion_DB.getConexion();
            CONSULTASDAO dao = new CONSULTASDAO(conn);

            // Usa java.text.SimpleDateFormat y java.util.Date aquí
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaInicioUtil = sdf.parse("2024-05-01");
            java.util.Date fechaFinUtil = sdf.parse("2024-05-31");

            // Convierte java.util.Date a java.sql.Date para la consulta SQL
            Date fechaInicio = new Date(fechaInicioUtil.getTime());
            Date fechaFin = new Date(fechaFinUtil.getTime());

            // Llama a la función y muestra los resultados
            Map<String, Integer> ventasEmpleado = dao.obtenerVentasPorEmpleado(fechaInicio, fechaFin);
            ventasEmpleado.forEach((nombre, total) -> {
                System.out.println("Empleado: " + nombre + ", Total Ventas: " + total);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
    
    
        /*
       public static void main(String[] args) {
        try {
            Connection con = Conexion_DB.getConexion(); // Asegúrate de que este método está correctamente implementado.
            CONSULTASDAO consultas = new CONSULTASDAO(con);
            Date fechaInicio = Date.valueOf("2024-05-22"); // Ajusta las fechas según tu necesidad
            Date fechaFin = Date.valueOf("2024-05-25");
            Map<String, Integer> ventas = consultas.obtenerVentasPorProducto(fechaInicio, fechaFin);
            ventas.forEach((nombreProducto, totalVendido) -> System.out.println("Producto: " + nombreProducto + ", Total Vendido: " + totalVendido));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
    
}


