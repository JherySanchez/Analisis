/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entidad;

import java.util.Date;

/**
 *
 * @author jhery
 */
public class Pedido {
    private int id_pedido;
    private Date fecha_pedido;
    private String estado_pedido;
    private double total;
    private Proveedor proveedor;

    public Pedido() {
    }

    public Pedido(int id_pedido, Date fecha_pedido, String estado_pedido, double total, Proveedor proveedor) {
        this.id_pedido = id_pedido;
        this.fecha_pedido = fecha_pedido;
        this.estado_pedido = estado_pedido;
        this.total = total;
        this.proveedor = proveedor;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getEstado_pedido() {
        return estado_pedido;
    }

    public void setEstado_pedido(String estado_pedido) {
        this.estado_pedido = estado_pedido;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
}
