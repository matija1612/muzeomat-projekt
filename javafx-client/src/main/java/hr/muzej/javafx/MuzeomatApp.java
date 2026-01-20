package hr.muzej.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Properties;

public class MuzeomatApp extends Application {

    private static String backendUrl;

    @Override
    public void start(Stage stage) throws Exception {
        ucitajConfig();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/dobrodosli.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add(
                getClass().getResource("/css/stil.css").toExternalForm()
        );

        stage.setTitle("Muzeomat - Vodic");
        stage.setScene(scene);
        stage.show();
    }

    private void ucitajConfig() {
        Properties props = new Properties();
        try (InputStream in = getClass().getResourceAsStream("/application.properties")) {
            if (in != null) {
                props.load(in);
                backendUrl = props.getProperty("backend.url", "http://localhost:8080");
            } else {
                backendUrl = "http://localhost:8080";
            }
        } catch (Exception e) {
            System.err.println("Config greska: " + e.getMessage());
            backendUrl = "http://localhost:8080";
        }
        System.out.println("Backend: " + backendUrl);
    }

    public static String getBackendUrl() {
        return backendUrl;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
