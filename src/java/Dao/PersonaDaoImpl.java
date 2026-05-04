/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IPersona;
import java.util.List;
import model.Persona;
import model.Usuario;
import java.sql.*;
import Util.ConexionSingleton;
import model.Rol;
/**
 *
 * @author JHOSMER
 */
public class PersonaDaoImpl implements IPersona{

    private Connection cn;
    
    @Override
    public List<Persona> lista() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insert(Persona p, Usuario u) {
        PreparedStatement st;
        String query = null;
        ResultSet rs;
        int id_persona =0;
        int r =0;
        
        try {
            query = "INSERT INTO persona(nombre,email,direccion,telefono) VALUES(?, ?, ?, ?)";
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, p.getNombre());
            st.setString(2, p.getEmail());
            st.setString(3, p.getDireccion());
            st.setString(4, p.getTelefono());
            r=st.executeUpdate();
            
            
            if (r !=0) {
                rs= st.getGeneratedKeys();
                if (rs.next()) {
                    //linea que devuelve el id de la persona creada
                    id_persona = rs.getInt(1);
                    System.out.println("id_persona:" + id_persona);
                }
                if (id_persona >0) {
                    u.setRol(Rol.CLIENTE);
                    String  hashpassword = u.HasPassword(u.getPassword());
                    query = "INSERT INTO usuarios(usuario,password,rol,id_persona) VALUES(?, ?, ?, ?)";
                    st = cn.prepareStatement(query);
                    st.setString(1, p.getEmail());
                    st.setString(2, hashpassword);
                    st.setString(3, u.getRol().name());
                    st.setInt(4,id_persona);
                    r = st.executeUpdate();
                }else{
                    System.out.println("Error al agregar una persona");
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error al agregar" + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
                  System.out.println("Error del rollback" + e.getMessage());
            }
        } finally{
            if (cn != null) {
                try { 
                } catch (Exception ex) {
                }
            }
    }
        return r;
    }

    @Override
    public boolean update(Persona p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Persona SearchByid(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
