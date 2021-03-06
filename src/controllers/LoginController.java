package controllers;

import application.Main;
import application.Principal;
import application.SendEmail;
import dao.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Person;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox chBoxRemember;

    @FXML
    private Button btnForgotEmail;

    private String user;

    private String password;

    private Path path = Paths.get("remember.txt");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if(!returnRead()){
                write();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnLogin.setOnMouseClicked((MouseEvent e) ->{
            try {
                if(chBoxRemember.isSelected()){
                    user = txtUser.getText();
                    password = txtPassword.getText();
                    read(user, password);
                } else {
                    user = null;
                    password= null;
                    read(null, null);
                }
                logIn();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        btnForgotEmail.setOnMouseClicked((MouseEvent e) -> {
            initializeSendEmail();
        });

        btnLogin.setOnKeyPressed((KeyEvent e) ->{
            if(e.getCode() == KeyCode.ENTER){
                try {
                    logIn();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });


        txtPassword.setOnKeyPressed((KeyEvent e) ->{
            if(e.getCode() == KeyCode.ENTER){
                try {
                    logIn();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public void closeBtAction(){
        Main.getStage().close();
    }

    public void logIn() throws IOException {
        PersonDao personDao = new PersonDao();
        List<Person> personList = personDao.findAll();
        if (txtUser.getText().equals("root") && txtPassword.getText().equals("1234")) {
            Principal p = new Principal();
            try {
                p.start(new Stage());
                closeBtAction();
            } catch(Exception e){
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < personList.size(); i++) {
                if (txtUser.getText().equals(personList.get(i).getEmail()) && txtPassword.getText().equals(personList.get(i).getPassword())) {
                    Principal p = new Principal();
                    i = personList.size();
                    closeBtAction();
                    try {
                        p.start(new Stage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (i == personList.size() - 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error");
                    alert.setTitle("Error");
                    alert.setContentText("Email or password invalid.");
                    alert.show();
                }
            }

        }
    }

    public void read(String user, String password) throws IOException {
        if(user != null && password != null) {
            String vetUse = (user + " " + password);
            byte[] userBytes = vetUse.getBytes();
            Files.write(path, userBytes);
        } else {
            String vetUse = " ";
            byte[] userBytes = vetUse.getBytes();
            Files.write(path, userBytes);
        }
    }

    public void write() throws IOException {
        byte[] users = Files.readAllBytes(path);
        String read = new String(users);
        String[] salve = read.split(" ");
        if(!returnRead()){
            txtUser.setText(salve[0]);
            txtPassword.setText(salve[1]);
        }
    }

    public boolean returnRead() throws IOException {
        byte[] users = Files.readAllBytes(path);
        String read = new String(users);
        return read.equals(" ");
    }

    public void initializeSendEmail(){
        SendEmail sendEmail = new SendEmail();
        try{
            sendEmail.start(new Stage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

