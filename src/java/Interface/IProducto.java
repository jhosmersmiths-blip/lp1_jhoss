/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.Productos;

/**
 *
 * @author JHOSMER
 */
public interface IProducto {
    public List<Productos> lista();
    public boolean insert (Productos p);
    public boolean update (Productos p);
    public Productos SerachById(int id);
    public boolean delete(int id);
    public boolean updateStock(int id, int stock);
}
