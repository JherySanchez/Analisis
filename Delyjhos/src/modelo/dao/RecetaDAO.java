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
import modelo.entidad.Producto;
import modelo.entidad.Receta;

/**
 *
 * @author jhery
 */
public class RecetaDAO {
    private Connection cnn;

    public RecetaDAO() {
        this.cnn = ConexionDB.getConnection();
    }

    /**
     * Agrega un ingrediente (insumo) a la receta de un producto.
     * @param receta El objeto Receta que contiene el producto, el insumo y la cantidad.
     * @return true si se agregó correctamente, false si falló.
     */
    public boolean guardarReceta(int idProducto, List<Receta> listaIngredientes) {
        String sqlDelete = "DELETE FROM RECETA WHERE id_producto = ?";
        String sqlInsert = "INSERT INTO RECETA (id_producto, id_insumo, cantidad_necesaria) VALUES (?, ?, ?)";

        PreparedStatement psDel = null;
        PreparedStatement psIns = null;

        try {
            cnn.setAutoCommit(false); // INICIO TRANSACCIÓN

            // 1. Borrar receta anterior para sobreescribir limpia
            psDel = cnn.prepareStatement(sqlDelete);
            psDel.setInt(1, idProducto);
            psDel.executeUpdate();

            // 2. Insertar los nuevos ingredientes
            psIns = cnn.prepareStatement(sqlInsert);
            for (Receta r : listaIngredientes) {
                psIns.setInt(1, idProducto);
                // Usamos el objeto Insumo para obtener el ID
                psIns.setInt(2, r.getInsumo().getId_insumo()); 
                psIns.setDouble(3, r.getCantidad_necesaria());
                psIns.addBatch(); // Añadir al lote
            }
            psIns.executeBatch(); // Ejecutar todo junto

            cnn.commit(); // CONFIRMAR
            return true;

        } catch (SQLException e) {
            try { cnn.rollback(); } catch (SQLException ex) {}
            System.err.println("Error al guardar receta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (psDel != null) psDel.close();
                if (psIns != null) psIns.close();
                cnn.setAutoCommit(true);
            } catch (SQLException e) {}
        }
    }

    // --- MÉTODO 2: OBTENER RECETA (Para verla en la tabla) ---
    public List<Receta> obtenerRecetaPorProducto(int idProducto) {
        List<Receta> lista = new ArrayList<>();
        // Hacemos JOIN con INSUMO para obtener el nombre y unidad
        String sql = "SELECT r.id_receta, r.cantidad_necesaria, r.id_insumo, i.nombre, i.unidad_medida " +
                     "FROM RECETA r " +
                     "INNER JOIN INSUMO i ON r.id_insumo = i.id_insumo " +
                     "WHERE r.id_producto = ?";

        try (PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receta r = new Receta();
                    r.setId_receta(rs.getInt("id_receta"));
                    // No seteamos producto aquí porque ya sabemos cuál es, pero sí el insumo
                    r.setCantidad_necesaria(rs.getDouble("cantidad_necesaria"));
                    
                    // Reconstruimos el objeto Insumo para mostrar su nombre en la tabla
                    Insumo ins = new Insumo();
                    ins.setId_insumo(rs.getInt("id_insumo"));
                    ins.setNombre(rs.getString("nombre"));
                    ins.setUnidad_medida(rs.getString("unidad_medida"));
                    
                    r.setInsumo(ins); 
                    
                    lista.add(r);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener receta: " + e.getMessage());
        }
        return lista;
    }
    
    public boolean registrarProduccion(int idProducto, int cantidadProducir, int idUsuario) {
        // 1. Obtener los ingredientes que necesito
        String sqlReceta = "SELECT id_insumo, cantidad_necesaria FROM RECETA WHERE id_producto = ?";
        
        // 2. Verificar Stock y Descontar Insumos
        String sqlVerifStock = "SELECT stock_actual, nombre FROM INSUMO WHERE id_insumo = ?";
        String sqlUpdateInsumo = "UPDATE INSUMO SET stock_actual = stock_actual - ? WHERE id_insumo = ?";
        String sqlMovSalida = "INSERT INTO MOVIMIENTO_INVENTARIO (fecha_movimiento, tipo_movimiento, cantidad, id_usuario, id_insumo) VALUES (NOW(), 'Salida por Producción', ?, ?, ?)";
        
        // 3. Aumentar Stock del Producto
        String sqlUpdateProducto = "UPDATE PRODUCTO SET stock_actual = stock_actual + ? WHERE id_producto = ?";
        // Opcional: Registrar la entrada del producto terminado en el historial
        String sqlMovEntrada = "INSERT INTO MOVIMIENTO_INVENTARIO (fecha_movimiento, tipo_movimiento, cantidad, id_usuario, id_producto) VALUES (NOW(), 'Entrada por Producción', ?, ?, ?)";

        PreparedStatement psReceta = null;
        PreparedStatement psVerif = null;
        PreparedStatement psUpdIns = null;
        PreparedStatement psMovSal = null;
        PreparedStatement psUpdProd = null;
        PreparedStatement psMovEnt = null;
        ResultSet rsReceta = null;
        ResultSet rsVerif = null;

        try {
            cnn.setAutoCommit(false); // INICIO TRANSACCIÓN BLINDADA

            // A. Recuperar la receta
            psReceta = cnn.prepareStatement(sqlReceta);
            psReceta.setInt(1, idProducto);
            rsReceta = psReceta.executeQuery();

            // Preparamos los statements que usaremos en el bucle
            psVerif = cnn.prepareStatement(sqlVerifStock);
            psUpdIns = cnn.prepareStatement(sqlUpdateInsumo);
            psMovSal = cnn.prepareStatement(sqlMovSalida);

            boolean tieneIngredientes = false;

            // B. Recorrer cada ingrediente
            while (rsReceta.next()) {
                tieneIngredientes = true;
                int idInsumo = rsReceta.getInt("id_insumo");
                double cantPorUnidad = rsReceta.getDouble("cantidad_necesaria");
                
                // Cuánto necesito en total (ej. 0.5kg * 10 tortas = 5kg)
                double totalNecesario = cantPorUnidad * cantidadProducir;

                // C. Verificar si alcanza el stock
                psVerif.setInt(1, idInsumo);
                rsVerif = psVerif.executeQuery();
                if (rsVerif.next()) {
                    double stockActual = rsVerif.getDouble("stock_actual");
                    String nombreInsumo = rsVerif.getString("nombre");

                    if (stockActual < totalNecesario) {
                        throw new SQLException("Stock insuficiente de: " + nombreInsumo + 
                                               ". Requieres: " + totalNecesario + 
                                               ", Tienes: " + stockActual);
                    }
                }

                // D. Descontar Insumo
                psUpdIns.setDouble(1, totalNecesario);
                psUpdIns.setInt(2, idInsumo);
                psUpdIns.executeUpdate();

                // E. Guardar Historial (Salida de Insumo)
                psMovSal.setDouble(1, totalNecesario);
                psMovSal.setInt(2, idUsuario);
                psMovSal.setInt(3, idInsumo);
                psMovSal.executeUpdate();
            }

            if (!tieneIngredientes) {
                throw new SQLException("Este producto no tiene una receta definida. Configure la receta primero.");
            }

            // F. Aumentar Stock del Producto Terminado
            psUpdProd = cnn.prepareStatement(sqlUpdateProducto);
            psUpdProd.setInt(1, cantidadProducir);
            psUpdProd.setInt(2, idProducto);
            psUpdProd.executeUpdate();
            
            // G. Guardar Historial (Entrada de Producto)
            psMovEnt = cnn.prepareStatement(sqlMovEntrada);
            psMovEnt.setDouble(1, cantidadProducir);
            psMovEnt.setInt(2, idUsuario);
            psMovEnt.setInt(3, idProducto);
            psMovEnt.executeUpdate();

            cnn.commit(); // CONFIRMAR CAMBIOS
            return true;

        } catch (SQLException e) {
            try { cnn.rollback(); } catch (SQLException ex) {} // Deshacer si algo falló
            System.err.println("Error en producción: " + e.getMessage());
            // Relanzamos la excepción para mostrar el mensaje específico (ej. "Stock insuficiente") en la vista
            // O podemos capturarla en el Controller. Vamos a imprimirla aquí y devolver false, 
            // pero lo ideal es propagar el mensaje.
            // Para simplicidad en este ejemplo, usaremos un truco en el controlador o imprimimos en consola.
            javax.swing.JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error de Producción", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (rsReceta != null) rsReceta.close();
                if (rsVerif != null) rsVerif.close();
                if (psReceta != null) psReceta.close();
                if (psVerif != null) psVerif.close();
                if (psUpdIns != null) psUpdIns.close();
                if (psMovSal != null) psMovSal.close();
                if (psUpdProd != null) psUpdProd.close();
                if (psMovEnt != null) psMovEnt.close();
                cnn.setAutoCommit(true);
            } catch (SQLException e) {}
        }
    }
    
}
