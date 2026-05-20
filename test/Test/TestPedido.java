/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Test;

import Dao.PedidoDaoImpl;
import Interface.IPedido;
import java.util.ArrayList;
import java.util.List;
import model.Carrito;
import model.EstadoPedido;
import model.Pedidos;
import model.Persona;

/**
 *
 * @author JHOSMER
 */
public class TestPedido {

    IPedido dao = new PedidoDaoImpl();

    public static void main(String[] args) {
        TestPedido t = new TestPedido();
        t.Testpedido();
    }

    public void Testpedido() {
        Persona p = new Persona();
        p.setId_persona(1);

        List<Carrito> listaCarrito = new ArrayList<>();

        Carrito item1 = new Carrito();
        item1.setIdProducto(1);
        item1.setPrecio(50);
        item1.setCantidad(2);
        item1.setSubTotal(100);
        listaCarrito.add(item1);

        Carrito item2 = new Carrito();
        item2.setIdProducto(3);
        item2.setPrecio(250);
        item2.setCantidad(2);
        item2.setSubTotal(500);
        listaCarrito.add(item2);

        double total = 100 + 500;

        Pedidos nuevoPedido = new Pedidos();
        nuevoPedido.setPersona(p);
        nuevoPedido.setTotal(total);
        nuevoPedido.setEstadopedido(EstadoPedido.ENVIADO);
        nuevoPedido.setDetallepedido(listaCarrito);

        System.out.println("Enviando....");
        int result = dao.generarPedido(nuevoPedido);
        if (result > 0) {
            System.out.println("Pedido Registrado");
            System.out.println("Total:" + total);
        } else {
            System.out.println("Error al generar el pedido");
        }
    }
}
