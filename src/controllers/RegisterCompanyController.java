package controllers;

import application.RegisterCompany;
import dao.CompanyDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdbc.DbException;
import model.Company;
import util.validatcnpj.validatcnpj;
import util.refator.Navigator;

import java.io.File;
import java.io.IOException;
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

    @FXML
    private ImageView imgPhoto;

    private String pictureUrl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btCancel.setOnMouseClicked((MouseEvent e) -> {
            closed();
        });

        btCancel.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){
                closed();
                Navigator.openMain();
            }
        });

        btRegister.setOnMouseClicked((MouseEvent e) -> {
            registerCompany();
        });

        btRegister.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){
               registerCompany();
            }
        });

        imgPhoto.setOnMouseClicked((MouseEvent e) -> {
            selectedPhoto();
        });
    }

    public void closed(){
        RegisterCompany.getStage().close();
    }

    public boolean cnpjValidate(String cnpj){
        if(!validatcnpj.isCNPJ(cnpj)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.setContentText("CNPJ invalid.");
            alert.show();
            return false;
        } else {
            return true;
        }
    }

    public void registerCompany(){
        String nome = txName.getText();
        String cnpj = txCNPJ.getText();
        if(checkInfos(nome, cnpj, pictureUrl)) {
            if (cnpjValidate(cnpj)) {
                try {
                    Company com = new Company(nome, cnpj, pictureUrl);
                    CompanyDao comDao = new CompanyDao();
                    comDao.insertCompany(com);
                    Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                    al.setHeaderText("Empresa cadastrada.");
                    closed();
                    al.showAndWait();
                } catch (IOException | DbException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Blank fields");
            al.showAndWait();
        }
    }

    private boolean checkInfos(String name, String cnpj, String photoUrl){
        return name.length() > 4 && cnpj.length() > 10 && photoUrl.length() > 2;
    }

    public void selectedPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null) {
            imgPhoto.setImage(new Image("file:///" + file.getAbsolutePath())); // file:/// indica para o sistema que o arquivo vem de fora do sistema.
            pictureUrl = file.getAbsolutePath();
        }
    }

}
