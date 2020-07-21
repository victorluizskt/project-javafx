package dao;

import jdbc.DB;
import jdbc.DbException;
import model.Company;
import model.Person;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao {
    private Connection con;

    public CompanyDao() throws IOException {
        this.con = DB.getConnection();
    }

    public void insertCompany(Company obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "INSERT INTO company "
                            + "(nome, cnpj, photo) "
                            + "VALUES "
                            + "(?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getCnpj());
            st.setString(3, obj.getPhoto());
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

    public void update(Company obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "UPDATE company " + "SET nome = ?, cnpj = ?, photo = ? " +
                            "WHERE id = ?;"
            );
            st.setString(1, obj.getName());
            st.setString(2, obj.getCnpj());
            st.setString(3, obj.getPhoto());
            st.setLong(4, obj.getId());
            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    public void deleteById(Company obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "DELETE FROM company WHERE Id = ?");
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

    public List<Company> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(
                    "SELECT * FROM company");
            rs = st.executeQuery();

            List<Company> list = new ArrayList<>();

            while (rs.next()) {
                Company obj = new Company();
                obj.setId(rs.getLong("id"));
                obj.setName(rs.getString("nome"));
                obj.setCnpj(rs.getString("cnpj"));
                obj.setPhoto(rs.getString("photo"));
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
