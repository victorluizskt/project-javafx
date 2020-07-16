package view;

import application.RegisterCompany;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import util.validatcnpj.validatcnpj;
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
    private TextField txCNPJ;


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
            cnpjValidate();
        });

        btRegister.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){
               cnpjValidate();
            }
        });



    }

    public void closed(){
        RegisterCompany.getStage().close();
    }

    public void cnpjValidate(){
        if(!validatcnpj.isCNPJ(txCNPJ.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.setContentText("CNPJ invalid.");
            alert.show();
        }
    }

}
