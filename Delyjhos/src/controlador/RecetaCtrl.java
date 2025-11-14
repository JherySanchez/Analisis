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

    public boolean agregarIngrediente(Producto producto, Insumo insumo, double cantidad) {
        
        // Validación de datos
        if (producto == null || insumo == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un producto y un insumo.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a cero.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Crear el objeto entidad
        Receta itemReceta = new Receta();
        itemReceta.setProducto(producto);
        itemReceta.setInsumo(insumo);
        itemReceta.setCantidad_necesaria(cantidad);

        // Llamar al Modelo (DAO)
        if (recetaDAO.agregarIngredienteAReceta(itemReceta)) {
            JOptionPane.showMessageDialog(null, "Ingrediente agregado a la receta.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar el ingrediente.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<Receta> listarRecetaPorProducto(int idProducto) {
        return recetaDAO.listarRecetaPorProducto(idProducto);
    }

    public boolean eliminarIngrediente(int idReceta) {
        if (recetaDAO.eliminarIngredienteDeReceta(idReceta)) {
            JOptionPane.showMessageDialog(null, "Ingrediente eliminado de la receta.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar el ingrediente.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
}
