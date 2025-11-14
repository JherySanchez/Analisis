/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import modelo.dao.ReporteDAO;
import modelo.entidad.Insumo;

/**
 *
 * @author jhery
 */
public class ReporteCtrl {
    private ReporteDAO reporteDAO;

    public ReporteCtrl() {
        this.reporteDAO = new ReporteDAO();
    }

    public List<Insumo> generarReporteStockActual() {
        // Este controlador es simple, solo pasa la solicitud al DAO
        return reporteDAO.getReporteStockActual();
    }
    
    // Aqui se agregarian mas metodos que llamen al ReporteDAO
    // public List<MovimientoInventario> generarReporteMovimientos(Date inicio, Date fin) { ... }
    
}
