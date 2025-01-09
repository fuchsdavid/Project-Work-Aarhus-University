package org.database.services;

import com.example.habittracker.utils.DatabaseConnection;
import org.database.Habit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardService {

    public List<Habit> getUserHabits(String username) {
        List<Habit> habits = new ArrayList<>();
        String query = "SELECT * FROM habit WHERE UserName = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

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
            System.err.println("Error loading user habits for username: " + username);
            e.printStackTrace();
        }
        return habits;
    }

    public Habit addHabit(String habitName, String username) {
        String insertQuery = "INSERT INTO habit (HabitName, UserName, CurrentStreak, LongestStreak) VALUES (?, ?, 0, 0)";
        String selectQuery = "SELECT * FROM habit WHERE UserName = ? AND HabitName = ? ORDER BY HabitID DESC LIMIT 1";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

            // Habit in die Datenbank einfügen
            insertStatement.setString(1, habitName);
            insertStatement.setString(2, username);
            int rowsInserted = insertStatement.executeUpdate();

            if (rowsInserted > 0) {
                // Neu eingefügten Habit abrufen
                selectStatement.setString(1, username);
                selectStatement.setString(2, habitName);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    Habit habit = new Habit();
                    habit.setId(resultSet.getInt("HabitID"));
                    habit.setHabitName(resultSet.getString("HabitName"));
                    habit.setCurrentStreak(resultSet.getInt("CurrentStreak"));
                    habit.setLongestStreak(resultSet.getInt("LongestStreak"));
                    return habit;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding habit: " + habitName + " for username: " + username);
            e.printStackTrace();
        }
        return null;
    }


    public boolean updateEntry(int habitId, String date, int value) {
        String query = "INSERT INTO entry (HabitID, Date, Value) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE Value = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, habitId);
            statement.setString(2, date);
            statement.setInt(3, value);
            statement.setInt(4, value);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating entry for HabitID: " + habitId + " on Date: " + date);
            e.printStackTrace();
            return false;
        }
    }

    public Integer getEntryStatus(int habitId, String date) {
        String query = "SELECT Value FROM entry WHERE HabitID = ? AND Date = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, habitId);
            statement.setString(2, date);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("Value");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching entry status for HabitID: " + habitId + " on Date: " + date);
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateHabitStreak(int habitId, int currentStreak, int longestStreak) {
        String query = "UPDATE habit SET CurrentStreak = ?, LongestStreak = ? WHERE HabitID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, currentStreak);
            statement.setInt(2, longestStreak);
            statement.setInt(3, habitId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Rückgabe true, wenn mindestens eine Zeile aktualisiert wurde
        } catch (SQLException e) {
            System.err.println("Error updating streak for HabitID: " + habitId);
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHabit(int habitId) {
        String deleteEntriesQuery = "DELETE FROM entry WHERE HabitID = ?";
        String deleteHabitQuery = "DELETE FROM habit WHERE HabitID = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Lösche zuerst die Einträge in der Tabelle "entry"
            try (PreparedStatement deleteEntriesStmt = connection.prepareStatement(deleteEntriesQuery)) {
                deleteEntriesStmt.setInt(1, habitId);
                deleteEntriesStmt.executeUpdate();
            }

            // Lösche anschließend den Habit in der Tabelle "habit"
            try (PreparedStatement deleteHabitStmt = connection.prepareStatement(deleteHabitQuery)) {
                deleteHabitStmt.setInt(1, habitId);
                int rowsAffected = deleteHabitStmt.executeUpdate();
                return rowsAffected > 0; // Rückgabe true, wenn mindestens eine Zeile gelöscht wurde
            }
        } catch (SQLException e) {
            System.err.println("Error deleting HabitID: " + habitId);
            e.printStackTrace();
            return false;
        }
    }

}
