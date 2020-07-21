package controllers;

import application.*;
import dao.CompanyDao;
import dao.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnOmbudsman;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnHelp.setOnMouseClicked((MouseEvent e) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Help");
            alert.setTitle("Help");
            alert.setContentText("For any kind of help, \n" +
                                 "enter the ombudsman for \n" +
                                 "more details or contact \n" +
                                 "us by email or whatsapp.");
            alert.show();
        });

        btnOmbudsman.setOnMouseClicked((MouseEvent e) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Information");
            alert.setTitle("Ombudsman");
            alert.setContentText("Within 24 hours we will contact \n" +
                                 "you via email or call, just wait \n" +
                                 "for support to contact you.");
            alert.show();
        });

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

        btListPerson.setOnMouseClicked((MouseEvent e ) -> {
            findAllPerson();
        });

        btListPerson.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){
                findAllPerson();
            }
        });

        btListCompany.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){
                findAllCompany();
            }
        });

        btListCompany.setOnMouseClicked((MouseEvent e ) -> {
            findAllCompany();
        });
    }

    public void openRegisterPerson(){
        RegisterPerson rp = new RegisterPerson();
        try {
            rp.start(new Stage());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void openRegisterCompany(){
        RegisterCompany rc = new RegisterCompany();
        try {
            rc.start(new Stage());
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void findAllPerson(){
        ListPerson p = new ListPerson();
       try{
           PrincipalCloseOpen.closed();
           p.start(new Stage());
       } catch (Exception e){
           e.printStackTrace();
       }
    }

    public void findAllCompany(){
        ListCompany p = new ListCompany();
        try{
            PrincipalCloseOpen.closed();
            p.start(new Stage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
