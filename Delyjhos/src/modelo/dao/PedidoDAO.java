/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.ConexionDB;
import modelo.entidad.Pedido;
import modelo.entidad.PedidoDetalle;
import modelo.entidad.Proveedor;

/**
 *
 * @author jhery
 */
public class PedidoDAO {
    private Connection cnn;

    public PedidoDAO() {
        this.cnn = ConexionDB.getConnection();
    }

    /**
     * Guarda un pedido (cabecera) y sus detalles en una sola transacción.
     * @param pedido El objeto Pedido (cabecera) con sus datos.
     * @param detalles La lista de objetos PedidoDetalle.
     * @return true si la transacción fue exitosa, false si falló.
     */
    public boolean agregarPedido(Pedido pedido, List<PedidoDetalle> detalles) {
        
        String sqlPedido = "INSERT INTO PEDIDO (id_proveedor, fecha_pedido, estado_pedido, total) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO PEDIDO_DETALLE (id_pedido, id_insumo, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement stmtPedido = null;
        PreparedStatement stmtDetalle = null;
        ResultSet generatedKeys = null;

        try {
            // --- 1. Iniciar Transacción ---
            // Desactivamos el auto-commit. Nosotros controlaremos la transacción.
            cnn.setAutoCommit(false);

            // --- 2. Insertar la Cabecera (PEDIDO) ---
            stmtPedido = cnn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            stmtPedido.setInt(1, pedido.getProveedor().getId_proveedor());
            stmtPedido.setDate(2, new java.sql.Date(pedido.getFecha_pedido().getTime()));
            stmtPedido.setString(3, pedido.getEstado_pedido());
            stmtPedido.setDouble(4, pedido.getTotal());
            
            stmtPedido.executeUpdate();

            // --- 3. Obtener la ID generada del Pedido ---
            generatedKeys = stmtPedido.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idPedidoGenerado = generatedKeys.getInt(1);
                
                // --- 4. Insertar los Detalles (PEDIDO_DETALLE) ---
                stmtDetalle = cnn.prepareStatement(sqlDetalle);
                
                for (PedidoDetalle detalle : detalles) {
                    stmtDetalle.setInt(1, idPedidoGenerado); // Usamos la ID generada
                    stmtDetalle.setInt(2, detalle.getInsumo().getId_insumo());
                    stmtDetalle.setInt(3, detalle.getCantidad());
                    stmtDetalle.setDouble(4, detalle.getPrecio_unitario());
                    stmtDetalle.setDouble(5, detalle.getSubtotal());
                    
                    // Agregamos el detalle al "batch" (lote)
                    stmtDetalle.addBatch();
                }
                
                // Ejecutamos el lote de detalles
                stmtDetalle.executeBatch();
            } else {
                throw new SQLException("No se pudo obtener la ID del pedido.");
            }

            // --- 5. Si todo fue bien, confirmamos la transacción ---
            cnn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al agregar pedido: " + e.getMessage());
            try {
                // --- 6. Si algo falló, revertimos la transacción ---
                System.err.println("Revertiendo transacción (Rollback)...");
                cnn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (stmtPedido != null) stmtPedido.close();
                if (stmtDetalle != null) stmtDetalle.close();
                cnn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    public List<Pedido> listarPedidos() {
        String sql = "SELECT * FROM PEDIDO p " +
                     "INNER JOIN PROVEEDOR pr ON p.id_proveedor = pr.id_proveedor " +
                     "ORDER BY p.fecha_pedido DESC";
        
        List<Pedido> pedidos = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = cnn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                // Crear el Proveedor
                Proveedor proveedor = new Proveedor();
                proveedor.setId_proveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setRUC(rs.getString("ruc"));
                // ... (puedes llenar más datos del proveedor si los necesitas)

                // Crear el Pedido
                Pedido pedido = new Pedido();
                pedido.setId_pedido(rs.getInt("id_pedido"));
                pedido.setFecha_pedido(rs.getDate("fecha_pedido"));
                pedido.setEstado_pedido(rs.getString("estado_pedido"));
                pedido.setTotal(rs.getDouble("total"));
                
                // Asignar el proveedor al pedido
                pedido.setProveedor(proveedor);
                
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return pedidos;
    }
    
    // Aquí podrías agregar el método para "Recibir Pedido"
    // que actualiza el estado del pedido y el stock de insumos,
    // también usando una transacción.
    
}
