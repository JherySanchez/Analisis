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
import modelo.entidad.Producto;

/**
 *
 * @author jhery
 */
public class ProductoDAO {
    private Connection cnn;

    public ProductoDAO() {
        this.cnn = ConexionDB.getConnection();
    }

    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO PRODUCTO (nombre, descripcion, precio, categoria, stock_actual, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setString(4, producto.getCategoria());
            stmt.setInt(5, producto.getStock_actual());
            stmt.setString(6, producto.getEstado());

            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al agregar producto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    public List<Producto> listarProductos() {
        String sql = "SELECT * FROM PRODUCTO";
        
        List<Producto> productos = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = cnn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setStock_actual(rs.getInt("stock_actual"));
                producto.setEstado(rs.getString("estado"));
                
                productos.add(producto);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return productos;
    }

    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE PRODUCTO SET nombre = ?, descripcion = ?, precio = ?, " +
                     "categoria = ?, stock_actual = ?, estado = ? " +
                     "WHERE id_producto = ?";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setString(4, producto.getCategoria());
            stmt.setInt(5, producto.getStock_actual());
            stmt.setString(6, producto.getEstado());
            stmt.setInt(7, producto.getId_producto()); // ID para el WHERE

            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM PRODUCTO WHERE id_producto = ?";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setInt(1, idProducto);

            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            // Esto dará error si el producto está siendo usado en RECETA o MOVIMIENTO_INVENTARIO
            System.err.println("Error al eliminar producto: " + e.getMessage());
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
