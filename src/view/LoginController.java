package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
            System.out.println("Enter");
        });

        btClose.setOnMouseClicked((MouseEvent e) ->{
            System.out.println("Closed");
        });
    }
}
