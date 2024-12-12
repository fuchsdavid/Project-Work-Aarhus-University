package com.example.habittracker;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.database.Habit;
import org.database.services.DashboardService;

import java.util.List;
import java.util.Optional;

public class HabitViewController {

    public Button viewDetailsButton;
    public HBox streakButtonsRow;
    public Button removeButton;
    public VBox habitContainer;

    @FXML
    private Text habitNameText;

    @FXML
    private Text longestStreakText;


    @FXML
    private Text streakCountText;

    @FXML
    private Circle day1Button, day2Button, day3Button, day4Button, day5Button, day6Button, day7Button;

    private int currentStreak = 0;
    private int longestStreak = 0;
    private int lastClickedDay = -1;

    private final Color defaultColor = Color.DARKGRAY;
    private final Color clickedColor = Color.PURPLE;

    private List<Circle> streakButtons;

    public void setHabitData(Habit habit, DashboardService dashboardService) {
        habitNameText.setText(habit.getHabitName());
        currentStreak = habit.getCurrentStreak();
        longestStreak = habit.getLongestStreak();

        streakCountText.setText(String.valueOf(currentStreak));
        longestStreakText.setText(String.valueOf(longestStreak));

        streakButtons = List.of(day1Button, day2Button, day3Button, day4Button, day5Button, day6Button, day7Button);

        for (int i = 0; i < streakButtons.size(); i++) {
            int dayIndex = i;
            Circle dayButton = streakButtons.get(i);

            // Reset color on load
            dayButton.setFill(defaultColor);

            dayButton.setOnMouseClicked(event -> {
                if (lastClickedDay == -1 || dayIndex == lastClickedDay + 1) {
                    // Valid sequential click
                    currentStreak++;
                    if (currentStreak > longestStreak) {
                        longestStreak = currentStreak;
                    }
                    lastClickedDay = dayIndex;
                    dayButton.setFill(clickedColor); // Update color to clicked
                } else {
                    // Reset streak if the sequence is broken
                    resetStreak();
                    currentStreak = 1;
                    lastClickedDay = dayIndex;
                    dayButton.setFill(clickedColor);
                }

                // Update streak in UI
                streakCountText.setText(String.valueOf(currentStreak));
                longestStreakText.setText(String.valueOf(longestStreak));

                // Update streak in the database
                dashboardService.updateHabitStreak(habit.getId(), currentStreak, longestStreak);
            });
        }

        // Remove button action
        removeButton.setOnAction(event -> {
            dashboardService.deleteHabit(habit.getId());
            // Additional logic to remove the habit from the UI
        });

        // View details button action (if necessary)
        viewDetailsButton.setOnAction(event -> {
            // Implement logic to view detailed information about the habit
        });
    }

    private void resetStreak() {
        currentStreak = 0;
        lastClickedDay = -1;
        for (Circle dayButton : streakButtons) {
            dayButton.setFill(defaultColor); // Reset all circles to default color
        }
    }
}
