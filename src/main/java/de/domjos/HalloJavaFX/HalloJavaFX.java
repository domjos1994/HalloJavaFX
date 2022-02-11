package de.domjos.HalloJavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class HalloJavaFX extends Application {

    static {
        System.setProperty("javafx.platform" , "Desktop");
        System.setProperty("http.agent", "Mozilla/5.0");
        System.setProperty("nashorn.args", "--no-deprecation-warning");
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = HalloJavaFX.class.getResource("/fxml/Main.fxml");

        if(url != null) {
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Hallo JavaFX");
            stage.show();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
