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
import modelo.entidad.Proveedor;

/**
 *
 * @author jhery
 */
public class ProveedorDAO {
    private Connection cnn;

    public ProveedorDAO() {
        this.cnn = ConexionDB.getConnection();
    }

    public boolean agregarProveedor(Proveedor proveedor) {
        String sql = "INSERT INTO PROVEEDOR (nombre, ruc, telefono, direccion, correo, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getRUC());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getDireccion());
            stmt.setString(5, proveedor.getCorreo());
            stmt.setString(6, proveedor.getEstado());

            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al agregar proveedor: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    public List<Proveedor> listarProveedores() {
        String sql = "SELECT * FROM PROVEEDOR";
        
        List<Proveedor> proveedores = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = cnn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId_proveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setRUC(rs.getString("ruc"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setCorreo(rs.getString("correo"));
                proveedor.setEstado(rs.getString("estado"));
                
                proveedores.add(proveedor);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar proveedores: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return proveedores;
    }

    public boolean actualizarProveedor(Proveedor proveedor) {
        String sql = "UPDATE PROVEEDOR SET nombre = ?, ruc = ?, telefono = ?, " +
                     "direccion = ?, correo = ?, estado = ? " +
                     "WHERE id_proveedor = ?";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getRUC());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getDireccion());
            stmt.setString(5, proveedor.getCorreo());
            stmt.setString(6, proveedor.getEstado());
            stmt.setInt(7, proveedor.getId_proveedor()); // ID para el WHERE

            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    public boolean eliminarProveedor(int idProveedor) {
        String sql = "DELETE FROM PROVEEDOR WHERE id_proveedor = ?";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setInt(1, idProveedor);

            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            // Esto dará error si el proveedor está siendo usado en un PEDIDO
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }
    
}
