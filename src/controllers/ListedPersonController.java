package controllers;

import application.ListPerson;
import application.UpdatePerson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import dao.CompanyDao;
import dao.PersonDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Company;
import model.Person;
import util.PrincipalCloseOpen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListedPersonController implements Initializable {

    @FXML
    private TableView<Person> table;

    @FXML
    private TableColumn<Person, Long> clmId;

    @FXML
    private TableColumn<Person, String> clmName;

    @FXML
    private TableColumn<Person, String> clmEmail;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnSair;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbId;

    @FXML
    private Label lbNome;

    @FXML
    private ImageView imgView;

    @FXML
    private Button btUpdate;

    @FXML
    private TextField textSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btcGeradorPdf;

    private ObservableList<Person> personObservableList = FXCollections.observableArrayList();

    private Person selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initTable();
            btnSair.setOnMouseClicked((MouseEvent e) -> {
                closed();
                PrincipalCloseOpen.openMain();
            });

            btnDel.setOnMouseClicked((MouseEvent e) -> {
                removePerson();
            });

            textSearch.setOnKeyReleased((KeyEvent e) -> {
                table.setItems(search());
            });

            btnSearch.setOnMouseClicked((MouseEvent e) -> {
                table.setItems(search());
            });

            btnUpdate.setOnMouseClicked((MouseEvent e) -> {
                try {
                    table.setItems(updateTable());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            btcGeradorPdf.setOnMouseClicked((MouseEvent e) -> {
                generatePdf();
            });

            btUpdate.setOnMouseClicked((MouseEvent e) -> {
                if(selected != null) {
                    UpdatePerson updatePerson = new UpdatePerson(selected);
                    try {
                        updatePerson.start(new Stage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Error");
                    alert.setTitle("Selected person");
                    alert.setContentText("None person selected.");
                    alert.show();
                }
            });

            table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
                @Override
                public void changed(ObservableValue<? extends Person> observableValue, Person person, Person t1) {
                    selected = t1;
                    viewDetails();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initTable() throws IOException { // Iniciando os valores da tabela;
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        table.setItems(updateTable());
    }

    public ObservableList<Person> updateTable() throws IOException { // Valores do banco de dados para a initTable
        PersonDao dao = new PersonDao();
        personObservableList = FXCollections.observableArrayList(dao.findAll());
        return personObservableList;
    }

    public void closed(){
        ListPerson.getStage().close();
    }

    public void removePerson() {
        try {
            if(selected != null) {
                PersonDao dao = new PersonDao();
                dao.deleteById(selected);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Clean");
                alert.setTitle("User deleted success");
                alert.setContentText("Successfully deleted person.");
                alert.show();
                table.setItems(updateTable());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error");
                alert.setTitle("Selected person");
                alert.setContentText("None person selected.");
                alert.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void viewDetails(){
        if(selected != null){
            imgView.setImage(new Image("file:///" + selected.getPhoto()));
            lbId.setText(selected.getId().toString());
            lbNome.setText(selected.getName());
            lbEmail.setText(selected.getEmail());
        } else {
            imgView.setImage(new Image("/resources/image/boneco.png"));
            lbId.setText("");
            lbNome.setText("");
            lbEmail.setText("");
        }
    }

    public void generatePdf(){
        Document document = new Document();
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            File file = fileChooser.showOpenDialog(new Stage());
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
            document.open();
            List<Person> personList = new PersonDao().findAll();
            for (Person person : personList) {
                document.add(new Paragraph("Id: " + person.getId()));
                document.add(new Paragraph("Nome: " + person.getName()));
                document.add(new Paragraph("Email: " + person.getEmail()));
                document.add(new Paragraph("Caminho da foto: " + person.getPhoto()));
                document.add(new Paragraph("                                                 "));
            }
            document.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("PDF Gerado com sucesso.");
            alert.showAndWait();
        } catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }

    private ObservableList<Person> search(){
        ObservableList<Person> persons = FXCollections.observableArrayList();
        for (Person value : personObservableList) {
            if (value.getName().toLowerCase().contains(textSearch.getText().toLowerCase())) {
                persons.add(value);
            }
        }
        return persons;
    }
}
