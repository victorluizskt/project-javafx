package dao;

import jdbc.DB;
import jdbc.DbException;
import model.Person;

import java.io.IOException;
import java.sql.*;

public class PersonDao {
    private Connection con;

    public PersonDao() throws IOException {
        this.con = DB.getConnection();
    }

    public void insert(Person obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "INSERT INTO person "
                            + "(Nome, Email, Senha) "
                            + "VALUES "
                            + "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getPassword());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId((long) id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

}
