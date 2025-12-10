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
import modelo.entidad.Insumo;
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
    public boolean registrarPedido(Pedido pedido, List<PedidoDetalle> detalles) {
        String sqlPedido = "INSERT INTO PEDIDO (id_proveedor, total, estado_pedido, fecha_pedido) VALUES (?, ?, ?, NOW())";
        String sqlDetalle = "INSERT INTO PEDIDO_DETALLE (id_pedido, id_insumo, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement psPedido = null;
        PreparedStatement psDetalle = null;

        try {
            cnn.setAutoCommit(false); // INICIO TRANSACCIÓN

            // 1. Insertar Cabecera (PEDIDO)
            psPedido = cnn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psPedido.setInt(1, pedido.getProveedor().getId_proveedor());
            psPedido.setDouble(2, pedido.getTotal());
            psPedido.setString(3, "Pendiente"); // Estado inicial según Caso de Uso
            
            int filas = psPedido.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se pudo guardar el pedido.");
            }

            // 2. Obtener el ID generado del Pedido
            int idPedidoGenerado = -1;
            try (ResultSet rsKeys = psPedido.getGeneratedKeys()) {
                if (rsKeys.next()) {
                    idPedidoGenerado = rsKeys.getInt(1);
                } else {
                    throw new SQLException("No se obtuvo el ID del pedido.");
                }
            }

            // 3. Insertar Detalles (Loop)
            psDetalle = cnn.prepareStatement(sqlDetalle);
            for (PedidoDetalle d : detalles) {
                psDetalle.setInt(1, idPedidoGenerado);
                psDetalle.setInt(2, d.getInsumo().getId_insumo());
                psDetalle.setInt(3, d.getCantidad());
                psDetalle.setDouble(4, d.getPrecio_unitario());
                psDetalle.setDouble(5, d.getSubtotal());
                psDetalle.addBatch(); // Agregar al lote
            }
            psDetalle.executeBatch(); // Ejecutar lote

            cnn.commit(); // CONFIRMAR TODO
            return true;

        } catch (SQLException e) {
            System.err.println("Error transacción pedido: " + e.getMessage());
            try { cnn.rollback(); } catch (SQLException ex) {} // Deshacer si falla
            return false;
        } finally {
            try {
                if (psPedido != null) psPedido.close();
                if (psDetalle != null) psDetalle.close();
                cnn.setAutoCommit(true);
            } catch (SQLException e) {}
        }
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.id_pedido, p.fecha_pedido, p.total, p.estado_pedido, " +
                     "pr.id_proveedor, pr.nombre AS nombre_proveedor " +
                     "FROM PEDIDO p " +
                     "INNER JOIN PROVEEDOR pr ON p.id_proveedor = pr.id_proveedor " +
                     "ORDER BY p.fecha_pedido DESC";

        try (PreparedStatement ps = cnn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId_pedido(rs.getInt("id_pedido"));
                p.setFecha_pedido(rs.getTimestamp("fecha_pedido"));
                p.setTotal(rs.getDouble("total"));
                p.setEstado_pedido(rs.getString("estado_pedido"));

                Proveedor prov = new Proveedor();
                prov.setId_proveedor(rs.getInt("id_proveedor"));
                prov.setNombre(rs.getString("nombre_proveedor"));
                p.setProveedor(prov);

                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        }
        return lista;
    }
    
    // Aquí podrías agregar el método para "Recibir Pedido"
    // que actualiza el estado del pedido y el stock de insumos,
    // también usando una transacción.
    // Meetodo para cofirmr pedido
    public boolean confirmarRecepcionPedido(int idPedido, int idUsuario) {
        String sqlVerificar = "SELECT estado_pedido FROM PEDIDO WHERE id_pedido = ?";
        String sqlUpdateEstado = "UPDATE PEDIDO SET estado_pedido = 'Recibido' WHERE id_pedido = ?";
        String sqlGetDetalles = "SELECT id_insumo, cantidad FROM PEDIDO_DETALLE WHERE id_pedido = ?";
        String sqlUpdateStock = "UPDATE INSUMO SET stock_actual = stock_actual + ? WHERE id_insumo = ?";
        String sqlInsertMov = "INSERT INTO MOVIMIENTO_INVENTARIO (fecha_movimiento, tipo_movimiento, cantidad, id_usuario, id_insumo) VALUES (NOW(), 'Entrada por Compra', ?, ?, ?)";

        PreparedStatement psVerif = null;
        PreparedStatement psEstado = null;
        PreparedStatement psDetalles = null;
        PreparedStatement psStock = null;
        PreparedStatement psMov = null;
        ResultSet rs = null;
        ResultSet rsDetalles = null;

        try {
            cnn.setAutoCommit(false); // 1. INICIO TRANSACCIÓN

            // A. Verificar que no haya sido recibido antes (Evitar stock duplicado)
            psVerif = cnn.prepareStatement(sqlVerificar);
            psVerif.setInt(1, idPedido);
            rs = psVerif.executeQuery();
            if (rs.next()) {
                String estado = rs.getString("estado_pedido");
                if ("Recibido".equalsIgnoreCase(estado)) {
                    return false; // Ya estaba recibido, abortamos.
                }
            }

            // B. Cambiar estado del Pedido a 'Recibido'
            psEstado = cnn.prepareStatement(sqlUpdateEstado);
            psEstado.setInt(1, idPedido);
            psEstado.executeUpdate();

            // C. Obtener los insumos de este pedido
            psDetalles = cnn.prepareStatement(sqlGetDetalles);
            psDetalles.setInt(1, idPedido);
            rsDetalles = psDetalles.executeQuery();

            // Preparamos las consultas repetitivas
            psStock = cnn.prepareStatement(sqlUpdateStock);
            psMov = cnn.prepareStatement(sqlInsertMov);

            // D. Bucle: Por cada insumo del pedido...
            while (rsDetalles.next()) {
                int idInsumo = rsDetalles.getInt("id_insumo");
                int cantidad = rsDetalles.getInt("cantidad");

                // 1. Aumentar Stock Real
                psStock.setDouble(1, cantidad);
                psStock.setInt(2, idInsumo);
                psStock.executeUpdate();

                // 2. Guardar en Historial (Movimientos)
                psMov.setDouble(1, cantidad);
                psMov.setInt(2, idUsuario); // Quién recibió la carga
                psMov.setInt(3, idInsumo);
                psMov.executeUpdate();
            }

            cnn.commit(); // 2. CONFIRMAR CAMBIOS (Éxito)
            return true;

        } catch (SQLException e) {
            try { cnn.rollback(); } catch (SQLException ex) {} // Deshacer si falla
            System.err.println("Error al recepcionar pedido: " + e.getMessage());
            return false;
        } finally {
            // Cerrar todo para liberar memoria
            try {
                if (rs != null) rs.close();
                if (rsDetalles != null) rsDetalles.close();
                if (psVerif != null) psVerif.close();
                if (psEstado != null) psEstado.close();
                if (psDetalles != null) psDetalles.close();
                if (psStock != null) psStock.close();
                if (psMov != null) psMov.close();
                cnn.setAutoCommit(true);
            } catch (SQLException e) {}
        }
    }
    
    public List<PedidoDetalle> obtenerDetallesPedido(int idPedido) {
        List<PedidoDetalle> lista = new ArrayList<>();
        // Hacemos JOIN con INSUMO para obtener el nombre y unidad
        String sql = "SELECT d.id_pedido, d.id_insumo, d.cantidad, d.precio_unitario, d.subtotal, " +
                     "i.nombre, i.unidad_medida " +
                     "FROM PEDIDO_DETALLE d " +
                     "INNER JOIN INSUMO i ON d.id_insumo = i.id_insumo " +
                     "WHERE d.id_pedido = ?";

        try (PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PedidoDetalle det = new PedidoDetalle();
                    det.setCantidad(rs.getInt("cantidad"));
                    det.setPrecio_unitario(rs.getDouble("precio_unitario"));
                    det.setSubtotal(rs.getDouble("subtotal"));

                    // Reconstruir el objeto Insumo completo
                    Insumo ins = new Insumo();
                    ins.setId_insumo(rs.getInt("id_insumo"));
                    ins.setNombre(rs.getString("nombre"));
                    ins.setUnidad_medida(rs.getString("unidad_medida"));
                    
                    det.setInsumo(ins); // Guardamos el objeto Insumo dentro del detalle
                    
                    lista.add(det);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles: " + e.getMessage());
        }
        return lista;
    }
}
