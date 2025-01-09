package com.example.habittracker;

import com.example.habittracker.utils.DomainUser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

import java.io.IOException;
import java.time.LocalDate;
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
    DomainUser user;

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

    private void receiveData() {
        stage = (Stage) (HabitPane.getScene().getWindow());
        user = (DomainUser) stage.getUserData();

        if (user == null) {
            user = new DomainUser("test");
        }

        System.out.println("Current User: " + user.GetUserName());
    }

    void loadHabits() {
        List<Habit> habits = dashboardService.getUserHabits(currentUser);
        for (Habit habit : habits) {
            addHabitToPane(habit);
        }
    }

    void addNewHabit() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add a new Habit");
        dialog.setHeaderText(null);
        dialog.setContentText("Name your habit:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(habitName -> {
            if (!habitName.trim().isEmpty()) {
                // Habit zur Datenbank hinzufügen und das Habit-Objekt abrufen
                Habit newHabit = dashboardService.addHabit(habitName, currentUser);

                if (newHabit != null && newHabit.getId() != null) {
                    // Neue Habit sofort zur Benutzeroberfläche hinzufügen
                    addHabitToPane(newHabit);
                } else {
                    System.err.println("Failed to load the newly added habit: " + habitName);
                }
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

        for (int i = 0; i < 7; i++) {
            Circle circle = new Circle(10);
            String date = LocalDate.now().minusDays(6 - i).toString(); // Datum für jeden Tag der Woche
            Integer status = dashboardService.getEntryStatus(habit.getId(), date);

            circle.setFill((status != null && status == 1) ? circleColor : defaultColor);

            int finalI = i;
            circle.setOnMouseClicked(event -> {
                boolean isClicked = circle.getFill().equals(defaultColor);
                circle.setFill(isClicked ? circleColor : defaultColor);

                int value = isClicked ? 1 : 0;
                if (isClicked) {
                    currentStreak[0]++;
                    longestStreak[0] = Math.max(longestStreak[0], currentStreak[0]);
                } else {
                    currentStreak[0]--;
                }

                dashboardService.updateEntry(habit.getId(), date, value);
                dashboardService.updateHabitStreak(habit.getId(), currentStreak[0], longestStreak[0]);
            });

            circlesRow.getChildren().add(circle);
        }

        Button removeButton = getRemoveButton(habit, habitContainer, circlesRow);
        Button viewDetailsButton = getViewDetailsButton(habit);

        circlesRow.getChildren().addAll(removeButton, viewDetailsButton);

        CirclePane.setSpacing(35); // Abstand zwischen den Reihen im CirclePane (VBox)
        CirclePane.getChildren().add(circlesRow);
    }

    private Button getViewDetailsButton(Habit habit) {
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #22369f; -fx-font-size: 10; -fx-padding: 0;");

        viewDetailsButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HabitDetails.fxml"));
                Parent root = loader.load();

                HabitDetailsController controller = loader.getController();
                controller.setHabit(habit);

                Stage stage = (Stage) viewDetailsButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return viewDetailsButton;
    }

    private Button getRemoveButton(Habit habit, VBox habitContainer, HBox circlesRow) {
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
                boolean isDeleted = dashboardService.deleteHabit(habit.getId());
                if (isDeleted) {
                    HabitPane.getChildren().remove(habitContainer);
                    CirclePane.getChildren().remove(circlesRow);
                } else {
                    System.err.println("Failed to delete habit from database: " + habit.getHabitName());
                }
            }
        });
        return removeButton;
    }
}
