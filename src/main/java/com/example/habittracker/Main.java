package com.example.habittracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Lade das Dashboard FXML-Layout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Test-Dashboard-view.fxml")); // Stelle sicher, dass der Name korrekt ist
        Parent root = loader.load();

        // Setze die Szene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Circle Color Changer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Startet die JavaFX-Anwendung
    }
}
