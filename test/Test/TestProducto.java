/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Test;

import Dao.ProductoDaoImpl;
import Interface.IProducto;
import java.util.List;
import model.Productos;

/**
 *
 * @author JHOSMER
 */
public class TestProducto {

    public static IProducto dao = new ProductoDaoImpl();

    public static void main(String[] args) {
        TestProducto t = new TestProducto();
        //t.listar();
        //t.agregar();
        //t.actualizar();
        //t.buscarPorId();
        //t.elimnar();
        t.actualizarStock();
    }

    public static void listar() {

        List<Productos> lista = dao.lista();

        if (lista != null && !lista.isEmpty()) {
            System.out.println("ID\tNombre\t\tPrecio\tStock");
            for (Productos p : lista) {
                System.out.println(p.getId_producto()
                        + "\t" + p.getNombre() + "\t$"
                        + p.getPrecio() + "\t" + p.getStock());
            }
        } else {
            System.out.println("No hay Productos");
        }
    }

    public static void agregar() {
        Productos p = new Productos();
        p.setNombre("gaseosa");
        p.setDescripcion("bebida refrescante");
        p.setPrecio(2.00);
        p.setStock(50);

        boolean result = dao.insert(p);
        if (result) {
            System.out.println("Registro Sucess");
        } else {
            System.out.println("Error de registro");

        }
    }

    public static void actualizar() {
        Productos p = new Productos();
        p.setId_producto(1);
        p.setNombre("Arroz");
        p.setDescripcion("granos");
        p.setPrecio(4);
        p.setStock(30);
        boolean result = dao.update(p);
        if (result) {
            System.out.println("Registro actualizado");
        } else {
            System.out.println("Error de actualizacion");

        }
    }

    public void buscarPorId() {
        Productos prod = dao.SerachById(2);
        if (prod != null) {
            System.out.println("ID:" + prod.getId_producto());
            System.out.println("Nombre:" + prod.getNombre());
            System.out.println("descripcion:" + prod.getDescripcion());
            System.out.println("precio:" + prod.getPrecio());
            System.out.println("stock:" + prod.getStock());
        } else {
            System.out.println("producto no encontrado");
        }
    }

    public void elimnar() {
        Productos pr = new Productos();
        pr.setId_producto(2);
        boolean result = dao.delete(2);
        if (result) {
            System.out.println("Registro eliminado");
        } else {
            System.out.println("Error de eliminacion");

        }
    }

    public static void actualizarStock() {
        boolean result = dao.updateStock(1, 100);
        if (result) {
            System.out.println("Stock actualizado correctamente");
        } else {
            System.out.println("Error al actualizar el stock");
        }
    }
}
