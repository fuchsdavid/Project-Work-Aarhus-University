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

import java.util.Optional;

public class ThirdTESTDashboardController {

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

    @FXML
    private GridPane CircleGridPane;

    @FXML
    private Button addRowButton;

    // Definieren des spezifischen Blautons
    private final Color circleColor = Color.web("#3A51CA");
    private final Color defaultColor = Color.WHITE;

    private int currentRow = 3; // Starten bei Zeile 3

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

    // Diese Methode wird aufgerufen, wenn der Button gedrückt wird
    @FXML
    public void addNewTextRow() {
        // Erstelle einen TextInputDialog für die Eingabe des Habit-Namens
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add a new Habit");
        dialog.setHeaderText(null);
        dialog.setContentText("Name your habit:");

        // Setze die Eingabewerte zurück, um sicherzustellen, dass das Feld leer ist
        dialog.getEditor().clear();

        // Zeige den Dialog und warte auf die Eingabe
        Optional<String> result = dialog.showAndWait();

        // Wenn der Benutzer eine Eingabe gemacht hat
        result.ifPresent(inputText -> {
            if (!inputText.isEmpty()) {
                // Erstelle einen VBox-Container für die Texte (Habit und Longest Streak)
                VBox vBox = new VBox();
                vBox.setSpacing(2);

                // Text für den Habit
                Text habitText = new Text(inputText);
                habitText.setFont(new Font(14));

                // Text für "Longest Streak"
                Text longestStreakText = new Text("Longest Streak:");
                longestStreakText.setFont(new Font(8));

                // Füge die Texte zur VBox hinzu
                vBox.getChildren().addAll(habitText, longestStreakText);

                // Füge die VBox in die neue Zeile des GridPane hinzu (erste Spalte)
                CircleGridPane.add(vBox, 0, currentRow);

                // Erstelle neue Kreise für die Tage der Woche (Montag bis Sonntag)
                for (int i = 1; i <= 7; i++) {
                    Circle circle = new Circle(10); // Erstelle einen Kreis mit Radius 10
                    circle.setFill(defaultColor); // Standardfarbe (weiß)
                    circle.setOnMouseClicked(this::handleCircleClick); // Setze den Event-Handler für den Klick

                    // Füge den Kreis zur GridPane in der entsprechenden Spalte (für jeden Wochentag)
                    CircleGridPane.add(circle, i, currentRow);
                }

                // Erhöhe den Zeilenindex, damit bei der nächsten Eingabe eine neue Zeile hinzugefügt wird
                currentRow++; // Erhöhe den Zähler für die nächste Zeile
            }
        });
    }
}
