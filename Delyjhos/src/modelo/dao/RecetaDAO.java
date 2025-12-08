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
    public boolean agregarIngredienteAReceta(Receta receta) {
        String sql = "INSERT INTO RECETA (id_producto, id_insumo, cantidad_necesaria) VALUES (?, ?, ?)";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setInt(1, receta.getProducto().getId_producto());
            stmt.setInt(2, receta.getInsumo().getId_insumo());
            stmt.setDouble(3, receta.getCantidad_necesaria());
            
            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al agregar ingrediente a receta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    public List<Receta> listarRecetaPorProducto(int idProducto) {
        String sql = "SELECT * FROM RECETA r " +
                     "INNER JOIN INSUMO i ON r.id_insumo = i.id_insumo " +
                     "WHERE r.id_producto = ?";
        
        List<Receta> recetaCompleta = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setInt(1, idProducto);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Insumo insumo = new Insumo();
                insumo.setId_insumo(rs.getInt("id_insumo"));
                insumo.setNombre(rs.getString("nombre"));
                insumo.setUnidad_medida(rs.getString("unidad_medida"));
                // ... (puedes llenar más datos del insumo si los necesitas)

                Producto producto = new Producto();
                producto.setId_producto(idProducto);

                Receta itemReceta = new Receta();
                itemReceta.setId_receta(rs.getInt("id_receta"));
                itemReceta.setCantidad_necesaria(rs.getDouble("cantidad_necesaria"));
                itemReceta.setInsumo(insumo); // Asignar el insumo completo
                itemReceta.setProducto(producto); // Asignar el producto
                
                recetaCompleta.add(itemReceta);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar receta por producto: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return recetaCompleta;
    }

    public boolean eliminarIngredienteDeReceta(int idReceta) {
        String sql = "DELETE FROM RECETA WHERE id_receta = ?";
        
        PreparedStatement stmt = null;
        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setInt(1, idReceta);

            int filasAfectadas = stmt.executeUpdate();
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al eliminar ingrediente de receta: " + e.getMessage());
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
