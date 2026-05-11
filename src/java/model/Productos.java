/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author JHOSMER
 */
public class Productos {
    private int id_producto;
    private String nombre;
    private String descripcion;
    private double preciuo;
    private int stock;
    private String imagen;

    public Productos() {
    }

    public Productos(int id_producto, String nombre, String descripcion, double preciuo, int stock, String imagen) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preciuo = preciuo;
        this.stock = stock;
        this.imagen = imagen;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
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

    public double getPreciuo() {
        return preciuo;
    }

    public void setPreciuo(double preciuo) {
        this.preciuo = preciuo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    
}
