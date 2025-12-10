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
import java.util.Date;
import java.util.List;
import modelo.ConexionDB;
import modelo.entidad.Insumo;
import modelo.entidad.MovimientoInventario;
import modelo.entidad.Producto;
import modelo.entidad.Usuario;

/**
 *
 * @author jhery
 */
public class MovimientoInventarioDAO {
    
    private Connection cnn;

    public MovimientoInventarioDAO() {
        this.cnn = ConexionDB.getConnection();
    }

    /**
     * Registra el movimiento y DESCUENTA el stock en una sola transacción segura.Usado por el módulo de Inventario.
     * @param mov
     * @return 
     */
    public boolean registrarMovimiento(MovimientoInventario mov) {
        // Para guardar el historial
        String sqlInsertMov = "INSERT INTO MOVIMIENTO_INVENTARIO (fecha_movimiento, tipo_movimiento, cantidad, id_usuario, id_insumo, id_producto) VALUES (NOW(), ?, ?, ?, ?, ?)";
        
        // Para descontar stock (Validando que no quede negativo)
        String sqlUpdateStock = "UPDATE INSUMO SET stock_actual = stock_actual - ? WHERE id_insumo = ? AND stock_actual >= ?";

        PreparedStatement stmtMov = null;
        PreparedStatement stmtUpd = null;

        try {
            cnn.setAutoCommit(false);

            // Insertar movimiento
            stmtMov = cnn.prepareStatement(sqlInsertMov);
            stmtMov.setString(1, mov.getTipo_movimiento());
            stmtMov.setDouble(2, mov.getCantidad());
            stmtMov.setInt(3, mov.getUsuario().getId_usuario());
            
            // Manejo de Insumo vs Producto
            if (mov.getInsumo() != null) {
                stmtMov.setInt(4, mov.getInsumo().getId_insumo());
                stmtMov.setNull(5, java.sql.Types.INTEGER);
            } else if (mov.getProducto() != null) {
                stmtMov.setNull(4, java.sql.Types.INTEGER);
                stmtMov.setInt(5, mov.getProducto().getId_producto());
            } else {
                stmtMov.setNull(4, java.sql.Types.INTEGER);
                stmtMov.setNull(5, java.sql.Types.INTEGER);
            }
            
            stmtMov.executeUpdate();

            // Descontar stock al insumo
            if (mov.getInsumo() != null) {
                stmtUpd = cnn.prepareStatement(sqlUpdateStock);
                stmtUpd.setDouble(1, mov.getCantidad()); // Cuanto resta
                stmtUpd.setInt(2, mov.getInsumo().getId_insumo()); // A quien
                stmtUpd.setDouble(3, mov.getCantidad()); // Validacion: Stock actual >= cantidad
                
                int filasAfectadas = stmtUpd.executeUpdate();
                
                if (filasAfectadas == 0) {
                    // Si entra aquí, es porque NO HAY STOCK SUFICIENTE
                    throw new SQLException("Stock insuficiente para realizar la salida.");
                }
            }

            cnn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al registrar movimiento: " + e.getMessage());
            try {
                cnn.rollback();
            } catch (SQLException ex) {}
            return false;
        } finally {
            try {
                if (stmtMov != null) stmtMov.close();
                if (stmtUpd != null) stmtUpd.close();
                cnn.setAutoCommit(true);
            } catch (SQLException e) {}
        }
    }

    /**
     * Lista los movimientos con los NOMBRES REALES (JOINs).Usado por el módulo de Reportes.
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
    public List<MovimientoInventario> listarMovimientosPorFecha(java.util.Date fechaInicio, java.util.Date fechaFin) {
        List<MovimientoInventario> lista = new ArrayList<>();
        
        // Esta consulta trae los nombres cruzando las tablas
        String sql = "SELECT m.*, " +
                     "       i.nombre AS nombre_insumo, " +
                     "       p.nombre AS nombre_producto, " +
                     "       u.usuario AS nombre_usuario " +
                     "FROM MOVIMIENTO_INVENTARIO m " +
                     "LEFT JOIN INSUMO i ON m.id_insumo = i.id_insumo " +
                     "LEFT JOIN PRODUCTO p ON m.id_producto = p.id_producto " +
                     "INNER JOIN USUARIO u ON m.id_usuario = u.id_usuario " +
                     "WHERE DATE(m.fecha_movimiento) BETWEEN ? AND ? " +
                     "ORDER BY m.fecha_movimiento DESC";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            stmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                MovimientoInventario m = new MovimientoInventario();
                m.setId_movimiento(rs.getInt("id_movimiento"));
                m.setFecha_movimiento(rs.getTimestamp("fecha_movimiento"));
                m.setTipo_movimiento(rs.getString("tipo_movimiento"));
                m.setCantidad(rs.getDouble("cantidad"));
                
                // Llenamos el Usuario con nombre
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setUsuario(rs.getString("nombre_usuario")); 
                m.setUsuario(u);
                
                // Llenamos el Insumo con nombre (si existe)
                int idInsumo = rs.getInt("id_insumo");
                if (!rs.wasNull()) {
                    Insumo i = new Insumo();
                    i.setId_insumo(idInsumo);
                    i.setNombre(rs.getString("nombre_insumo"));
                    m.setInsumo(i);
                }
                
                // Llenamos el Producto con nombre (si existe)
                int idProd = rs.getInt("id_producto");
                if (!rs.wasNull()) {
                    Producto p = new Producto();
                    p.setId_producto(idProd);
                    p.setNombre(rs.getString("nombre_producto"));
                    m.setProducto(p);
                }
                
                lista.add(m);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar movimientos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {}
        }
        return lista;
    }
}