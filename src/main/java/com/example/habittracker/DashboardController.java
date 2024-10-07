package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;

public class DashboardController {

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
}
