package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ButtonController {

    @FXML
    private Circle circle;

    // Flag, um den aktuellen Zustand zu verfolgen
    private boolean isBlue = false;

    @FXML
    private void mouseclicked(MouseEvent event) {
        // Umschalten der Farbe basierend auf dem Flag
        if (isBlue) {
            circle.setFill(Color.WHITE);  // Setze die Farbe auf Weiß
        } else {
            circle.setFill(Color.BLUE);   // Setze die Farbe auf Blau
        }
        isBlue = !isBlue; // Zustand umschalten
    }
}
