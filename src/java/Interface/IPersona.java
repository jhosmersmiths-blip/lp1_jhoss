/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.Persona;
import model.Usuario;

/**
 *
 * @author JHOSMER
 */
public interface IPersona {
    public List<Persona> lista();
    public int insert(Persona p, Usuario u);
    public boolean update (Persona p);
    public Persona SearchByid (int id);
    public boolean delete (int id);
}
