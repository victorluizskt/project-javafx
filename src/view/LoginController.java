package view;

import application.Main;
import application.Principal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnLogin.setOnMouseClicked((MouseEvent e) ->{
           logIn();
        });

        btnLogin.setOnKeyPressed((KeyEvent e) ->{
            if(e.getCode() == KeyCode.ENTER){
                logIn();
            }
        });

        btnExit.setOnMouseClicked((MouseEvent e) ->{
            closeBtAction();
        });

        btnExit.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){
                closeBtAction();
            }
        });

        txtPassword.setOnKeyPressed((KeyEvent e) ->{
            if(e.getCode() == KeyCode.ENTER){
                logIn();
            }
        });
    }

    public void closeBtAction(){
        Main.getStage().close();
    }

    public void logIn(){
        if(txtUser.getText().equals("root") && txtPassword.getText().equals("1234")){
            Principal p = new Principal();
            closeBtAction();
            try {
                p.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.setContentText("Email or password invalid.");
            alert.show();
        }
    }
}
