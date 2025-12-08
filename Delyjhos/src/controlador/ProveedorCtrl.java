/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import modelo.dao.ProveedorDAO;
import modelo.entidad.Proveedor;

/**
 *
 * @author jhery
 */
public class ProveedorCtrl {
    private ProveedorDAO proveedorDAO;

    public ProveedorCtrl() {
        this.proveedorDAO = new ProveedorDAO();
    }

    // Metodo para AGREGAR un proveedor
    public boolean agregarProveedor(String nombre, String ruc, String telefono, String direccion, String correo, String estado) {
        
        // Validación de datos
        if (nombre.isEmpty() || ruc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y RUC son obligatorios.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // (Aquí se pueden agregar mas validaciones, como longitud de RUC, formato de correo, etc.)

        // Crear el objeto entidad
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(nombre);
        proveedor.setRUC(ruc);
        proveedor.setTelefono(telefono);
        proveedor.setDireccion(direccion);
        proveedor.setCorreo(correo);
        proveedor.setEstado(estado);

        //Llamar al Modelo (DAO)
        if (proveedorDAO.agregarProveedor(proveedor)) {
            JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar el proveedor.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Metodo para LISTAR todos los proveedores
    public List<Proveedor> listarProveedores() {
        return proveedorDAO.listarProveedores();
    }

    // Metodo para ACTUALIZAR un proveedor
    public boolean actualizarProveedor(int id, String nombre, String ruc, String telefono, String direccion, String correo, String estado) {
        
        // Validaciones
        if (nombre.isEmpty() || ruc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y RUC son obligatorios.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Proveedor proveedor = new Proveedor();
        proveedor.setId_proveedor(id); // ¡Importante pasar el ID!
        proveedor.setNombre(nombre);
        proveedor.setRUC(ruc);
        proveedor.setTelefono(telefono);
        proveedor.setDireccion(direccion);
        proveedor.setCorreo(correo);
        proveedor.setEstado(estado);

        if (proveedorDAO.actualizarProveedor(proveedor)) {
            JOptionPane.showMessageDialog(null, "Proveedor actualizado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Metodo para ELIMINAR un proveedor
    public boolean eliminarProveedor(int id) {
        if (proveedorDAO.eliminarProveedor(id)) {
            JOptionPane.showMessageDialog(null, "Proveedor eliminado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor. (Verificar que no este en uso en un Pedido).", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
}
