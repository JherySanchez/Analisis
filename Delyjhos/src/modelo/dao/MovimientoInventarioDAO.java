/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.ConexionDB;
import modelo.entidad.MovimientoInventario;

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
     * Registra un movimiento y actualiza el stock correspondiente (Insumo o Producto)
     * en una sola transacción.
     * @param movimiento El objeto MovimientoInventario a registrar.
     * @return true si la transacción fue exitosa, false si falló.
     */
    public boolean registrarMovimiento(MovimientoInventario movimiento) {
        
        String sqlMovimiento = "INSERT INTO MOVIMIENTO_INVENTARIO " +
                               "(id_usuario, id_insumo, id_producto, fecha_movimiento, tipo_movimiento, cantidad) " +
                               "VALUES (?, ?, ?, ?, ?, ?)";
        
        String sqlActualizarStock = "";
        
        PreparedStatement stmtMov = null;
        PreparedStatement stmtStock = null;

        try {
            cnn.setAutoCommit(false);

            stmtMov = cnn.prepareStatement(sqlMovimiento);
            stmtMov.setInt(1, movimiento.getUsuario().getId_usuario());
            
            if (movimiento.getInsumo() != null) {
                stmtMov.setInt(2, movimiento.getInsumo().getId_insumo());
                stmtMov.setNull(3, java.sql.Types.INTEGER);
                
                sqlActualizarStock = "UPDATE INSUMO SET stock_actual = stock_actual " + 
                                     (movimiento.getCantidad() > 0 ? "+ ?" : "- ?") + // Si es positivo (ej: compra) suma, si no (ej: consumo) resta
                                     " WHERE id_insumo = ?";
                
            } else if (movimiento.getProducto() != null) {
                stmtMov.setNull(2, java.sql.Types.INTEGER);
                stmtMov.setInt(3, movimiento.getProducto().getId_producto());
                
                sqlActualizarStock = "UPDATE PRODUCTO SET stock_actual = stock_actual " +
                                     (movimiento.getCantidad() > 0 ? "+ ?" : "- ?") + // Si es positivo (ej: producción) suma, si no (ej: venta) resta
                                     " WHERE id_producto = ?";
            } else {
                throw new SQLException("El movimiento debe estar asociado a un Insumo o un Producto.");
            }
            
            stmtMov.setDate(4, new java.sql.Date(movimiento.getFecha_movimiento().getTime()));
            stmtMov.setString(5, movimiento.getTipo_movimiento());
            stmtMov.setDouble(6, Math.abs(movimiento.getCantidad())); // Siempre guardamos la cantidad en positivo
            
            stmtMov.executeUpdate();

            stmtStock = cnn.prepareStatement(sqlActualizarStock);
            
            stmtStock.setDouble(1, Math.abs(movimiento.getCantidad())); 
            
            if (movimiento.getInsumo() != null) {
                stmtStock.setInt(2, movimiento.getInsumo().getId_insumo());
            } else {
                stmtStock.setInt(2, movimiento.getProducto().getId_producto());
            }
            
            stmtStock.executeUpdate();
            
            cnn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al registrar movimiento: " + e.getMessage());
            try {
                System.err.println("Revertiendo transacción (Rollback)...");
                cnn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (stmtMov != null) stmtMov.close();
                if (stmtStock != null) stmtStock.close();
                cnn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    // Aquí se podrían agregar métodos para listar movimientos (para reportes)
    
}
