package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Classe responsável por fabricar as conexões entre Java e Banco de Dados(Abre a conexão)
public class ConnectionFactory {

    public Connection getConnection(){
        try {
            String user = "root";
            String password = "verteiros2";
            String dburl = "localhost:3306";
            String nameBanc = "revisaobd";
            return DriverManager.getConnection("jdbc:mysql://" + dburl + "/" + nameBanc,
                    user, password);
        } catch (SQLException e){
            System.out.println("SQL Connection error.");
            throw new RuntimeException(e);
        }
    }
}
