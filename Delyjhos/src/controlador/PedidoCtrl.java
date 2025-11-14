/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.dao.PedidoDAO;
import modelo.entidad.Pedido;
import modelo.entidad.PedidoDetalle;
import modelo.entidad.Proveedor;

/**
 *
 * @author jhery
 */
public class PedidoCtrl {
    private PedidoDAO pedidoDAO;

    public PedidoCtrl() {
        this.pedidoDAO = new PedidoDAO();
    }

    public boolean agregarPedido(Proveedor proveedor, Date fecha, String estado, double total, List<PedidoDetalle> detalles) {
        
        // Validación de datos
        if (proveedor == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (detalles == null || detalles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El pedido debe tener al menos un insumo.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (total <= 0) {
            JOptionPane.showMessageDialog(null, "El total del pedido no puede ser cero o negativo.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Crear el objeto entidad (Cabecera)
        Pedido pedido = new Pedido();
        pedido.setProveedor(proveedor);
        pedido.setFecha_pedido(fecha);
        pedido.setEstado_pedido(estado);
        pedido.setTotal(total);

        // Llamar al Modelo (DAO)
        if (pedidoDAO.agregarPedido(pedido, detalles)) {
            JOptionPane.showMessageDialog(null, "Pedido agregado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar el pedido.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<Pedido> listarPedidos() {
        return pedidoDAO.listarPedidos();
    }
    
    // Aquí irían otros métodos, como:
    // - public List<PedidoDetalle> verDetalleDePedido(int idPedido) { ... }
    // - public boolean marcarPedidoComoRecibido(int idPedido) { ... }
    //   (Este último sería complejo, ya que debe actualizar el stock de insumos)
    
}
