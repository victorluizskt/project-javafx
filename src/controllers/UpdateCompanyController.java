package controllers;

import application.UpdateCompany;
import dao.CompanyDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Company;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCompanyController implements Initializable {

    private static Company company;

    @FXML
    private Button btUpdate;

    @FXML
    private Button btCancel;

    @FXML
    private TextField txName;

    @FXML
    private TextField txCNPJ;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private Label lbId;


    private String pictureUrl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewDetails();
        btCancel.setOnMouseClicked((MouseEvent e) -> {
            close();
        });

        imgPhoto.setOnMouseClicked((MouseEvent e) -> {
            selectedPhoto();
        });

        btUpdate.setOnMouseClicked((MouseEvent e) -> {
            updateCompany();
        });
    }

    public static Company getCompany() {
        return company;
    }

    public static void setCompany(Company company) {
        UpdateCompanyController.company = company;
    }

    public void close(){
        UpdateCompany.getStage().close();
    }

    public void updateCompany() {
        try {
            Long id = Long.parseLong(lbId.getText());
            String nome = txName.getText();
            String cnpj = txCNPJ.getText();
            CompanyDao companyDao = new CompanyDao();
            Company companyNew = new Company(id, nome, cnpj, pictureUrl);
            companyDao.update(companyNew);
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setHeaderText("Empresa atualizada.");
            al.showAndWait();
            close();
        } catch(IOException ex){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Empresa não atualizada.");
            al.showAndWait();
            ex.printStackTrace();
        }

    }

    public void selectedPhoto(){  // Adicionar caminho para imagens e filtrar apenas a extensão. * significa qualquer arquivo.
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null) {
            imgPhoto.setImage(new Image("file:///" + file.getAbsolutePath())); // file:/// indica para o sistema que o arquivo vem de fora do sistema.
            pictureUrl = file.getAbsolutePath();
        }
    }

    public void viewDetails(){
        imgPhoto.setImage(new Image("file:///" + company.getPhoto()));
        txName.setText(company.getName());
        txCNPJ.setText(company.getCnpj());
        lbId.setText(company.getId().toString());
        pictureUrl = company.getPhoto();
    }
}
