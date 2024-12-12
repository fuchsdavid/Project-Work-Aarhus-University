package com.example.habittracker;

import com.example.habittracker.utils.DomainUser;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

public class DashboardControllerTest extends ApplicationTest {

    private DashboardController controller;
    private VBox habitPane, circlePane;
    private Button newHabitButton;

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard-view.fxml"));
        Parent root = loader.load();

        // Get the controller
        controller = loader.getController();

        // Initialize JavaFX controls
        habitPane = (VBox) root.lookup("#HabitPane");
        circlePane = (VBox) root.lookup("#CirclePane");
        newHabitButton = (Button) root.lookup("#newHabitButton");

        // Create a new scene for testing
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testInitialize() {
        // Simulate receiving data
        Platform.runLater(() -> {
            controller.initialize();
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the initial state
        assertNotNull(habitPane);
        assertNotNull(circlePane);
        assertNotNull(newHabitButton);
    }

    @Test
    public void testAddNewHabit() {
        // Simulate adding a new habit
        Platform.runLater(() -> {
            controller.initialize();
            controller.addNewHabit();
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the new habit is added to the pane
        assertFalse(habitPane.getChildren().isEmpty());
    }

    @Test
    public void testLoadHabits() {
        // Simulate loading habits
        Platform.runLater(() -> {
            controller.initialize();
            controller.loadHabits();
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the habits are loaded into the pane
        assertFalse(habitPane.getChildren().isEmpty());
    }
}