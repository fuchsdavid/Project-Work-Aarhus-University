package com.example.habittracker;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
        // Initialize JavaFX controls
        login = new TextField();
        login.setId("login");
        password = new PasswordField();
        password.setId("password");
        feedback = new Label();
        feedback.setId("feedback");

        // Initialize the controller and assign fields
        controller = new LoginController();
        controller.login = login;
        controller.password = password;
        controller.feedback = feedback;

        // Mock the UserService
        mockUserService = mock(UserService.class);
        controller.setUserService(mockUserService);

        // Create a new scene for testing
        Scene scene = new Scene(new VBox(login, password, feedback), 800, 600);
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
        assertEquals("", feedback.getText());
    }

    @Test
    public void testIncorrectCredentials() {
        // Set up the mock behavior
        when(mockUserService.getUser("test")).thenReturn(new User("test", "test", "test", 20, "test", "test"));

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
        // Set up the mock behavior
        User mockUser = new User("test", "test", "test", 20, "test", "test");
        when(mockUserService.getUser("test")).thenReturn(mockUser);
        when(mockUserService.checkPassword(mockUser, "test")).thenReturn(true);

        // Fill fields with correct credentials
        Platform.runLater(() -> {
            login.setText("test");
            password.setText("test");
            controller.processLoginForm();
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("", feedback.getText());
    }
}