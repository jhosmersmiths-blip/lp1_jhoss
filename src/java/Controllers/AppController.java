/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Dao.PedidoDaoImpl;
import Dao.ProductoDaoImpl;
import Interface.IPedido;
import Interface.IProducto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Carrito;
import model.EstadoPedido;
import model.Pedidos;
import model.Productos;
import model.Usuario;

/**
 *
 * @author JHOSMER
 */
@WebServlet(name = "AppContoller", urlPatterns = {"/AppContoller"})
public class AppController extends HttpServlet {

    private IProducto pDao = new ProductoDaoImpl();
    private IPedido IDao = new PedidoDaoImpl();
    private Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        JsonObject jsonResponse = new JsonObject();

        HttpSession session = request.getSession();

        List<Carrito> listCarrito = (List<Carrito>) session.getAttribute("carrito");

        if (listCarrito == null) {
            listCarrito = new ArrayList<>();
            session.setAttribute("carrito", listCarrito);
        }
        try (PrintWriter out = response.getWriter()) {
            switch (action) {
                case "listarProductos":
                    List<Productos> productos = pDao.lista();
                    out.print(gson.toJson(productos));
                    break;
                case "AddCarrito":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Productos p = pDao.SearchById(id);
                    if (p != null) {
                        int pos = -1;
                        for (int i = 0; i < listCarrito.size(); i++) {
                            if (listCarrito.get(i).getIdProducto() == id) {
                                pos = i;
                            }
                        }
                        if (pos != -1) {
                            int nuevaCant = listCarrito.get(pos).getCantidad() + 1;
                            listCarrito.get(pos).setCantidad(nuevaCant);
                            listCarrito.get(pos).setSubTotal(nuevaCant * p.getPrecio());
                        } else {
                            Carrito car = new Carrito();
                            car.setIdProducto(p.getId_producto());
                            car.setNombre(p.getNombre());
                            car.setPrecio(p.getPrecio());
                            car.setCantidad(1);
                            car.setSubTotal(p.getPrecio());
                            listCarrito.add(car);

                        }
                        jsonResponse.addProperty("success", true);
                        jsonResponse.addProperty("cartCout", listCarrito.size());

                    }
                    out.print(jsonResponse.toString());

                    break;

                case "listarcarrito":
                    double total = listCarrito.stream().mapToDouble(Carrito::getSubTotal).sum();
                    session.setAttribute("total", total);
                    JsonObject carData = new JsonObject();
                    carData.add("items", gson.toJsonTree(listCarrito));
                    carData.addProperty("total", total);
                    out.print(carData.toString());

                    break;

                case "delete":
                    try {
                        int idproducto = Integer.parseInt(request.getParameter("id"));
                        boolean eliminado = listCarrito.removeIf(c -> c.getIdProducto() == idproducto);
                        session.setAttribute("carrito", listCarrito);
                        jsonResponse.addProperty("success", eliminado);
                        jsonResponse.addProperty("message", eliminado ? "producto eliminado"
                                : "no se encontro el producto");

                    } catch (Exception e) {
                        jsonResponse.addProperty("sucess", false);
                        jsonResponse.addProperty("message", "error" + e.getMessage());
                    }
                    out.print(jsonResponse.toString());

                    break;
                case "GenerarCompra":

                    Usuario user = (Usuario) session.getAttribute("usuario");
                    if (user == null) {
                        jsonResponse.addProperty("success", false);
                        jsonResponse.addProperty("message", "Inicie sesion");
                        out.print(jsonResponse.toString());
                    }
                    if (listCarrito == null || listCarrito.isEmpty()) {
                         jsonResponse.addProperty("success", false);
                        jsonResponse.addProperty("message", " El carrito esta vacio");
                        out.print(jsonResponse.toString());
                        
                    }
                    
                    boolean stockDisponible = true;
                    String productoSinStock ="";
                    
                    for (Carrito c : listCarrito) {
                        Productos prodBD = pDao.SearchById(c.getIdProducto());
                        if (prodBD.getStock() < c.getCantidad()) {
                            stockDisponible = false;
                            
                            productoSinStock = prodBD.getNombre();
                            break;
                        }
                    }
                    if (!stockDisponible) {
                        jsonResponse.addProperty("success", false);
                        jsonResponse.addProperty("message", " Stock insuficiente"+productoSinStock);
                        out.print(jsonResponse.toString());
                        return;
                    }
                    
                    double totalPagar = listCarrito.stream().mapToDouble(Carrito::getSubTotal).sum();
                    
                    Pedidos pedido = new Pedidos();
                    pedido.setPersona(user.getPersona());
                    pedido.setTotal(totalPagar);
                    pedido.setEstadopedido(EstadoPedido.PROCESADO);
                    pedido.setDetallepedido(listCarrito);
                    
                    int idGenerado = IDao.generarPedido(pedido);
                    if (idGenerado >0) {
                        for (Carrito c : listCarrito) {
                            Productos prodBD = pDao.SearchById(c.getIdProducto());
                            int nuevoStock = prodBD.getStock() - c.getCantidad();
                            pDao.updateStock(c.getIdProducto(), nuevoStock);
                            
                        }
                        listCarrito.clear();
                        session.setAttribute("carrito", listCarrito);
                        session.setAttribute("total", 0.0);
                        jsonResponse.addProperty("success", true);
                        jsonResponse.addProperty("message", "compra exitosa !!!");
                    
                    }else{
                        jsonResponse.addProperty("success", false);
                        jsonResponse.addProperty("message", "compra no procesada !!!");
                    }
                    out.print(jsonResponse.toString());
                    break;

                default:
                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("success", "action no encontrada");
                    out.print(jsonResponse.toString());
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
