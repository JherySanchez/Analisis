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
import modelo.entidad.Insumo;

/**
 *
 * @author jhery
 */
public class InsumoDAO {
    private Connection cnn;

    public InsumoDAO() {
        this.cnn = ConexionDB.getConnection();
    }

    public boolean agregarInsumo(Insumo insumo) {
        String sql = "INSERT INTO INSUMO (nombre, descripcion, unidad_medida, stock_actual, stock_minimo, fecha_caducidad, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, insumo.getNombre());
            stmt.setString(2, insumo.getDescripcion());
            stmt.setString(3, insumo.getUnidad_medida());
            stmt.setDouble(4, insumo.getStock_actual());
            stmt.setDouble(5, insumo.getStock_minimo());
            
            // Convertimos java.util.Date a java.sql.Date
            stmt.setDate(6, new java.sql.Date(insumo.getFecha_caducidad().getTime()));
            
            stmt.setString(7, insumo.getEstado());

            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al agregar insumo: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    public List<Insumo> listarInsumos() {
        String sql = "SELECT * FROM INSUMO";
        
        List<Insumo> insumos = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = cnn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Insumo insumo = new Insumo();
                insumo.setId_insumo(rs.getInt("id_insumo"));
                insumo.setNombre(rs.getString("nombre"));
                insumo.setDescripcion(rs.getString("descripcion"));
                insumo.setUnidad_medida(rs.getString("unidad_medida"));
                insumo.setStock_actual(rs.getDouble("stock_actual"));
                insumo.setStock_minimo(rs.getDouble("stock_minimo"));
                insumo.setFecha_caducidad(rs.getDate("fecha_caducidad")); // rs.getDate() devuelve java.sql.Date
                insumo.setEstado(rs.getString("estado"));
                
                insumos.add(insumo);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar insumos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return insumos;
    }

    public boolean actualizarInsumo(Insumo insumo) {
        String sql = "UPDATE INSUMO SET nombre = ?, descripcion = ?, unidad_medida = ?, " +
                     "stock_actual = ?, stock_minimo = ?, fecha_caducidad = ?, estado = ? " +
                     "WHERE id_insumo = ?";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, insumo.getNombre());
            stmt.setString(2, insumo.getDescripcion());
            stmt.setString(3, insumo.getUnidad_medida());
            stmt.setDouble(4, insumo.getStock_actual());
            stmt.setDouble(5, insumo.getStock_minimo());
            stmt.setDate(6, new java.sql.Date(insumo.getFecha_caducidad().getTime()));
            stmt.setString(7, insumo.getEstado());
            stmt.setInt(8, insumo.getId_insumo());

            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al actualizar insumo: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    public boolean eliminarInsumo(int idInsumo) {
        String sql = "DELETE FROM INSUMO WHERE id_insumo = ?";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setInt(1, idInsumo);

            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al eliminar insumo: " + e.getMessage());
            // Si hay claves foráneas (FK) en otras tablas (ej. RECETA),
            // esto podría dar un error de integridad.
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }
    
    public Insumo obtenerInsumoPorId(int id) {
    String sql = "SELECT * FROM INSUMO WHERE id_insumo = ?";
    
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        stmt = cnn.prepareStatement(sql);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();

        if (rs.next()) {
            Insumo insumo = new Insumo();
            insumo.setId_insumo(rs.getInt("id_insumo"));
            insumo.setNombre(rs.getString("nombre"));
            insumo.setDescripcion(rs.getString("descripcion"));
            insumo.setUnidad_medida(rs.getString("unidad_medida"));
            insumo.setStock_actual(rs.getDouble("stock_actual"));
            insumo.setStock_minimo(rs.getDouble("stock_minimo"));
            insumo.setFecha_caducidad(rs.getDate("fecha_caducidad"));
            insumo.setEstado(rs.getString("estado"));

            return insumo;
        }

    } catch (SQLException e) {
        System.err.println("Error al obtener insumo por ID: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {}
    }

    return null;
    }
    
    public boolean disminuirStock(int idInsumo, double cantidad) {
        String sql = "UPDATE INSUMO SET stock_actual = stock_actual - ? WHERE id_insumo = ? AND stock_actual >= ?";
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setDouble(1, cantidad);
            stmt.setInt(2, idInsumo);
            stmt.setDouble(3, cantidad);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al disminuir stock: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) { stmt.close(); }
            } catch (SQLException e) {
            }
        }
    }
    
    public List<Insumo> listarInsumosProximosACaducar() {
        List<Insumo> lista = new ArrayList<>();

        // Caducan en menos de 30 días
        String sql = "SELECT * FROM insumo WHERE fecha_caducidad <= DATE_ADD(CURDATE(), INTERVAL 30 DAY)";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Insumo i = new Insumo();
                i.setId_insumo(rs.getInt("id_insumo"));
                i.setNombre(rs.getString("nombre"));
                i.setStock_actual(rs.getDouble("stock_actual"));
                i.setFecha_caducidad(rs.getDate("fecha_caducidad"));
                lista.add(i);
            }

        } catch (Exception e) { }

        return lista;
    }

    
}
