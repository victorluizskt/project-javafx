/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author victor
 */
public class DB {
    private static Connection conn = null;

    public static Connection getConnection() throws IOException{
        if(conn == null){
            try {
            Properties props = loadProperties();
            String url = props.getProperty("dburl");
            conn = DriverManager.getConnection(url, props);
            }catch(SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    } // Abre a conexão

    public static void closeConnection(){
        if(conn != null){
            try{
            conn.close();
            } catch(SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }  // Fecha a conexão

    private static Properties loadProperties() throws FileNotFoundException, IOException{
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch(IOException e){
            throw new DbException(e.getMessage());
        }
    } // Abre arquivo do Banco de Dados com as informações
    
    public static void closeStatement(Statement st){
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
               throw new DbException(e.getMessage());
            }
        }
    }
    
    public static void closeResultSet(ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
               throw new DbException(e.getMessage());
            }
        }
    }
}
