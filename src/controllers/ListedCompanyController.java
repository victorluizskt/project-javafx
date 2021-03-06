package controllers;

import application.ListCompany;
import application.UpdateCompany;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import dao.CompanyDao;
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
import util.refator.Navigator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListedCompanyController implements Initializable{

    @FXML
    private TableView<Company> table;

    @FXML
    private TableColumn<Company, Long> clmId;

    @FXML
    private TableColumn<Company, String> clmName;

    @FXML
    private TableColumn<Company, String> clmCNPJ;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnSair;

    @FXML
    private Label lbId;

    @FXML
    private Label lbNome;

    @FXML
    private Label lbCNPJ;

    @FXML
    private ImageView imgView;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnGeradorCpf;

    @FXML
    private TextField textSearch;

    @FXML
    private Button btnSearch;

    private ObservableList<Company> companyObservableList = FXCollections.observableArrayList();

    private Company company;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initTable();
            btnSair.setOnMouseClicked((MouseEvent e) -> {
                closed();
                Navigator.openMain();
            });

            btnDel.setOnMouseClicked((MouseEvent e) -> {
                delCompany();
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
            textSearch.setOnKeyReleased((KeyEvent e) -> {
                table.setItems(search());
            });

            btnAlterar.setOnMouseClicked((MouseEvent e) -> {
                if(company != null) {
                    UpdateCompany updateCompany = new UpdateCompany(company);
                    try {
                        updateCompany.start(new Stage());
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error");
                    alert.setTitle("Error");
                    alert.setContentText("Select company.");
                    alert.show();
                }
                    });
            btnGeradorCpf.setOnMouseClicked((MouseEvent e) -> {
                try {
                    generateCpf();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends Company> observableValue, Company t1, Company t2) {
                    company = t2;
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
        clmCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        table.setItems(updateTable());
    }

    public ObservableList<Company> updateTable() throws IOException { // Valores do banco de dados para a initTable
        CompanyDao dao = new CompanyDao();
        companyObservableList = FXCollections.observableArrayList(dao.findAll());
        return companyObservableList;
    }

    public void closed() {
        ListCompany.getStage().close();
    }

    public void delCompany() {
        try {
            if (company != null) {
                CompanyDao companyDao = new CompanyDao();
                companyDao.deleteById(company);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Clean");
                alert.setTitle("Company deleted success");
                alert.setContentText("Successfully deleted company.");
                alert.show();
                table.setItems(updateTable());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error");
                alert.setTitle("Selected person");
                alert.setContentText("None company selected.");
                alert.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewDetails(){
        if(company != null){
            imgView.setImage(new Image("file:///" + company.getPhoto()));
            lbId.setText(company.getId().toString());
            lbNome.setText(company.getName());
            lbCNPJ.setText(company.getCnpj());
        } else {
            imgView.setImage(new Image("/resources/image/company.png"));
            lbId.setText("");
            lbNome.setText("");
            lbCNPJ.setText("");
        }
    }

    public void generateCpf() throws IOException {
        Document document = new Document();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
                document.open();
                List<Company> companyList = new CompanyDao().findAll();
                for (int i = 0; i < companyList.size(); i++) {
                    document.add(new Paragraph("ID: " + companyList.get(i).getId()));
                    document.add(new Paragraph("Name: " + companyList.get(i).getName()));
                    document.add(new Paragraph("CNPJ: " + companyList.get(i).getCnpj()));
                    document.add(new Paragraph("URLPICTURE: " + companyList.get(i).getPhoto()));
                    document.add(new Paragraph("                                          "));
                }
                document.close();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("PDF generate");
                alert.showAndWait();
            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.showAndWait();
        }
    }

    private ObservableList<Company> search() {
        ObservableList<Company> companies = FXCollections.observableArrayList();
        for (Company value : companyObservableList) {
            if (value.getName().toLowerCase().contains(textSearch.getText().toLowerCase())) {
                companies.add(value);
            }
        }
        return companies;
    }

}
