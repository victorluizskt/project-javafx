package view;

import application.Principal;
import application.RegisterCompany;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.PrincipalCloseOpen;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterCompanyController implements Initializable {

    @FXML
    private Button btRegister;

    @FXML
    private Button btCancel;

    @FXML
    private TextField txName;

    @FXML
    private TextField txEmail;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btCancel.setOnMouseClicked((MouseEvent e) -> {
            closed();
            PrincipalCloseOpen.openMain();
        });

        btCancel.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){
                closed();
                PrincipalCloseOpen.openMain();
            }
        });

        btRegister.setOnMouseClicked((MouseEvent e) -> {

        });

        btRegister.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){

            }
        });
    }

    public void closed(){
        RegisterCompany.getStage().close();
    }


}
