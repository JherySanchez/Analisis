/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.dao.MovimientoInventarioDAO;
import modelo.entidad.Insumo;
import modelo.entidad.MovimientoInventario;
import modelo.entidad.Producto;
import modelo.entidad.Usuario;

/**
 *
 * @author jhery
 */
public class MovimientoCtrl {
    private MovimientoInventarioDAO movimientoDAO;

    public MovimientoCtrl() {
        this.movimientoDAO = new MovimientoInventarioDAO();
    }

    public boolean registrarMovimiento(Usuario usuario, Insumo insumo, Producto producto, String tipoMovimiento, double cantidad) {
        
        // --- Validación de datos ---
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "No se puede registrar un movimiento sin un usuario logueado.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (insumo == null && producto == null) {
            JOptionPane.showMessageDialog(null, "El movimiento debe afectar a un insumo o a un producto.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cantidad == 0) {
            JOptionPane.showMessageDialog(null, "La cantidad no puede ser cero.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Crear el objeto entidad
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setUsuario(usuario);
        movimiento.setInsumo(insumo);
        movimiento.setProducto(producto);
        movimiento.setTipo_movimiento(tipoMovimiento);
        movimiento.setCantidad(cantidad);
        movimiento.setFecha_movimiento(new Date());

        // Llamar al Modelo (DAO)
        if (movimientoDAO.registrarMovimiento(movimiento)) {
            System.out.println("Movimiento registrado y stock actualizado.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar el movimiento y actualizar el stock.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean registrarConsumoInsumo(Usuario usuario, Insumo insumo, double cantidad) {
        if (cantidad <= 0) {
             JOptionPane.showMessageDialog(null, "La cantidad debe ser un numero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
             return false;
        }
        return registrarMovimiento(usuario, insumo, null, "Consumo", -cantidad);
    }
    
    public boolean registrarVentaProducto(Usuario usuario, Producto producto, int cantidad) {
         if (cantidad <= 0) {
             JOptionPane.showMessageDialog(null, "La cantidad debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
             return false;
        }
        return registrarMovimiento(usuario, null, producto, "Venta", -cantidad);
    }
    
    public boolean registrarSalida(Insumo insumo, double cantidad, String tipoMovimiento, Usuario usuario) {
        if (insumo == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un insumo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Usuario no identificado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        MovimientoInventario mov = new MovimientoInventario();
        mov.setFecha_movimiento(new Date());
        mov.setTipo_movimiento(tipoMovimiento);
        mov.setCantidad(cantidad);
        mov.setInsumo(insumo);
        mov.setUsuario(usuario);
        mov.setProducto(null);

        boolean ok = movimientoDAO.agregarMovimiento(mov);
        if (ok) {
            JOptionPane.showMessageDialog(null, "Movimiento registrado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el movimiento (verifique stock).", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return ok;
    }

    public List<MovimientoInventario> listarMovimientosPorFecha(Date inicio, Date fin) {
        return movimientoDAO.listarMovimientosPorFecha(inicio, fin);
    }
}
