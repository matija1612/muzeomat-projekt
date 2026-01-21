package hr.muzej.javafx.controller;

import hr.muzej.javafx.MuzeomatApp;
import hr.muzej.javafx.model.DetaljiDTO;
import hr.muzej.javafx.model.OdgovorDTO;
import hr.muzej.javafx.model.ProstorijaDTO;
import hr.muzej.javafx.model.ZahtjevDTO;
import hr.muzej.javafx.service.ApiServis;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OdabirController {

    @FXML private Label statusLabel;
    @FXML private TableView<ProstorijaItem> tablica;
    @FXML private TableColumn<ProstorijaItem, Boolean> odabranaCol;
    @FXML private TableColumn<ProstorijaItem, String> nazivCol;
    @FXML private TableColumn<ProstorijaItem, Integer> katCol;
    @FXML private TableColumn<ProstorijaItem, String> opisCol;

    @FXML private Button natragBtn;
    @FXML private Button optimizirajBtn;

    @FXML private VBox rezultatBox;
    @FXML private Label rezultatLabel;
    @FXML private ListView<String> rutaList;

    private ApiServis api;
    private List<ProstorijaDTO> sve;

    @FXML
    public void initialize() {
        System.out.println("[UI] Odabir screen init");

        api = new ApiServis(MuzeomatApp.getBackendUrl());
        postaviTablicu();
        ucitajProstorije();
    }

    private void postaviTablicu() {
        odabranaCol.setCellValueFactory(new PropertyValueFactory<>("odabrana"));
        odabranaCol.setCellFactory(CheckBoxTableCell.forTableColumn(odabranaCol));
        odabranaCol.setEditable(true);

        nazivCol.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        katCol.setCellValueFactory(new PropertyValueFactory<>("kat"));
        opisCol.setCellValueFactory(new PropertyValueFactory<>("opis"));

        tablica.setEditable(true);
    }

    private void ucitajProstorije() {
        statusLabel.setText("Ucitavanje...");
        optimizirajBtn.setDisable(true);

        new Thread(() -> {
            try {
                sve = api.dohvatiProstorije();

                Platform.runLater(() -> {
                    prikaziProstorije();
                    statusLabel.setText("Odaberite prostorije");
                    optimizirajBtn.setDisable(false);
                });

            } catch (Exception e) {
                System.err.println("[UI] Error loading: " + e.getMessage());

                Platform.runLater(() -> {
                    statusLabel.setText("Greska pri ucitavanju");
                    prikaziGresku("Greska: " + e.getMessage());
                });
            }
        }).start();
    }

    private void prikaziProstorije() {
        ObservableList<ProstorijaItem> items = FXCollections.observableArrayList();
        if (sve != null) {
            for (ProstorijaDTO p : sve) items.add(new ProstorijaItem(p));
        }
        tablica.setItems(items);
        System.out.println("[UI] Loaded " + items.size() + " prostorija");
    }

    @FXML
    private void handleNatrag() {
        System.out.println("[UI] Nazad na dobrodosli");

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/dobrodosli.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) natragBtn.getScene().getWindow();
            Scene scene = new Scene(root, 1024, 768);
            scene.getStylesheets().add(
                    getClass().getResource("/css/stil.css").toExternalForm()
            );
            stage.setScene(scene);

        } catch (IOException e) {
            System.err.println("[UI] Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleOptimiziraj() {
        List<Long> ids = tablica.getItems().stream()
                .filter(ProstorijaItem::isOdabrana)
                .map(i -> i.getProstorija().getId())
                .collect(Collectors.toList());

        if (ids.size() < 2) {
            prikaziGresku("Odaberite barem 2 prostorije!");
            return;
        }

        System.out.println("[UI] Optimizacija za " + ids.size() + " prostorija");
        System.out.println("[UI] Saljem API request...");

        statusLabel.setText("Racunam rutu...");
        optimizirajBtn.setDisable(true);
        rezultatBox.setVisible(false);

        new Thread(() -> {
            try {
                ZahtjevDTO req = ZahtjevDTO.builder()
                        .idProstorija(ids)
                        .muzeomatId("KIOSK-" + UUID.randomUUID().toString().substring(0, 8))
                        .build();

                OdgovorDTO resp = api.izracunajRutu(req);

                Platform.runLater(() -> {
                    prikaziRezultat(resp);
                    statusLabel.setText("Ruta gotova!");
                    optimizirajBtn.setDisable(false);
                });

            } catch (Exception e) {
                System.err.println("[UI] Error: " + e.getMessage());

                Platform.runLater(() -> {
                    prikaziGresku("Greska: " + e.getMessage());
                    statusLabel.setText("Greska pri racunanju");
                    optimizirajBtn.setDisable(false);
                });
            }
        }).start();
    }

    private void prikaziRezultat(OdgovorDTO resp) {
        if (resp == null) return;

        rezultatLabel.setText(String.format(
                "Optimizirana ruta: %d prostorija\n" +
                        "Ukupna udaljenost: %.1f m\n" +
                        "Trajanje: %d min\n" +
                        "Vrijeme izracuna: %d ms",
                (resp.getPutanja() != null ? resp.getPutanja().size() - 1 : 0),
                (resp.getUkupnaUdaljenost() != null ? resp.getUkupnaUdaljenost() : 0.0),
                (resp.getUkupnoVrijeme() != null ? resp.getUkupnoVrijeme() : 0),
                (resp.getVrijemeIzracuna() != null ? resp.getVrijemeIzracuna() : 0L)
        ));

        ObservableList<String> items = FXCollections.observableArrayList();
        List<DetaljiDTO> det = resp.getDetalji();
        if (det != null) {
            for (int i = 0; i < det.size(); i++) {
                DetaljiDTO d = det.get(i);
                items.add(String.format(
                        "%d. %s (Kat %d) - %d min",
                        i + 1,
                        d.getNaziv(),
                        d.getBrojKata(),
                        d.getProsjecnoTrajanje()
                ));
            }
        }

        rutaList.setItems(items);
        rezultatBox.setVisible(true);

        System.out.println("[UI] Rezultat prikazan");
    }

    private void prikaziGresku(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozorenje");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Helper klasa za TableView
    public static class ProstorijaItem {
        private final ProstorijaDTO prostorija;
        private final SimpleBooleanProperty odabrana;

        public ProstorijaItem(ProstorijaDTO p) {
            this.prostorija = p;
            this.odabrana = new SimpleBooleanProperty(false);
        }

        public boolean isOdabrana() { return odabrana.get(); }
        public void setOdabrana(boolean v) { odabrana.set(v); }

        public SimpleBooleanProperty odabranaProperty() { return odabrana; }

        public String getNaziv() { return prostorija.getNaziv(); }
        public Integer getKat() { return prostorija.getBrojKata(); }
        public String getOpis() { return prostorija.getOpis(); }
        public ProstorijaDTO getProstorija() { return prostorija; }
    }
}
