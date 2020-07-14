package application;

import dao.PersonDao;
import model.Person;

import java.io.IOException;
import java.sql.SQLException;

public class Teste {
    public static void main(String[] args) throws SQLException, IOException {
        Person person = new Person("Victor Luiz", "victor@gmail.com", "87451336");
        PersonDao dao = new PersonDao();
        dao.insert(person);
    }
}
