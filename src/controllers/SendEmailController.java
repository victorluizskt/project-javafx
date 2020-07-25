package controllers;

import application.SendEmail;
import application.UpdatePerson;
import dao.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Person;
import util.sendEmail.sendEmail;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SendEmailController implements Initializable {

    @FXML
    private Button btnSendEmail;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldEmailConfirm;

    @FXML
    private Button btnSetValue;

    @FXML
    private TextField txtNumber;

    @FXML
    private Button btnHelp;

    private Person person;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnHelp.setOnMouseClicked((MouseEvent e) ->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("To recover your password, fill in the email field, \n" +
                    "right after confirm in the email and click on send. \n" +
                    "When a number arrives by email, enter it in the field \n" +
                    "below and confirm, right after you will be redirected to \n" +
                    "the initial registration update screen.");
            alert.showAndWait();
        });

        btnSendEmail.setOnMouseClicked((MouseEvent e) -> {
            try {
                if(consultEmailBd()){
                    sendEmail();
                } else if(textFieldEmail.getText().equals("") || textFieldEmailConfirm.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Empty fields.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Email does not exist.");
                    alert.showAndWait();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        btnSetValue.setOnMouseClicked((MouseEvent e) -> {
            try {
                confirmValue();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public void sendEmail(){
        String email = textFieldEmail.getText();
        String confirmEmail = textFieldEmailConfirm.getText();
        if(email.equals(confirmEmail) && sendEmail.sendGmail(email)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Email successfully sent.");
            alert.show();
        } else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Emails different");
            alert.showAndWait();
        }
    }

    public void confirmValue() throws IOException {
        Alert alert;
        try {
            int value2 = Integer.parseInt(txtNumber.getText());
            if (value2 == sendEmail.getValue()) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Ok, confirmation");
                SendEmail.getStage().close();
                UpdatePerson updatePerson = new UpdatePerson(person);
                updatePerson.start(new Stage());
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Values different");
            }
            alert.show();
        } catch(NumberFormatException e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Only numbers");
            alert.show();
        }
    }

    public boolean consultEmailBd() throws IOException {
        List<Person> personList;

        personList = new PersonDao().findAll();
        for (Person value : personList) {
            if (value.getEmail().equals(textFieldEmail.getText())) {
                person = value;
                return true;
            }
        }
        return false;
    }

}
