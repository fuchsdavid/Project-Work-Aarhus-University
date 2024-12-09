package org.database.services;

import com.example.habittracker.utils.DatabaseConnection;
import org.database.Habit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardService {
    // Lädt alle Habits eines bestimmten Benutzers
    public List<Habit> getUserHabits(String username) {
        List<Habit> habits = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM habit WHERE UserName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Habit habit = new Habit();
                habit.setId(resultSet.getInt("HabitID"));
                habit.setHabitName(resultSet.getString("HabitName"));
                habit.setCurrentStreak(resultSet.getInt("CurrentStreak"));
                habit.setLongestStreak(resultSet.getInt("LongestStreak"));
                habits.add(habit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habits;
    }

    // Fügt einen neuen Habit hinzu
    public void addHabit(String habitName, String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Adding habit: " + habitName + " for user: " + username);

            String query = "INSERT INTO habit (HabitName, UserName, CurrentStreak, LongestStreak) VALUES (?, ?, 0, 0)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, habitName);
            statement.setString(2, username);

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Löscht einen Habit anhand seiner ID
    public boolean deleteHabit(int habitId) {
        String deleteQuery = "DELETE FROM habit WHERE HabitID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, habitId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Rückgabe true, wenn mindestens eine Zeile gelöscht wurde

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Rückgabe false bei einem Fehler
        }
    }


    // Aktualisiert den aktuellen Streak eines Habits
    public void updateHabitStreak(int habitId, int currentStreak, int longestStreak) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE habit SET CurrentStreak = ?, LongestStreak = ? WHERE HabitID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, currentStreak);
            statement.setInt(2, longestStreak);
            statement.setInt(3, habitId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
