package com.example.habittracker;

import com.example.habittracker.api.GoogleCalendarAuth;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.database.Habit;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class HabitDetailsController {

    @FXML
    private Label habitNameLabel, habitFrequencyLabel, habitReminderTimeLabel, habitStreakLabel;
    @FXML
    private Button editButton, deleteButton, syncButton, backButton;

    private String habitName = "Reading";
    private int habitStreak = 5;

    @FXML
    public void initialize() {
        // Populate labels with habit details
        habitNameLabel.setText(habitName);
        String habitFrequency = "Daily";
        habitFrequencyLabel.setText(habitFrequency);
        String habitReminderTime = "08:00 AM";
        habitReminderTimeLabel.setText(habitReminderTime);
        habitStreakLabel.setText(habitStreak + " Days");
    }

    public void setHabit(Habit habit) {
        habitName = habit.getHabitName();
        habitStreak = habit.getCurrentStreak();
        habitNameLabel.setText(habitName);
        habitStreakLabel.setText(habitStreak + "");
    }

    @FXML
    private void handleEdit() {
        // Logic to open Edit Habit page
        showAlert("Edit", "Edit functionality is not implemented yet.");
    }

    @FXML
    private void handleDelete() {
        // Logic to delete the habit
        showAlert("Delete", "Habit has been deleted.");
    }

    @FXML
    private void handleSync() {
        try {
            // Initialize Google Calendar API client
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Calendar service = new Calendar.Builder(
                    HTTP_TRANSPORT, GoogleCalendarAuth.JSON_FACTORY, GoogleCalendarAuth.getCredentials(HTTP_TRANSPORT))
                    .setApplicationName("Habit Tracker")
                    .build();

            // Set up habit details
            String habitTitle = "Habit: " + habitName;
            String habitDescription = "Reminder for habit: " + habitName;
            int habitDurationMinutes = 30; // Duration of the event in minutes
            LocalDate startDate = LocalDate.now(); // Start from today


            for (int i = 0; i < 7; i++) {
                LocalDate eventDate = startDate.plusDays(i);

                // Set the event start and end times
                LocalDateTime startDateTime = eventDate.atTime(8, 0); // 8:00 AM
                Date startDateConverted = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
                Date endDateConverted = Date.from(startDateTime.plusMinutes(habitDurationMinutes).atZone(ZoneId.systemDefault()).toInstant());

                // Create a new event
                Event event = new Event()
                        .setSummary(habitTitle)
                        .setDescription(habitDescription)
                        .setStart(new EventDateTime().setDateTime(new DateTime(startDateConverted)).setTimeZone("UTC"))
                        .setEnd(new EventDateTime().setDateTime(new DateTime(endDateConverted)).setTimeZone("UTC"));

                // Add the event to the user's calendar
                service.events().insert("primary", event).execute();
            }

            // Show success message
            showAlert("Success", "Habit synced with Google Calendar successfully!");

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to sync with Google Calendar: " + e.getMessage());
        }
    }


    @FXML
    private void handleBack() {
        try {
            // Load the Dashboard view
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard-view.fxml")));

            // Get the current stage from any node in the current scene
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

            // Show an alert to inform the user of the error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Navigation Failed");
            alert.setContentText("Unable to load the Dashboard view. Please try again.");
            alert.showAndWait();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
