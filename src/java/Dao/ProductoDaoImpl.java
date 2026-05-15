/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IProducto;
import Util.ConexionSingleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Productos;
import java.sql.*;

/**
 *
 * @author JHOSMER
 */
public class ProductoDaoImpl implements IProducto {

    private Connection cn;

    @Override
    public List<Productos> lista() {
        List<Productos> lista = null;
        Productos pr;
        PreparedStatement st;
        ResultSet rs;
        String query = null;
        try {
            query = " SELECT id_producto, nombre, descripcion,"
                    + " precio,stock FROM productos ";

            lista = new ArrayList<>();
            //if (cn == null || cn.isClosed()) {
            // System.out.println("La conexion es nula o esta cerrada");
            //}
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                pr = new Productos();
                pr.setId_producto(rs.getInt("id_producto"));
                pr.setNombre(rs.getString("nombre"));
                pr.setDescripcion(rs.getString("descripcion"));
                pr.setPrecio(rs.getDouble("precio"));
                pr.setStock(rs.getInt("stock"));
                lista.add(pr);
            }

        } catch (Exception e) {
            System.out.println("Error al listar:" + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
            }
            System.out.println("no se pudo listar el producto");
        } finally {
            if (cn != null) {
                try {

                } catch (Exception e) {
                }
            }
        }
        return lista;
    }

    @Override
    public boolean insert(Productos p) {
        boolean flag = false;
        PreparedStatement st;
        String query = null;

        try {
            query = "INSERT INTO productos(nombre,descripcion, precio, stock)"
                    + " VALUES(?,?,?,?)";
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setString(1, p.getNombre());
            st.setString(2, p.getDescripcion());
            st.setDouble(3, p.getPrecio());
            st.setInt(4, p.getStock());

            st.executeUpdate();
            flag = true;

        } catch (Exception e) {
            System.out.println("Error al agregar un producto");
            try {
                cn.rollback();
            } catch (Exception ex) {
            }
            flag = false;
            System.out.println("Error: no se pudo agregar al registro");
        } finally {
            if (cn != null) {
                try {

                } catch (Exception e) {
                    System.out.println("Error al cerrar la conexion");
                }
            }
        }
        return flag;
    }

    @Override
    public boolean update(Productos p) {
        boolean flag = false;
        PreparedStatement st;
        String query = null;
        try {

            query = "UPDATE productos SET nombre = ?, descripcion = ?, "
                    + "precio = ?, stock = ? "
                    + "WHERE id_producto = ?";

            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setString(1, p.getNombre());
            st.setString(2, p.getDescripcion());
            st.setDouble(3, p.getPrecio());
            st.setInt(4, p.getStock());
            st.setInt(5, p.getId_producto());

            st.executeUpdate();

            flag = true;

        } catch (Exception e) {

            System.out.println("Error de actualizacion: " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

            flag = false;

        } finally {

            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    System.out.println("Error al cerrar la conexion " + e.getMessage());
                }
            }
        }

        return flag;
    }

    @Override
    public Productos SerachById(int id) {
        Productos prod = null;
        PreparedStatement st;
        //declarar variable que va conectar el SQL de insercion
        ResultSet rs;
        String query = null;

        try {
            query = "SELECT * FROM productos WHERE id_producto =?";
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                prod = new Productos();
                prod.setId_producto(rs.getInt("id_producto"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setStock(rs.getInt("stock"));
            }
        } catch (Exception e) {
            System.out.println("error de busqueda" + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {

            }
            System.out.println("no se pudo buscar el por id");

        } finally {
            if (cn != null) {
                try {

                } catch (Exception e) {
                    System.out.println("error al cerrar la conexion" + e.getMessage());
                }
            }
        }
        return prod;
    }

    @Override
    public boolean delete(int id) {
        boolean flag = false;
        PreparedStatement st;

        String query = null;
        try {
            query = "DELETE FROM productos WHERE id_producto = ?";
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setInt(1, id);

            st.executeUpdate();
            flag = true;

        } catch (Exception e) {
            System.out.println("Error de inserccion" + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {

            }
            flag = false;
            System.out.println("Error , no se agrego el registro");
        } finally {
            if (cn != null) {
                try {

                } catch (Exception e) {
                    System.out.println("Error al cerrar la conexion" + e.getMessage());
                }
            }
        }
        return flag;
    }

    @Override
    public boolean updateStock(int id, int stock) {
        boolean flag = false;
        PreparedStatement st;
        String query = null;

        try {
            query = "UPDATE productos SET stock = ? WHERE id_producto = ?";
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setInt(1, stock);
            st.setInt(2, id);

            st.executeUpdate();
            flag = true;

        } catch (Exception e) {
            System.out.println("Error al actualizar stock: " + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
            }
            flag = false;
            System.out.println("Error: no se pudo actualizar el stock");
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    System.out.println("Error al cerrar la conexion: " + e.getMessage());
                }
            }
        }
        return flag;
    }
}
