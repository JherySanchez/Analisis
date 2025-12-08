/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import modelo.dao.ProductoDAO;
import modelo.entidad.Producto;

/**
 *
 * @author jhery
 */
public class ProductoCtrl {
    private ProductoDAO productoDAO;

    public ProductoCtrl() {
        this.productoDAO = new ProductoDAO();
    }

    // Metodo para AGREGAR un nuevo producto
    public boolean agregarProducto(String nombre, String descripcion, double precio, String categoria, int stockInicial, String estado) {
        
        // Validacion de datos 
        if (nombre.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y Categoria son obligatorios.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (precio < 0 || stockInicial < 0) {
            JOptionPane.showMessageDialog(null, "El precio y el stock no pueden ser negativos.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Crear el objeto entidad 
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCategoria(categoria);
        producto.setStock_actual(stockInicial);
        producto.setEstado(estado);

        // Llamar al Modelo (DAO)
        if (productoDAO.agregarProducto(producto)) {
            JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar el producto.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Metodo para LISTAR todos los productos
    public List<Producto> listarProductos() {
        return productoDAO.listarProductos();
    }

    // Metodo para ACTUALIZAR un producto
    public boolean actualizarProducto(int id, String nombre, String descripcion, double precio, String categoria, int stock, String estado) {
        
        // (Validaciones
        if (nombre.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y Categoria son obligatorios.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Producto producto = new Producto();
        producto.setId_producto(id);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCategoria(categoria);
        producto.setStock_actual(stock);
        producto.setEstado(estado);

        if (productoDAO.actualizarProducto(producto)) {
            JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Metodo para ELIMINAR un producto
    public boolean eliminarProducto(int id) {
        if (productoDAO.eliminarProducto(id)) {
            JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto. (Verificar que no este en uso).", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
}
