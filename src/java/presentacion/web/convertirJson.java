/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.web;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Alfredo V
 */
public class convertirJson {
    private Gson convertir = new Gson();
    private String respuestaJson = "";
    private ResultSetMetaData rsMetadata = null;
    
    public String convertirAJson(String nombreTabla)
    {
        int cantCampos =0;
        ConexionFB fbc = new ConexionFB();
        fbc.conectarse();
        ResultSet resultado = fbc.selectRS("SELECT * FROM "+ nombreTabla);              
        
        //se crea array de listado q luego sera paquete json
        ArrayList listado = new ArrayList();
        try{
            rsMetadata = resultado.getMetaData();
            cantCampos = rsMetadata.getColumnCount();
            // aca se hace un while para recorrer todos los registros de la tabla
            while (resultado.next()) {

                TreeMap<String, String> tabla = new TreeMap();
                for (int i = 1; i <= cantCampos; i++) {
                    // aca recorremos cada columna y vamos completando el arbol para el json
                    tabla.put(rsMetadata.getColumnName(i), resultado.getString(i));
                    //res+= " \n ";
                    }
                listado.add(tabla);
                //respuestaJson = respuestaJson + convertir.toJson(tabla);
            }
            respuestaJson = convertir.toJson(listado);
            //esto es solo x el titulo y la forma en arbol. sino se manda directamente
            resultado.close();
            //5.cerrar coneccion
            
        }
        catch (Exception e) {
            //salida.print("\n error al sacar el resultado de la query para json \n");
            e.printStackTrace();
            respuestaJson = "error al hacer el json";
        }
        //fbc.desconectar();
        fbc.desconectarSolo();
        return respuestaJson;
        
        
    }
    public String convertirDeJson(String texto,String tabla)
    {   
        String q = "";
        ConexionFB fbc = new ConexionFB();
        fbc.conectarse();
        
        try {
            if (tabla.equals("USUARIOS")) {
                Usuario usuarioParametro = convertir.fromJson(texto, Usuario.class);
                usuarioParametro.validar();
                // aca hay que obtener los datos del objeto usuario
                q = "INSERT INTO USUARIOS (NOMBRE, APELLIDO, EMAIL, USUARIO, CLAVE, ACTIVO) VALUES ("+
                        "'"+usuarioParametro.getNombre()+"', "+
                        "'"+usuarioParametro.getApellido()+"', "+
                        "'"+usuarioParametro.getEmail()+"', "+
                        "'"+usuarioParametro.getUsuario()+"', "+
                        "'"+usuarioParametro.getClave()+"', "+
                        usuarioParametro.getActivo()+
                        " )";
                System.out.println(q);
                fbc.insert(q);  
            }
            
            respuestaJson = convertir.toJson("OK");

        }
        catch (ClassNotFoundException ex) {
            //out.println("Verificar: " + ex.getMessage());
        }
        catch (SQLException ex) {
            //out.println("Verificar:" + ex.getMessage());
        }
        catch (Exception ex) {
            //out.println("Verificar:" + ex.getMessage());
        }
        return respuestaJson;
        }
        
    }
