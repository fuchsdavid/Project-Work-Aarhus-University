package com.example.habittracker;

import com.example.habittracker.utils.DomainUser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.database.Habit;
import org.database.services.DashboardService;
import org.database.services.UserService;

import java.util.List;
import java.util.Optional;

public class DashboardController {
    @FXML
    Button newHabitButton;

    @FXML
    VBox HabitPane; // Pane für die Habits
    @FXML
    VBox CirclePane; // Pane für die Streak-Kreise

    private final Color circleColor = Color.web("#3A51CA");
    private final Color defaultColor = Color.WHITE;

    DashboardService dashboardService;
    private String currentUser; // Der Benutzername des aktuell eingeloggten Benutzers
    Stage stage;
    Scene scene;
    DomainUser user;

    UserService userService;

    private void receiveData() {
        stage = (Stage) (HabitPane.getScene().getWindow());
        user = (DomainUser) stage.getUserData();

        // Check if user is null (for testing purposes)
        if (user == null) {
            user = new DomainUser("test");
        }

        System.out.println(user.GetUserName());

    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            receiveData();
            dashboardService = new DashboardService();
            currentUser = user.GetUserName(); // Benutzername aus Session laden
            loadHabits();

            // Event-Handler für den Button "+ New Habit"
            newHabitButton.setOnAction(event -> addNewHabit());
        });
    }

    private void loadHabits() {
        List<Habit> habits = dashboardService.getUserHabits(currentUser);

        for (Habit habit : habits) {
            addHabitToPane(habit);
        }
    }

    private void addNewHabit() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add a new Habit");
        dialog.setHeaderText(null);
        dialog.setContentText("Name your habit:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(habitName -> {
            if (!habitName.trim().isEmpty()) {
                dashboardService.addHabit(habitName, currentUser);
                Habit newHabit = new Habit(habitName, null); // Dummy Habit für Anzeige
                addHabitToPane(newHabit);
            }
        });
    }

    void addHabitToPane(Habit habit) {
        VBox habitContainer = new VBox();
        habitContainer.setSpacing(5);

        Text habitText = new Text(habit.getHabitName());
        habitText.setFont(new Font(18));
        habitText.setFill(Color.WHITE);

        Text longestStreakText = new Text("Longest Streak: " + habit.getLongestStreak());
        longestStreakText.setFont(new Font(10));
        longestStreakText.setFill(Color.WHITE);

        habitContainer.getChildren().addAll(habitText, longestStreakText);
        HabitPane.getChildren().add(habitContainer);

        HBox circlesRow = new HBox();
        circlesRow.setSpacing(20); // Abstand zwischen den Kreisen in der Reihe

        final int[] currentStreak = {habit.getCurrentStreak()};
        final int[] longestStreak = {habit.getLongestStreak()};
        boolean[] circleClicked = new boolean[7];

        Text streakCountText = new Text(String.valueOf(currentStreak[0]));
        streakCountText.setFont(Font.font("System", FontWeight.BOLD, 14));
        streakCountText.setFill(Color.web("#22369F"));

        Button removeButton = new Button("X");
        removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #22369f; -fx-font-size: 10; -fx-padding: 0;");
        removeButton.setMinSize(20, 20);
        removeButton.setMaxSize(20, 20);

        removeButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Do you really want to delete " + habit.getHabitName() + "?");
            alert.getButtonTypes().setAll(ButtonType.CANCEL, ButtonType.OK);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Habit aus der SQL-Datenbank löschen
                boolean isDeleted = dashboardService.deleteHabit(habit.getId());
                if (isDeleted) {
                    // Entferne das Habit aus der Benutzeroberfläche
                    HabitPane.getChildren().remove(habitContainer);
                    CirclePane.getChildren().remove(circlesRow);
                    System.out.println("Habit successfully deleted: " + habit.getHabitName());
                } else {
                    System.err.println("Failed to delete habit from database: " + habit.getHabitName());
                }
            }
        });

        for (int i = 0; i < 7; i++) {
            Circle circle = new Circle(10);
            circle.setFill(defaultColor);

            int finalI = i;
            circle.setOnMouseClicked(event -> {
                if (!circleClicked[finalI]) {
                    circle.setFill(circleColor);
                    circleClicked[finalI] = true;
                    currentStreak[0]++;
                    if (currentStreak[0] > longestStreak[0]) {
                        longestStreak[0] = currentStreak[0];
                    }
                } else {
                    circle.setFill(defaultColor);
                    circleClicked[finalI] = false;
                    currentStreak[0]--;
                }
                streakCountText.setText(String.valueOf(currentStreak[0]));
                dashboardService.updateHabitStreak(habit.getId(), currentStreak[0], longestStreak[0]);
            });

            circlesRow.getChildren().add(circle);
        }

        circlesRow.getChildren().addAll(streakCountText, removeButton);

        // Erhöhe den Abstand zwischen den Reihen
        CirclePane.setSpacing(35); // Abstand zwischen den Reihen im CirclePane (VBox)

        CirclePane.getChildren().add(circlesRow);
    }
}
