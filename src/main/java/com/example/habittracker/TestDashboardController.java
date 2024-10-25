package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;

public class TestDashboardController {

    @FXML
    private Button newHabitButton;

    @FXML
    private VBox HabitPane; // Pane für die neuen Habits
    @FXML
    private VBox CirclePane; // Pane für die Kreise
    @FXML
    private ImageView imageView; // ImageView zum Anzeigen des hochgeladenen Bildes

    // Definieren des spezifischen Blautons
    private final Color circleColor = Color.web("#3A51CA");
    private final Color defaultColor = Color.WHITE;

    @FXML
    public void initialize() {
        // Event-Handler für den Button + new habit
        newHabitButton.setOnAction(event -> addNewHabit());

        // Initiale Einstellungen für die ImageView
        imageView.setPreserveRatio(true); // Seitenverhältnis beibehalten
        imageView.setFitWidth(300); // Setze die Breite (kann angepasst werden)
        imageView.setFitHeight(200); // Setze die Höhe (kann angepasst werden)
        imageView.setStyle("-fx-alignment: center;"); // Zentriert das Bild
    }

    // Methode zum Hochladen eines Bildes
    @FXML
    private void handleImageUpload(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        // Öffne den FileChooser und warte auf die Benutzeraktion
        File selectedFile = fileChooser.showOpenDialog(imageView.getScene().getWindow());
        if (selectedFile != null) {
            // Lade das Bild und zeige es im ImageView an
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);

            // Setze die ImageView auf die Größe des Bildes, um es zu zentrieren
            imageView.setFitWidth(HabitPane.getWidth()); // Füllt die gesamte Breite der Pane
            imageView.setFitHeight(HabitPane.getHeight()); // Füllt die gesamte Höhe der Pane
        } else {
            // Zeige eine Warnung an, wenn kein Bild ausgewählt wurde
            Alert alert = new Alert(Alert.AlertType.WARNING, "No image selected.");
            alert.setTitle("Warning");
            alert.showAndWait();
        }
    
    }

    // Methode zum Hochladen eines Bildes
    private void handleCircleClick(MouseEvent event) {
        Circle clickedCircle = (Circle) event.getSource();
        if (clickedCircle.getFill() == defaultColor) {
            clickedCircle.setFill(circleColor);
        } else {
            clickedCircle.setFill(defaultColor);
        }
    }

    // Methode, um einen neuen Habit zu erstellen
    private void addNewHabit() {
        // Erstelle einen TextInputDialog für die Eingabe des Habit-Namens
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add a new Habit");
        dialog.setHeaderText(null);
        dialog.setContentText("Name your habit:");

        // Zeige den Dialog und warte auf die Eingabe
        Optional<String> result = dialog.showAndWait();

        // Wenn der Benutzer eine Eingabe gemacht hat, füge den Habit ins Pane hinzu
        result.ifPresent(habitName -> {
            if (!habitName.trim().isEmpty()) {
                addHabitToPane(habitName); // Habit hinzufügen
            }
        });
    }

    // Methode zum Hinzufügen eines neuen Habits zum HabitPane
    private void addHabitToPane(String habitName) {
        // Erstelle eine VBox, um den Habit und den "Longest Streak"-Text zu enthalten
        VBox habitContainer = new VBox();
        habitContainer.setSpacing(5); // Setzt den Abstand zwischen den Texten

        // Erstelle den Textknoten für den Habit mit Schriftgröße 18
        Text habitText = new Text(habitName);
        habitText.setFont(new Font(18)); // Schriftgröße 18
        habitText.setFill(Color.WHITE); // Setze die Schriftfarbe auf Weiß

        // Erstelle den Text für "Longest Streak:" mit Schriftgröße 10
        Text longestStreakText = new Text("Longest Streak:");
        longestStreakText.setFont(new Font(10)); // Schriftgröße 10
        longestStreakText.setFill(Color.WHITE); // Setze die Schriftfarbe auf Weiß

        // Füge beide Texte zur VBox hinzu
        habitContainer.getChildren().addAll(habitText, longestStreakText);

        // Füge die Habit VBox zum HabitPane hinzu
        HabitPane.getChildren().add(habitContainer);

        // Erstelle eine neue HBox für die Kreise und den Zähler für diese Habit
        HBox circlesRow = new HBox();
        circlesRow.setSpacing(20); // Horizontaler Abstand zwischen den Kreisen

        // Zähler für den aktuellen Streak und die Anzahl ausgelassener Tage
        final int[] currentStreak = {0};  // Aktueller Streak
        boolean[] circleClicked = new boolean[7]; // Um den Status jedes Kreises zu speichern (ob geklickt oder nicht)

        // Textknoten für die Anzeige des Streaks mit neuer Farbe und fetter Schrift
        Text streakCountText = new Text("0");
        streakCountText.setFont(Font.font("System", FontWeight.BOLD, 14)); // Setze Schrift auf fett und Größe 14
        streakCountText.setFill(Color.web("#22369F")); // Setze die Schriftfarbe auf #22369F

        // "X" Button für das Löschen der Habit
        Button removeButton = new Button("X");
        removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #22369f; -fx-font-size: 10; -fx-padding: 0;"); // Kleinere Schriftgröße und keine Polsterung
        removeButton.setMinSize(20, 20); // Setze eine minimale Größe für den Button
        removeButton.setMaxSize(20, 20); // Setze eine maximale Größe für den Button

        // Event-Handler für den "X"-Button, um die Habit zu löschen
        removeButton.setOnAction(event -> {
            // Erstelle einen Bestätigungsdialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Do you really want to delete " + habitName + "?");

            // Füge die Schaltflächen hinzu
            alert.getButtonTypes().setAll(ButtonType.CANCEL, ButtonType.OK);

            // Zeige den Dialog und warte auf die Antwort
            Optional<ButtonType> result = alert.showAndWait();

            // Wenn der Benutzer auf "Delete habit" klickt, lösche das Habit
            if (result.isPresent() && result.get() == ButtonType.OK) {
                HabitPane.getChildren().remove(habitContainer);  // Entfernt die Habit
                CirclePane.getChildren().remove(circlesRow);     // Entfernt die zugehörige Streak-Anzeige
            }
        });

        for (int i = 0; i < 7; i++) {
            Circle circle = new Circle(10); // Erstelle einen Kreis mit Radius 10
            circle.setFill(defaultColor); // Standardfarbe (weiß)

            // Event-Handler für Klicks auf die Kreise
            int finalI = i; // Final-Index für die Lambda-Funktion
            circle.setOnMouseClicked(event -> {
                if (!circleClicked[finalI]) { // Kreis wird das erste Mal geklickt
                    circle.setFill(circleColor); // Farbe ändern bei Klick
                    circleClicked[finalI] = true; // Markiere als geklickt

                    // Überprüfe den Streak
                    calculateStreak(circleClicked, currentStreak);
                } else { // Kreis wird erneut geklickt (zurücksetzen)
                    circle.setFill(defaultColor); // Farbe zurücksetzen
                    circleClicked[finalI] = false; // Markiere als nicht geklickt

                    // Überprüfe den Streak erneut
                    calculateStreak(circleClicked, currentStreak);
                }

                // Aktualisiere die Anzeige des Streaks
                streakCountText.setText(String.valueOf(currentStreak[0]));
            });

            circlesRow.getChildren().add(circle); // Füge den Kreis zur HBox hinzu
        }

        // Füge den Text (Streak-Zähler) und den "X"-Button rechts neben den Kreisen hinzu
        circlesRow.getChildren().addAll(streakCountText, removeButton);

        // Setze den Abstand zwischen den Zeilen (VBox, die die Kreise aufnimmt)
        if (CirclePane instanceof VBox) {
            ((VBox) CirclePane).setSpacing(33); // Vertikaler Abstand zwischen den Zeilen
        }

        // Füge die neue HBox mit den Kreisen und dem Streak-Zähler zur CirclePane hinzu
        CirclePane.getChildren().add(circlesRow);
    }

    // Methode zum Berechnen des Streaks, bei der der Streak erst unterbrochen wird, wenn zwei Tage in Folge ausgelassen werden
    private void calculateStreak(boolean[] circleClicked, int[] currentStreak) {
        currentStreak[0] = 0; // Setze den Streak zurück
        int missedCount = 0;  // Zähler für ausgelassene Tage in Folge
        boolean streakIsActive = true; // Variable, um den Streak zu verfolgen

        for (boolean clicked : circleClicked) {
            if (clicked) {
                if (missedCount > 0) {
                    missedCount = 0; // Setze den Zähler zurück, wenn ein Tag erledigt wurde
                }
                currentStreak[0]++; // Erhöhe den Streak-Zähler
            } else {
                missedCount++; // Erhöhe den Zähler für ausgelassene Tage
                if (missedCount >= 2) { // Streak unterbrochen
                    streakIsActive = false; // Setze Streak-Status auf inaktiv
                    break; // Beende die Schleife
                }
            }
        }

        // Wenn der Streak aktiv ist, gebe die Anzahl der Tage zurück
        if (streakIsActive) {
            currentStreak[0] += missedCount; // Füge die ausgelassenen Tage zum Streak hinzu
        }
    }
}
