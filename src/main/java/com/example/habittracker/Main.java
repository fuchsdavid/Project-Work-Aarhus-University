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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 640);
        primaryStage.setTitle("Habit tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Startet die JavaFX-Anwendung
    }
}
