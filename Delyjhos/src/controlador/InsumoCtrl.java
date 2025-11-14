/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.dao.InsumoDAO;
import modelo.entidad.Insumo;

/**
 *
 * @author jhery
 */
public class InsumoCtrl {
    private InsumoDAO insumoDAO;

    public InsumoCtrl() {
        this.insumoDAO = new InsumoDAO();
    }

    // Metodo para AGREGAR un insumo (Lo llamara la Vista)
    public boolean agregarInsumo(String nombre, String descripcion, String unidad, double stock, double stockMinimo, Date fechaCaducidad, String estado) {
        
        if (nombre.isEmpty() || unidad.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y Unidad son obligatorios.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (stock < 0 || stockMinimo < 0) {
            JOptionPane.showMessageDialog(null, "El stock no puede ser negativo.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //Crear el objeto entidad
        Insumo insumo = new Insumo();
        insumo.setNombre(nombre);
        insumo.setDescripcion(descripcion);
        insumo.setUnidad_medida(unidad);
        insumo.setStock_actual(stock);
        insumo.setStock_minimo(stockMinimo);
        insumo.setFecha_caducidad(fechaCaducidad);
        insumo.setEstado(estado);

        // Llamar al Modelo (DAO)
        if (insumoDAO.agregarInsumo(insumo)) {
            JOptionPane.showMessageDialog(null, "Insumo agregado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar el insumo.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Metodo para LISTAR todos los insumos (Lo llamara la Vista)
    public List<Insumo> listarInsumos() {
        // Simplemente llama al DAO y devuelve la lista
        return insumoDAO.listarInsumos();
    }
    
    // Metodo para ACTUALIZAR un insumo (Lo llamara la Vista)
    public boolean actualizarInsumo(int id, String nombre, String descripcion, String unidad, double stock, double stockMinimo, Date fechaCaducidad, String estado) {
        
        // (Aqui van validaciones similares a las de agregar)

        Insumo insumo = new Insumo();
        insumo.setId_insumo(id);
        insumo.setNombre(nombre);
        insumo.setDescripcion(descripcion);
        insumo.setUnidad_medida(unidad);
        insumo.setStock_actual(stock);
        insumo.setStock_minimo(stockMinimo);
        insumo.setFecha_caducidad(fechaCaducidad);
        insumo.setEstado(estado);

        if (insumoDAO.actualizarInsumo(insumo)) {
            JOptionPane.showMessageDialog(null, "Insumo actualizado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el insumo.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Método para ELIMINAR un insumo (Lo llamara la Vista)
    public boolean eliminarInsumo(int id) {
        if (insumoDAO.eliminarInsumo(id)) {
            JOptionPane.showMessageDialog(null, "Insumo eliminado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar el insumo. (Verificar que no este en uso).", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
}
