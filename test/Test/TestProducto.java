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
        t.agregar();
        t.listar();
        t.buscarPorId();
        t.actualizar();
        t.actualizarStock();
        //t.elimnar();
        
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
        p.setNombre("mouse ");
        p.setDescripcion("retroiluminado ");
        p.setPrecio(20);
        p.setStock(50);
        p.setImagen("/resoouces/img/teclado.jpg");

        boolean result = dao.insert(p);
        if (result) {
            System.out.println("Producto Registrado ");
        } else {
            System.out.println("Error de registro");

        }
    }

    public static void actualizar() {
        Productos p = new Productos();
        p.setId_producto(5);
        p.setNombre("monitor");
        p.setDescripcion("retroiluminado");
        p.setPrecio(250);
        p.setStock(50);
        p.setImagen("/resouces/img/monitor.jpg");
        boolean result = dao.update(p);
        if (result) {
            System.out.println("Registro actualizado");
        } else {
            System.out.println("Error de actualizacion");

        }
    }

    public void buscarPorId() {
        Productos prod = dao.SearchById(3);
        if (prod != null) {
            System.out.println("ID:" + prod.getId_producto());
            System.out.println("Nombre:" + prod.getNombre());
            System.out.println("descripcion:" + prod.getDescripcion());
            System.out.println("precio:" + prod.getPrecio());
            System.out.println("stock:" + prod.getStock());
            System.out.println("Ruta img:" + prod.getImagen());
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
        boolean result = dao.updateStock(5, 100);
        if (result) {
            System.out.println("Stock actualizado correctamente");
        } else {
            System.out.println("Error al actualizar el stock");
        }
    }
}
