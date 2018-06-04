/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alfredo V
 */
public class ConexionFB {
    private Connection connection = null;
    private ResultSet resultSet = null;
    private ResultSetMetaData rsMetadata = null;
    private ResultSetMetaData rsMeta = null;
    private Statement statement = null;
    private String host = "jdbc:firebirdsql://localhost/";
    private String db= "‪C:/COSASPERDIDAS.FDB";
    //private String db= "C:/Users/Octavio/Desktop/aplicaciones y herramientas/ITFS16/programacionAplicada/PRUEBA.FDB";
    private String user = "sysdba";
    private String password = "masterkey";
    
    public boolean conectarse(){ //throws IOException, ClassNotFoundException{
        boolean res=false;
        //HttpServletResponse response = null;
        //1PrintWriter salida = response.getWriter();
        try{
             Class.forName("org.firebirdsql.jdbc.FBDriver");
             //Class.forName("org.firebirdsql.jdbc");
             //connection = DriverManager.getConnection(host + db,this.user, this.password);
             connection = DriverManager.getConnection("jdbc:firebirdsql://localhost/C:/COSASPERDIDAS.FDB",this.user, this.password);
             
             System.out.println("Conectado a la base de datos [ " + this.db + "]");
             //salida.print("Conectado a la base de datos [ " + this.db + "]");
             res=true;
          }catch(Exception e){
             //System.out.println(e);
             //salida.print(e);
             e.printStackTrace();
          }
        return res;
    }
    
    public String select(String fields, String table)
    {
       String q =" SELECT "+ fields +" FROM " + table + " ";
       //String res=fields+" "+" \n ";
       String res="";
       int cantCampos = contarCaracteres(fields, ',') + 1;
       if (cantCampos > 1) 
       {
        try {
          statement = connection.createStatement();
          resultSet = statement.executeQuery(q);
          while (resultSet.next())
          {
               for (int i = 1; i <= cantCampos; i++) {
                  res+=resultSet.getString(i)+ " | ";
                  //res+= " \n ";
               }
               res+= "\n";   
               //res+=resultSet.getString(1) + " | " + resultSet.getString(2) + " | " + resultSet.getString(3) + " \n ";
               //res+=resultSet.getString(fields);
          }
         }
         catch (SQLException ex) {
            System.out.println(ex);
         }
       }
       else
       {
        
        try {
         statement = connection.createStatement();
         resultSet = statement.executeQuery(q);
         rsMetadata = resultSet.getMetaData();
         cantCampos = rsMetadata.getColumnCount();   
         while (resultSet.next())
         {
              for (int i = 1; i <= cantCampos; i++) {
                 res+=resultSet.getString(i) + " | ";
                 //res+= "\n ";
              }
              res+= "\n";   
              //res+=resultSet.getString(1) + " | " + resultSet.getString(2) + " | " + resultSet.getString(3) + " \n ";
              //res+=resultSet.getString(fields);
         }
        }
        catch (SQLException ex) {
           System.out.println(ex);
        }  
       }
           

       return res;
    }

    
 // Metodo select con toda la query
    public String selectQuery (String q){
    String res="";
    int cantCampos =0; 
        try {
         statement = connection.createStatement();
         resultSet = statement.executeQuery(q);
         rsMetadata = resultSet.getMetaData();
         cantCampos = rsMetadata.getColumnCount();   
         while (resultSet.next())
         {
              for (int i = 1; i <= cantCampos; i++) {
                 res+=resultSet.getString(i) + " | ";
                 //res+= "\n ";
              }
              res+= "\n";   
              //res+=resultSet.getString(1) + " | " + resultSet.getString(2) + " | " + resultSet.getString(3) + " \n ";
              //res+=resultSet.getString(fields);
         }
        }
        catch (SQLException ex) {
           System.out.println(ex);
        }  
    
           

       return res;    
    }
 
 
  /* METODO PARA REALIZAR UNA CONSULTA A LA BASE DE DATOS
  * INPUT:
  La query entera a consultar
  * OUTPUT:
  Un resulset de la query consultada
  */
    
  public ResultSet selectRS(String q)
    {
        //String q =" SELECT "+ fields +" FROM " + table + " " + where + " ";
        try {
          statement = connection.createStatement();
          resultSet = statement.executeQuery(q);
            }
         catch (SQLException ex) {
            System.out.println(ex);
            }
        return resultSet;
    }  

    
 // Metodo para sacar la metadata de una tabla
    public ResultSetMetaData metaDataRS(String table)
    {
        
        String q =" SELECT * FROM " + table + " ";
         
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(q);
            rsMetadata = resultSet.getMetaData();
            System.out.println("la cantidad de columnas que tiene es: "+rsMetadata.getColumnCount());
            
        }
        catch (SQLException ex) {
           System.out.println(ex);
        }
        return rsMetadata;
    }
 //___________________________________________________________________________________ Soy una barra separadora ðŸ™‚
    
    
 //___________________________________________________________________________________ Soy una barra separadora ðŸ™‚
 // metodo para obtener todos los nombres de las columnas de una tabla en un string:
    public String nombreColumnas (String tabla){
        String nombreColumnas = "";
        try {
            ConexionFB fbc = new ConexionFB();
            fbc.conectarse();
            rsMeta= fbc.metaDataRS(tabla);
            // Nombre de las columnas como apareceran en la tabla
            int cantCol = rsMeta.getColumnCount();
            //String[] columnas;
            String[] columnas = new String[cantCol];
            
            for (int i = 2; i <= cantCol; i++) {
                 columnas[i-1]=(rsMeta.getColumnName(i));
                 if (i < cantCol){
                     nombreColumnas += rsMeta.getColumnName(i)+",";
                 }else{
                     nombreColumnas += rsMeta.getColumnName(i)+" \n";
                 }
              }
            fbc.desconectarSolo();
            return nombreColumnas;
        } catch (SQLException e) {
            System.out.println("Error de lectura de BD\n\n");
            e.printStackTrace();
            return nombreColumnas;
        }
    }
   
    public void desconectarSolo()
    {
        try {
            connection.close();
            System.out.println("Desconectado de la base de datos [ " + this.db + "]");
        }        
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }
 //___________________________________________________________________________________ Soy una barra separadora ðŸ™‚
    
    
    public void desconectar()
    {
        try {
            resultSet.close();
            statement.close();
            connection.close();
            System.out.println("Desconectado de la base de datos [ " + this.db + "]");
        }        
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public static int contarCaracteres(String cadena, char caracter){
        int posicion,contador =0;
        posicion = cadena.indexOf(caracter);
        while (posicion != -1){
            contador++;
            posicion = cadena.indexOf(caracter,posicion +1);
        }
        return contador;    
    }
    
    public boolean updateDelete(String q){
        boolean res=false;
        //se ejecuta la consulta
        try {
            PreparedStatement pstm = connection.prepareStatement(q);
            pstm.execute();
            pstm.close();
            res=true;
         }catch(Exception e){
            System.out.println(e);
        }
        return res;
    }
    
    /* METODO PARA INSERTAR UN REGISTRO EN LA BASE DE DATOS
  * INPUT:
    q: query para hacer el insert.
  * OUTPUT:
  * Boolean
 */
    public boolean insert(String q){
        boolean res=false;
        //Se arma la consulta
        //String q=" INSERT INTO " + table + " ( " + fields + " ) VALUES ( " + values + " ) ";
        //se ejecuta la consulta
        try {
            PreparedStatement pstm = connection.prepareStatement(q);
            pstm.execute();
            pstm.close();
            res=true;
         }catch(Exception e){
            System.out.println(e);
        }
        return res;
    }
    
    /* METODO PARA INSERTAR UN REGISTRO EN LA BASE DE DATOS
  * INPUT:
  table = Nombre de la tabla
  fields = String con los nombres de los campos donde insertar Ej.: campo1,campo2campo_n
  values = String con los datos de los campos a insertar Ej.: valor1, valor2, valor_n
  * OUTPUT:
  * Boolean
 */
    public boolean insert(String table, String fields, String values){
        boolean res=false;
        //Se arma la consulta
        String q=" INSERT INTO " + table + " ( " + fields + " ) VALUES ( " + values + " ) ";
        //se ejecuta la consulta
        try {
            PreparedStatement pstm = connection.prepareStatement(q);
            pstm.execute();
            pstm.close();
            res=true;
         }catch(Exception e){
            System.out.println(e);
        }
        return res;
    }
    
    
}
