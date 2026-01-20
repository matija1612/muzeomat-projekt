package hr.muzej.javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DobrodosliController {

    @FXML private Label naslovLabel;
    @FXML private Label vrijemeLabel;
    @FXML private Button zapocniBtn;

    @FXML
    public void initialize() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        vrijemeLabel.setText(now.format(fmt));

        System.out.println("[UI] Dobrodosli ekran loaded");
    }

    @FXML
    private void handleZapocni() {
        System.out.println("[UI] Korisnik zapocinje odabir");

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/odabir.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) zapocniBtn.getScene().getWindow();
            Scene scene = new Scene(root, 1024, 768);
            scene.getStylesheets().add(
                    getClass().getResource("/css/stil.css").toExternalForm()
            );
            stage.setScene(scene);

        } catch (IOException e) {
            System.err.println("[UI] Greska loading screen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
