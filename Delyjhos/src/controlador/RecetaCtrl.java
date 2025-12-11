/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import modelo.dao.RecetaDAO;
import modelo.entidad.Insumo;
import modelo.entidad.Producto;
import modelo.entidad.Receta;

/**
 *
 * @author jhery
 */
public class RecetaCtrl {
    private RecetaDAO recetaDAO;

    public RecetaCtrl() {
        this.recetaDAO = new RecetaDAO();
    }

    public boolean guardarReceta(int idProducto, List<Receta> ingredientes) {
        if (idProducto <= 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Seleccione un producto válido.");
            return false;
        }
        if (ingredientes.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "La receta debe tener al menos un ingrediente.");
            return false;
        }
        return recetaDAO.guardarReceta(idProducto, ingredientes);
    }

    // Carga la receta existente
    public List<Receta> obtenerReceta(int idProducto) {
        return recetaDAO.obtenerRecetaPorProducto(idProducto);
    }
    
    // Descuenta insumos y suma stock de producto
    public boolean registrarProduccion(int idProducto, int cantidad, int idUsuario) {
        if (idProducto <= 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Seleccione un producto.");
            return false;
        }
        if (cantidad <= 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0.");
            return false;
        }
        return recetaDAO.registrarProduccion(idProducto, cantidad, idUsuario);
    }
    
}
