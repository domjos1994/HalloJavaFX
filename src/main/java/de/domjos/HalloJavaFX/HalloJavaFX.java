package de.domjos.HalloJavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class HalloJavaFX extends Application {
    private static ResourceBundle resourceBundle = null;

    static {
        System.setProperty("javafx.platform" , "Desktop");
        System.setProperty("http.agent", "Mozilla/5.0");
        System.setProperty("nashorn.args", "--no-deprecation-warning");
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = HalloJavaFX.class.getResource("/fxml/Main.fxml");

        if(url != null) {
            String stylesheet = Objects.requireNonNull(
                    HalloJavaFX.class.getResource("/styles/style.css")
            ).toExternalForm();

            InputStream inputStream =
                    HalloJavaFX.class.getResourceAsStream("/images/icon.png");

            ResourceBundle language = HalloJavaFX.getLanguage();
            Parent root = FXMLLoader.load(url, language);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(stylesheet);
            stage.setScene(scene);

            if(inputStream != null) {
                stage.getIcons().add(new Image(inputStream));
                inputStream.close();
            }

            stage.setTitle(language.getString("application.title"));
            stage.show();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static ResourceBundle getLanguage() {
        if(HalloJavaFX.resourceBundle == null) {
            ClassLoader classLoader = new URLClassLoader(new URL[] {HalloJavaFX.class.getResource("/lang/")});
            HalloJavaFX.resourceBundle = ResourceBundle.getBundle("lang", Locale.getDefault(), classLoader);
        }
        return HalloJavaFX.resourceBundle;
    }
}
