package controllers;

import application.RegisterPerson;
import dao.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdbc.DbException;
import model.Person;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPersonController implements Initializable {

    @FXML
    private Button btRegister;

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

    public static String pictureUrl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btCancel.setOnMouseClicked((MouseEvent e) -> {
            closed();
        });

        btCancel.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){
                closed();
            }
        });

        btRegister.setOnMouseClicked((MouseEvent e) -> {
            registerPerson();
        });

        btRegister.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER){
                registerPerson();
            }
        });

        imgPhoto.setOnMouseClicked((MouseEvent e) -> {
            selectedPhoto();
        });
    }

    public void closed(){
        RegisterPerson.getStage().close();
    }

    private void registerPerson(){
        String nome = txName.getText();
        String email = txEmail.getText();
        String password = psPassword.getText();
        String confPs = psPasswordConfirm.getText();
        if(validateInfos(nome, email, password, confPs)){
            if(password.equals(confPs)) {
                try {
                    Person person = new Person(nome, email, password, pictureUrl);
                    PersonDao dao = new PersonDao();
                    dao.insert(person);
                    Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                    al.setHeaderText("Usuário cadastrado.");
                    closed();
                    al.showAndWait();
                } catch (IOException | DbException e) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setHeaderText("Usuário não cadastrado.");
                    al.showAndWait();
                    e.printStackTrace();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Different passwords.");
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Blank fields");
            al.showAndWait();
        }
    }


    private boolean validateInfos(String name, String email, String password, String photoUrl){
        return name.length() > 5 && email.length() > 5 && password.length() > 8 && photoUrl.length() > 2;
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
