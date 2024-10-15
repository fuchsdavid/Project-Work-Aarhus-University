package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox; // Import der VBox
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class RowController {

    @FXML
    private GridPane gridPane; // Haupt-Gitter für Habits

    @FXML
    private Button addRowButton;

    private int currentRow = 1; // Starten bei 1, um Platz für die Wochentage zu schaffen

    // Diese Methode wird aufgerufen, wenn der Button gedrückt wird
    @FXML
    public void addNewTextRow() {
        // Erstelle einen TextInputDialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add a new Habit");
        dialog.setHeaderText(null);
        dialog.setContentText("Name your habit");

        // Setze die Eingabewerte zurück
        dialog.getEditor().clear();

        // Zeige den Dialog und warte auf die Eingabe
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(inputText -> {
            if (!inputText.isEmpty()) {
                // Erstelle eine neue VBox für den Habit und den Streak-Text
                VBox habitBox = new VBox();
                habitBox.setSpacing(5); // Vertikaler Abstand zwischen Habit und Longest Streak

                // Erstelle eine neue Textzeile für den Habit
                Text habitText = new Text(inputText);
                habitText.setFont(new Font(18)); // Setze die Schriftgröße auf 18px

                // Füge den Habit-Namen zur VBox hinzu
                habitBox.getChildren().add(habitText);

                // Füge den Text "Longest Streak: " hinzu
                Text streakText = new Text("Longest Streak: ");
                streakText.setFont(new Font(10)); // Setze die Schriftgröße auf 10px
                habitBox.getChildren().add(streakText); // Füge den Streak-Text zur VBox hinzu

                // Füge die VBox für den Habit zur aktuellen Zeile des GridPane hinzu
                gridPane.add(habitBox, 0, currentRow);

                // Füge eine HBox für die Kreise hinzu
                HBox hBox = new HBox();
                hBox.setSpacing(10); // Abstand zwischen den Kreisen
                gridPane.add(hBox, 1, currentRow); // Füge die HBox in die aktuelle Zeile ein

                // Zähler für angeklickte Kreise
                int[] clickCounter = {0};
                Text countText = new Text("0");
                countText.setFont(new Font(14));
                gridPane.add(countText, 2, currentRow); // Zähler in die dritte Spalte hinzufügen

                for (int i = 0; i < 7; i++) {
                    Circle circle = createCircle(clickCounter, countText); // Erstelle den Kreis
                    hBox.getChildren().add(circle); // Füge den Kreis zur HBox hinzu
                }

                // Erhöhe den aktuellen Zeilenindex
                currentRow++; // Erhöhe um 1, um die nächste Habit-Reihe zu erstellen
            }
        });
    }

    // Methode zum Erstellen eines Kreises
    private Circle createCircle(int[] clickCounter, Text countText) {
        Circle circle = new Circle(10);
        circle.setFill(Color.LIGHTGRAY);

        // Event-Handler für den Kreis
        circle.setOnMouseClicked(event -> {
            if (circle.getFill() == Color.LIGHTGRAY) {
                circle.setFill(Color.web("#3A51CA"));
                clickCounter[0]++;
            } else {
                circle.setFill(Color.LIGHTGRAY);
                clickCounter[0]--;
            }
            countText.setText(String.valueOf(clickCounter[0]));
        });

        return circle;
    }

    // Methode zum Hinzufügen von Wochentagen und Datum
    @FXML
    public void initialize() {
        // Heutiges Datum und Start der Woche
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1); // Montag dieser Woche

        // Wochentagsabkürzungen und Datumsangaben
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM");

        // Erstelle eine neue GridPane für Wochentage
        GridPane weekDaysPane = new GridPane();
        weekDaysPane.setHgap(10); // Horizontale Abstände zwischen den Wochentagen
        weekDaysPane.setVgap(5); // Vertikale Abstände zwischen den Wochentagen

        // Füge Wochentage und entsprechende Daten hinzu
        for (int i = 0; i < 7; i++) {
            LocalDate currentDay = startOfWeek.plusDays(i);
            String weekday = currentDay.getDayOfWeek().toString().substring(0, 3); // Erhalte die ersten 3 Buchstaben des Wochentags
            Text weekdayText = new Text(weekday + " " + dateFormatter.format(currentDay));
            weekdayText.setFont(new Font(12));
            weekDaysPane.add(weekdayText, i, 0); // Füge den Wochentag zur ersten Zeile der neuen GridPane hinzu
        }

        // Füge die Wochentage-Gitter in die erste Zeile des Haupt-Gitters ein
        gridPane.add(weekDaysPane, 0, 0, 3, 1); // Füge das Wochen-Gitter ein und spanne es über 3 Spalten

        // Optional: Füge einen Rahmen um die Wochentage hinzu
        weekDaysPane.setStyle("-fx-border-color: black; -fx-padding: 5;");
    }
}
