package com.example.habittracker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox; // Importiere VBox
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.util.Optional;

public class RowController {

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
