package util.refator;

import application.Principal;
import application.RegisterCompany;
import javafx.stage.Stage;

public class Navigator {

    public static void closed(){
        Principal.getStage().close();
    }

    public static void openMain(){
        Principal principal = new Principal();
        try {
            principal.start(new Stage());
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
