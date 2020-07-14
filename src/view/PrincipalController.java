package view;

import application.Principal;
import application.RegisterCompany;
import application.RegisterPerson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.PrincipalCloseOpen;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    @FXML
    private Button btListPerson;

    @FXML
    private Button btCadrasctPerson;

    @FXML
    private Button btListCompany;

    @FXML
    private Button btRegisterCompany;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btRegisterCompany.setOnMouseClicked((MouseEvent e) -> {
           openRegisterCompany();
        });

        btRegisterCompany.setOnKeyPressed((KeyEvent e) ->{
            if(e.getCode() == KeyCode.ENTER) {
                openRegisterCompany();
            }
        });

        btCadrasctPerson.setOnMouseClicked((MouseEvent e) -> {
           openRegisterPerson();
        });

        btCadrasctPerson.setOnKeyPressed((KeyEvent e) ->{
            if(e.getCode() == KeyCode.ENTER) {
                openRegisterPerson();
            }
        });
    }

    public void openRegisterPerson(){
        RegisterPerson rp = new RegisterPerson();
        try {
            Principal.getStage().close();
            rp.start(new Stage());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void openRegisterCompany(){
        RegisterCompany rc = new RegisterCompany();
        try {
            PrincipalCloseOpen.closed();
            Principal.getStage().close();
            rc.start(new Stage());
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
