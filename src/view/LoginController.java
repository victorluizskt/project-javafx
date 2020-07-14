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
    private PasswordField txPassword;

    @FXML
    private Button btClose;

    @FXML
    private Button btEnter;

    @FXML
    private TextField txEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btEnter.setOnMouseClicked((MouseEvent e) ->{
           logIn();
        });

        btEnter.setOnKeyPressed((KeyEvent e) ->{
            if(e.getCode() == KeyCode.ENTER){
                logIn();
            }
        });

        btClose.setOnMouseClicked((MouseEvent e) ->{
            closeBtAction();
        });

        btClose.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){
                closeBtAction();
            }
        });

        txPassword.setOnKeyPressed((KeyEvent e) ->{
            if(e.getCode() == KeyCode.ENTER){
                logIn();
            }
        });
    }

    public void closeBtAction(){
        Main.getStage().close();
    }

    public void logIn(){
        if(txEmail.getText().equals("root") && txPassword.getText().equals("1234")){
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
            alert.setContentText("Email or Password invalid.");
            alert.show();
        }
    }
}
