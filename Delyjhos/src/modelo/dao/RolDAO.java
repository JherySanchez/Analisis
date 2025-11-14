/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConexionDB;
import modelo.entidad.Rol;

/**
 *
 * @author jhery
 */
public class RolDAO {
    private Connection cnn;

    public RolDAO() {
        this.cnn = ConexionDB.getConnection();
    }

    public List<Rol> listarRoles() {
        String sql = "SELECT * FROM ROL";
        
        List<Rol> roles = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = cnn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setId_rol(rs.getInt("id_rol"));
                rol.setNombre_rol(rs.getString("nombre_rol"));
                
                roles.add(rol);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar roles: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return roles;
    }
    
    // Opcional: Se podrían agregar métodos para crear, actualizar o
    // eliminar roles si el sistema lo requiriera, pero usualmente
    // son datos fijos (Administrador, Empleado).
}
