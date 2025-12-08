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
public class ReporteDAO {
    private Connection cnn;

    public ReporteDAO() {
        this.cnn = ConexionDB.getConnection();
    }

    public List<Insumo> getReporteStockActual() {
        String sql = "SELECT * FROM INSUMO ORDER BY nombre ASC";
        
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
                insumo.setFecha_caducidad(rs.getDate("fecha_caducidad"));
                insumo.setEstado(rs.getString("estado"));
                
                insumos.add(insumo);
            }
        } catch (SQLException e) {
            System.err.println("Error al generar reporte de stock: " + e.getMessage());
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
    
    // Aque se agregarian mas metodos para los otros reportes:
    // public List<MovimientoInventario> getReporteMovimientos(Date fechaInicio, Date fechaFin) { ... }
    // public List<Insumo> getReporteInsumosPorCaducar() { ... }
    
}
