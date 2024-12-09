package com.example.habittracker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.database.services.UserService;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreateAccountControllerTest extends ApplicationTest {

    private TextField name;
    private TextField surname;
    private TextField age;
    private TextField login;
    private TextField email;
    private PasswordField password;
    private PasswordField repeatPassword;
    private Label feedback;
    private CreateAccountController controller;
    private UserService mockUserService;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        // Initialize JavaFX controls
        name = new TextField();
        name.setId("name");
        surname = new TextField();
        surname.setId("surname");
        age = new TextField();
        age.setId("age");
        login = new TextField();
        login.setId("login");
        email = new TextField();
        email.setId("email");
        password = new PasswordField();
        password.setId("password");
        repeatPassword = new PasswordField();
        repeatPassword.setId("repeatPassword");
        feedback = new Label();
        feedback.setId("feedback");

        // Initialize the controller and assign fields
        controller = new CreateAccountController();
        controller.name = name;
        controller.surname = surname;
        controller.age = age;
        controller.login = login;
        controller.email = email;
        controller.password = password;
        controller.repeatPassword = repeatPassword;
        controller.feedback = feedback;

        // Create a new scene for testing
        Scene scene = new Scene(new VBox(name, surname, age, login, email, password, repeatPassword, feedback), 800, 600);
        stage.setScene(scene);
        stage.show();

        // Mock the UserService
        mockUserService = mock(UserService.class);
        Thread.sleep(1000); //

    }

    @Test
    public void testEmptyFieldsValidation() throws InterruptedException {
        // Leave all fields empty and call the form validation
        clickOn("#name").write("");
        clickOn("#surname").write("");
        clickOn("#age").write("");
        clickOn("#login").write("");
        clickOn("#email").write("");
        clickOn("#password").write("");
        clickOn("#repeatPassword").write("");

        controller.processRegisterForm();

        assertEquals("All fields are required", feedback.getText());

        Thread.sleep(5000); //
    }

    @Test
    public void testMismatchedPasswordsValidation() {
        // Fill fields but use mismatched passwords
        clickOn("#name").write("John");
        clickOn("#surname").write("Doe");
        clickOn("#age").write("25");
        clickOn("#login").write("johndoe");
        clickOn("#email").write("john@example.com");
        clickOn("#password").write("password123");
        clickOn("#repeatPassword").write("differentPassword");

        controller.processRegisterForm();

        assertEquals("Passwords do not match", feedback.getText());
    }

    @Test
    public void testValidInput() {
        // Set up the mock behavior
        when(mockUserService.getUserByEmail("john@example.com")).thenThrow(new RuntimeException("No user found"));
        when(mockUserService.getUser("johndoe")).thenThrow(new RuntimeException("No user found"));

        // Fill in valid data
        clickOn("#name").write("John");
        clickOn("#surname").write("Doe");
        clickOn("#age").write("25");
        clickOn("#login").write("test");
        clickOn("#email").write("test@example.com");
        clickOn("#password").write("password123");
        clickOn("#repeatPassword").write("password123");

        controller.processRegisterForm();

        assertEquals("", feedback.getText());
        verify(mockUserService, times(1)).addUser(
                eq("test"), eq("John"), eq("Doe"), eq(25), eq("test@example.com"), eq("password123")
        );
    }

    @Test
    public void testAgeValidation() {
        // Enter invalid age
        clickOn("#name").write("John");
        clickOn("#surname").write("Doe");
        clickOn("#age").write("200"); // Invalid age
        clickOn("#login").write("johndoe");
        clickOn("#email").write("john@example.com");
        clickOn("#password").write("password123");
        clickOn("#repeatPassword").write("password123");

        controller.processRegisterForm();

        assertEquals("Enter correct age", feedback.getText());
    }
}
