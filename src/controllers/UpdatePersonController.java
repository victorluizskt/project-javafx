package controllers;

import application.UpdatePerson;
import dao.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdbc.DbException;
import model.Person;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePersonController implements Initializable {

    @FXML
    private Button btUpdate;

    @FXML
    private Button btCancel;

    @FXML
    private TextField txName;

    @FXML
    private PasswordField psPassword;

    @FXML
    private PasswordField psPasswordConfirm;

    @FXML
    private TextField txEmail;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private Label lbId;

    private static Person person;

    private String urlPicture;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btCancel.setOnMouseClicked((MouseEvent e) -> {
            UpdatePerson.getStage().close();
        });
        initPerson();
        btUpdate.setOnMouseClicked((MouseEvent e) ->{
            try {
                updatePerson();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        imgPhoto.setOnMouseClicked((MouseEvent e) -> {
            selectedPhoto();
        });
    }

    public void initPerson(){
        imgPhoto.setImage(new Image("file:///" + person.getPhoto()));
        lbId.setText(person.getId().toString());
        txName.setText(person.getName());
        txEmail.setText(person.getEmail());
        psPassword.setText(person.getPassword());
        psPasswordConfirm.setText(person.getPassword());
        urlPicture = person.getPhoto();
    }

    public static Person getPerson() {
        return person;
    }

    public static void setPerson(Person person) {
        UpdatePersonController.person = person;
    }

    public void updatePerson() throws IOException {
        Long id = Long.parseLong(lbId.getText());
        String name = txName.getText();
        String email = txEmail.getText();
        String password = psPassword.getText();
        String passwordConfirm  = psPasswordConfirm.getText();
        if(password.equals(passwordConfirm)) {
            PersonDao personDao = new PersonDao();
            Person personNew = new Person(id, name, email, password, urlPicture);
            try {
                personDao.update(personNew);
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setHeaderText("Usuário atualizado.");
                al.showAndWait();
                UpdatePerson.getStage().close();
            } catch(DbException e){
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Usuário não atualizado.");
                al.showAndWait();
                e.printStackTrace();
            }
        }
    }

    public void selectedPhoto(){  // Adicionar caminho para imagens e filtrar apenas a extensão. * significa qualquer arquivo.
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null) {
            imgPhoto.setImage(new Image("file:///" + file.getAbsolutePath())); // file:/// indica para o sistema que o arquivo vem de fora do sistema.
            urlPicture = file.getAbsolutePath();
        }
    }
}

