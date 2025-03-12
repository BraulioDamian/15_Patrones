/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
/**
 *
 * @author braul
 */

public class ConfiguracionRolesDAO {
    private Connection con;

    public ConfiguracionRolesDAO(Connection con) {
        this.con = con;
    }

    public boolean insertarRol(String rol, String contraseña) {
        String contraseñaHash = null;
        if (contraseña != null && !contraseña.isEmpty()) {
            contraseñaHash = HashingUtil.hashPassword(contraseña);
            if (contraseñaHash == null) {
                return false;  // Si el hash falla, no continuar
            }
        }

        String sql = "INSERT INTO ConfiguracionRoles (Rol, ContraseñaToken) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, rol);
            pstmt.setString(2, contraseñaHash);  // Puede insertar null si la contraseña es vacía
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void configurarRoles(Map<String, String> rolesYContraseñas) {
        for (Map.Entry<String, String> entrada : rolesYContraseñas.entrySet()) {
            String rol = entrada.getKey();
            String contraseña = entrada.getValue();
            if (!insertarRol(rol, contraseña)) {
                System.out.println("No se pudo insertar la configuración para el rol: " + rol);
            }
        }
    }

    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/dbtienda", "root", "123456");
            ConfiguracionRolesDAO gestorRoles = new ConfiguracionRolesDAO(con);
            Map<String, String> rolesYContraseñas = new HashMap<>();
            rolesYContraseñas.put("ADMINISTRADOR", "");
            rolesYContraseñas.put("GERENTE", "");
            rolesYContraseñas.put("SUPERVISOR", "");
            rolesYContraseñas.put("EMPLEADO", "");
            gestorRoles.configurarRoles(rolesYContraseñas);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


