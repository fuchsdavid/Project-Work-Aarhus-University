package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.util.Optional;

public class TwoTESTDashboardController {

    @FXML
    private Circle circleMon;
    @FXML
    private Circle circleTue;
    @FXML
    private Circle circleWed;
    @FXML
    private Circle circleThu;
    @FXML
    private Circle circleFri;
    @FXML
    private Circle circleSat;
    @FXML
    private Circle circleSun;

    @FXML
    private Button newHabitButton;

    @FXML
    private VBox HabitPane; // Pane für die neuen Habits, jetzt eine VBox

    // Definieren des spezifischen Blautons
    private final Color circleColor = Color.web("#3A51CA");
    private final Color defaultColor = Color.WHITE;

    @FXML
    private void handleCircleClick(MouseEvent event) {
        Circle clickedCircle = (Circle) event.getSource();
        if (clickedCircle.getFill() == defaultColor) {
            clickedCircle.setFill(circleColor);
        } else {
            clickedCircle.setFill(defaultColor);
        }
    }

    @FXML
    public void initialize() {
        // Event-Handler für den Button + new habit
        newHabitButton.setOnAction(event -> addNewHabit());
    }

    // Methode, um einen neuen Habit zu erstellen
    @FXML
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

        // Füge die VBox zum HabitPane hinzu
        HabitPane.getChildren().add(habitContainer); // Füge die neue VBox einfach hinzu
    }

    @FXML
    private GridPane gridPane;

    @FXML
    private Button addRowButton;

    private int currentRow = 0; // Zählt die aktuellen Zeilen im GridPane

    // Diese Methode wird aufgerufen, wenn der Button gedrückt wird
    @FXML
    public void addNewTextRow() {
        // Erstelle einen TextInputDialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add a new Habit"); // Setze den Titel des Dialogs
        dialog.setHeaderText(null); // Keine Kopfzeile
        dialog.setContentText("Name your habit"); // Inhaltstext für das Eingabefeld

        // Setze die Eingabewerte zurück, um sicherzustellen, dass das Feld leer ist
        dialog.getEditor().clear();

        // Zeige den Dialog und warte auf die Eingabe
        Optional<String> result = dialog.showAndWait();

        // Wenn der Benutzer einen Text eingegeben hat
        result.ifPresent(inputText -> {
            if (!inputText.isEmpty()) {
                // Erstelle einen VBox-Container für die Texte
                VBox vBox = new VBox(); // Erstelle eine neue VBox
                vBox.setSpacing(2); // Setze den Abstand zwischen den Texten auf 2px

                // Erstelle ein neues Text-Element mit Schriftgröße 14px
                Text habitText = new Text(inputText);
                habitText.setFont(new Font(14)); // Setze die Schriftgröße auf 14px

                // Erstelle den "Longest Streak"-Text mit Schriftgröße 8px
                Text longestStreakText = new Text("Longest Streak:");
                longestStreakText.setFont(new Font(8)); // Setze die Schriftgröße auf 8px

                // Füge die Texte zur VBox hinzu
                vBox.getChildren().addAll(habitText, longestStreakText);

                // Füge die VBox in die neue Zeile des GridPane hinzu
                gridPane.add(vBox, 0, currentRow);

                // Erhöhe den aktuellen Zeilenindex
                currentRow++; // Erhöhe um 1, da nur eine Zeile mit VBox hinzugefügt wird
            }
        });
    }
}
