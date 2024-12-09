package com.example.habittracker;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.database.Habit;
import org.database.User;
import org.database.services.DashboardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.util.Collections;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class DashboardControllerTest {

    private DashboardController controller;
    private DashboardService mockDashboardService;
    private Stage stage;

    private User user;

    @Start
    public void start(Stage stage) throws Exception {
        // Mock the DashboardService
        mockDashboardService = mock(DashboardService.class);

        // Create an instance of DashboardController
        controller = new DashboardController();
        controller.dashboardService = mockDashboardService;

        // Initialize the stage and scene
        VBox root = new VBox();
        VBox habitPane = new VBox();
        VBox circlePane = new VBox();
        Button newHabitButton = new Button("+ New Habit");

        user = new User("test", "test", "test", 18, "test", "test");

        controller.HabitPane = habitPane;
        controller.CirclePane = circlePane;
        controller.newHabitButton = newHabitButton;

        root.getChildren().addAll(habitPane, circlePane, newHabitButton);

        stage.setScene(new javafx.scene.Scene(root, 600, 400));
        stage.show();

        sleep(500);
    }

    @Test
    public void testLoadHabits(FxRobot robot) {
        // Mock habits returned by the service
        Habit mockHabit = new Habit("Test Habit", user);
        when(mockDashboardService.getUserHabits(Mockito.anyString()))
                .thenReturn(Collections.singletonList(mockHabit));

        // Simulate initialization
        robot.interact(controller::initialize);


    }

    @Test
    public void testAddNewHabit(FxRobot robot) {
        // Simulate the dialog returning a habit name
        doAnswer(invocation -> {
            String habitName = invocation.getArgument(0);
            return new Habit(habitName, new User());
        }).when(mockDashboardService).addHabit(Mockito.anyString(), Mockito.anyString());

        // Interact with the new habit button
        robot.clickOn(controller.newHabitButton);

        // Simulate dialog input
        robot.write("New Test Habit");
        robot.press(javafx.scene.input.KeyCode.ENTER).release(javafx.scene.input.KeyCode.ENTER);


    }

    @Test
    public void testDeleteHabit(FxRobot robot) {
        // Create a dummy habit
        Habit mockHabit = new Habit("Habit To Delete", user);
        when(mockDashboardService.deleteHabit(Mockito.anyInt())).thenReturn(true);

        // Add the habit to the pane
        Platform.runLater(() -> controller.addHabitToPane(mockHabit));
        WaitForAsyncUtils.waitForFxEvents();

        // Ensure the remove button is correctly added and has the correct id
        Platform.runLater(() -> {
            VBox habitBox = (VBox) controller.HabitPane.getChildren().get(0);
            Button removeButton = new Button("Remove");
            removeButton.setId("removeButton");
            habitBox.getChildren().add(removeButton);
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Simulate clicking the delete button
        robot.clickOn("#removeButton");
        WaitForAsyncUtils.waitForFxEvents();


    }
}