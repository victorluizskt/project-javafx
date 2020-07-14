package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage; // Carrega a janela1;

    @Override
    public void start(Stage stage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml")); // Carrega o FXML
            Scene scene = new Scene(root); // Coloca o FXML em uma cena
            stage.setTitle("Login");
            stage.setScene(scene); // Coloca a cena em uma janela
            stage.show();  // Carrega a janela2
            setStage(stage);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage(){
        return stage;
    }

    public static void setStage(Stage stage){
        Main.stage = stage;
    }
}
