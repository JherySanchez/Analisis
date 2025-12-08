/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entidad;

/**
 *
 * @author jhery
 */
public class Receta {
    private int id_receta;
    private Producto producto;
    private Insumo insumo;
    private double cantidad_necesaria;

    public Receta() {
    }

    public Receta(int id_receta, Producto producto, Insumo insumo, double cantidad_necesaria) {
        this.id_receta = id_receta;
        this.producto = producto;
        this.insumo = insumo;
        this.cantidad_necesaria = cantidad_necesaria;
    }

    public int getId_receta() {
        return id_receta;
    }

    public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public double getCantidad_necesaria() {
        return cantidad_necesaria;
    }

    public void setCantidad_necesaria(double cantidad_necesaria) {
        this.cantidad_necesaria = cantidad_necesaria;
    }
    
    
}
