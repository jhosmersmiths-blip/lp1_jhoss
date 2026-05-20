/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IPedido;
import Util.ConexionSingleton;
import model.Pedidos;
import java.sql.*;
import model.Carrito;

/**
 *
 * @author JHOSMER
 */
public class PedidoDaoImpl implements IPedido {

    private Connection cn;

    @Override
    public int generarPedido(Pedidos pedidos) {
        int id_pedido = 0;
        int r = 0;
        PreparedStatement st;
        String query = null;
        ResultSet rs;

        try {
            query = "INSERT INTO pedidos(id_persona, total, estado)"
                    + "VALUES(?,?,?)";
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, pedidos.getPersona().getId_persona());
            st.setDouble(2, pedidos.getTotal());
            st.setString(3, pedidos.getEstadopedido().name());
            r = st.executeUpdate();

            if (r != 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    id_pedido = rs.getInt(1);
                }
            }
            if (id_pedido > 0) {
                for (Carrito detalle : pedidos.getDetallepedido()) {
                    query = " INSERT INTO detalles_pedido(id_pedido,id_producto,cantidad,precio_unitario,subtotal)"
                            + " VALUES(?,?,?,?,?)";
                    st=cn.prepareStatement(query);
                    st.setInt(1, id_pedido);
                    st.setInt(2, detalle.getIdProducto());
                    st.setInt(3, detalle.getCantidad());
                    st.setDouble(4, detalle.getPrecio());
                    st.setDouble(5, detalle.getSubTotal());
                    r = st.executeUpdate();
                }
            }else{
                System.out.println("Error al agregar el detalle");
            }

        } catch (Exception e) {
            System.out.println("Error al agregar un pedido" + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
                System.out.println("Error del rollback" + e.getMessage());
            }
        } finally {
            if (cn != null) {
                try {
                } catch (Exception ex) {
                }
            }
        }
        return r;
    }

}
