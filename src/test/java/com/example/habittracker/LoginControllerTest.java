package com.example.habittracker;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.database.User;
import org.database.services.UserService;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest extends ApplicationTest {

    private TextField login;
    private PasswordField password;
    private Label feedback;
    private LoginController controller;
    private UserService mockUserService;

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        AnchorPane root = loader.load();

        // Get the controller
        controller = loader.getController();

        // Initialize JavaFX controls
        login = (TextField) root.lookup("#login");
        password = (PasswordField) root.lookup("#password");
        feedback = (Label) root.lookup("#feedback");

        // Mock the UserService
        mockUserService = mock(UserService.class);
        controller.setUserService(mockUserService);

        // Create a new scene for testing
        Scene scene = new Scene(root, 1024, 640);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testEmptyFieldsValidation() {
        // Leave all fields empty and call the form validation
        Platform.runLater(() -> {
            login.setText("");
            password.setText("");
            controller.processLoginForm();
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("Username or password is incorrect", feedback.getText());
    }

    @Test
    public void testIncorrectCredentials() {

        // Fill fields with incorrect credentials
        Platform.runLater(() -> {
            login.setText("test");
            password.setText("wrongpassword");
            controller.processLoginForm();
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("Username or password is incorrect", feedback.getText());
    }

    @Test
    public void testSuccessfulLogin() {

        // Fill fields with correct credentials
        Platform.runLater(() -> {
            login.setText("test");
            password.setText("password123");
            controller.processLoginForm();
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("", feedback.getText());
    }
}