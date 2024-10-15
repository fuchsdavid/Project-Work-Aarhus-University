package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Optional;

public class FourthTESTDashboardController {

    @FXML
    private Button newHabitButton;

    @FXML
    private VBox HabitPane; // Pane für die neuen Habits
    @FXML
    private HBox CircleHbox; // HBox für die Kreise

    // Definieren des spezifischen Blautons
    private final Color circleColor = Color.web("#3A51CA");
    private final Color defaultColor = Color.WHITE;

    @FXML
    public void initialize() {
        // Event-Handler für den Button + new habit
        newHabitButton.setOnAction(event -> addNewHabit());
    }

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

        // Erstelle 7 Kreise für die Tage der Woche (Montag bis Sonntag)
        CircleHbox.getChildren().clear(); // Vorherige Kreise löschen
        for (int i = 0; i < 7; i++) {
            Circle circle = new Circle(10); // Erstelle einen Kreis mit Radius 10
            circle.setFill(defaultColor); // Standardfarbe (weiß)
            circle.setOnMouseClicked(this::handleCircleClick); // Setze den Event-Handler für den Klick
            CircleHbox.getChildren().add(circle); // Füge den Kreis zur HBox hinzu
        }
    }
}
