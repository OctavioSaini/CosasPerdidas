/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.web;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Alfredo V
 */
@WebServlet(name = "ServletCP", urlPatterns = {"/ServletCP"})
public class ServletCP extends HttpServlet {
    Gson convertir = new Gson();
    String respuestaJson = "";

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //processRequest(request, response);
        PrintWriter salida = response.getWriter();
        convertirJson js = new convertirJson();
        String resultado = js.convertirAJson("USUARIOS");
        salida.println("" + resultado);
        //salida.print(js.convertirAJson("USUARIOS"));
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String texto = request.getReader().readLine();
        convertirJson js = new convertirJson();
        respuestaJson = js.convertirDeJson(texto,"USUARIOS");
        out.println(respuestaJson);

    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
