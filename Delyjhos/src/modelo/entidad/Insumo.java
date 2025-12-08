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
public class Insumo {
    private int id_insumo;
    private String nombre;
    private String descripcion;
    private String unidad_medida;
    private double stock_actual;
    private double stock_minimo;
    private Date fecha_caducidad;
    private String estado;

    public Insumo() {
    }

    public Insumo(int id_insumo, String nombre, String descripcion, String unidad_medida, double stock_actual, double stock_minimo, Date fecha_caducidad, String estado) {
        this.id_insumo = id_insumo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidad_medida = unidad_medida;
        this.stock_actual = stock_actual;
        this.stock_minimo = stock_minimo;
        this.fecha_caducidad = fecha_caducidad;
        this.estado = estado;
    }

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public double getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(double stock_actual) {
        this.stock_actual = stock_actual;
    }

    public double getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(double stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public Date getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(Date fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
