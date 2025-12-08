/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entidad;

/**
 *
 * @author jhery
 */
public class PedidoDetalle {
    private int id_pedido_detalle;
    private Pedido pedido;
    private Insumo insumo;
    private int cantidad;
    private double precio_unitario;
    private double subtotal;

    public PedidoDetalle() {
    }

    public PedidoDetalle(int id_pedido_detalle, Pedido pedido, Insumo insumo, int cantidad, double precio_unitario, double subtotal) {
        this.id_pedido_detalle = id_pedido_detalle;
        this.pedido = pedido;
        this.insumo = insumo;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
    }

    public int getId_pedido_detalle() {
        return id_pedido_detalle;
    }

    public void setId_pedido_detalle(int id_pedido_detalle) {
        this.id_pedido_detalle = id_pedido_detalle;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    
    
}
