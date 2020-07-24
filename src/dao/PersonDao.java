package dao;

import jdbc.DB;
import jdbc.DbException;
import model.Person;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                            + "(nome, email, senha, photo) "
                            + "VALUES "
                            + "(?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getPassword());
            st.setString(4, obj.getPhoto());
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

    public void update(Person obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "UPDATE person " + "SET Nome = ?, Email = ?, Senha = ?, Photo = ? " +
                            "WHERE id = ?;"
            );
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getPassword());
            st.setString(4, obj.getPhoto());
            st.setLong(5, obj.getId());
            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    public void deleteById(Person obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "DELETE FROM person WHERE Id = ?");
            st.setLong(1, obj.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException("Impossible deleting");
        }
        finally {
            DB.closeStatement(st);
        }
    }

    public List<Person> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(
                    "SELECT * FROM person");
            rs = st.executeQuery();

            List<Person> list = new ArrayList<>();

            while (rs.next()) {
                Person obj = new Person();
                obj.setId(rs.getLong("Id"));
                obj.setName(rs.getString("Nome"));
                obj.setEmail(rs.getString("Email"));
                obj.setPassword(rs.getString("Senha"));
                obj.setPhoto(rs.getString("Photo"));
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}



