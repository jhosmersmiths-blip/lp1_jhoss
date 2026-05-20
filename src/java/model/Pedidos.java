/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author JHOSMER
 */
public class Pedidos {
    private int id_pedido;
    private Persona persona;
    private Double total;
    private EstadoPedido estadopedido;
    private Timestamp fecha;
    private List<Carrito> detallepedido;

    public Pedidos() {
    }

    public Pedidos(int id_pedido, Persona persona, Double total, EstadoPedido estadopedido, Timestamp fecha, List<Carrito> detallepedido) {
        this.id_pedido = id_pedido;
        this.persona = persona;
        this.total = total;
        this.estadopedido = estadopedido;
        this.fecha = fecha;
        this.detallepedido = detallepedido;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoPedido getEstadopedido() {
        return estadopedido;
    }

    public void setEstadopedido(EstadoPedido estadopedido) {
        this.estadopedido = estadopedido;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public List<Carrito> getDetallepedido() {
        return detallepedido;
    }

    public void setDetallepedido(List<Carrito> detallepedido) {
        this.detallepedido = detallepedido;
    }
    
}
