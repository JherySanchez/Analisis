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

    public boolean registrarPedido(Pedido pedido, List<PedidoDetalle> detalles) {
        // Validaciones básicas
        if (pedido.getProveedor() == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor.");
            return false;
        }
        if (detalles.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "El pedido debe tener al menos un insumo.");
            return false;
        }
        
        return pedidoDAO.registrarPedido(pedido, detalles);
    }

    public List<Pedido> listarPedidos() {
        return pedidoDAO.listarPedidos();
    }
    
    public boolean confirmarRecepcion(int idPedido, int idUsuario) {
        return pedidoDAO.confirmarRecepcionPedido(idPedido, idUsuario);
    }
    
    public List<PedidoDetalle> obtenerDetalles(int idPedido) {
        return pedidoDAO.obtenerDetallesPedido(idPedido);
    }
    // Aquí irían otros métodos, como:
    // - public List<PedidoDetalle> verDetalleDePedido(int idPedido) { ... }
    // - public boolean marcarPedidoComoRecibido(int idPedido) { ... }
    //   (Este último sería complejo, ya que debe actualizar el stock de insumos)
    
}
