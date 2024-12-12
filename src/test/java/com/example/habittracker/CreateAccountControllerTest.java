package com.example.habittracker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateAccountControllerTest extends ApplicationTest {

    private CreateAccountController controller;
    private TextField name;
    private TextField surname;
    private TextField age;
    private TextField login;
    private TextField email;
    private PasswordField password;
    private PasswordField repeatPassword;
    private Label feedback;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-account-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

        controller = loader.getController();

        name = (TextField) scene.lookup("#name");
        surname = (TextField) scene.lookup("#surname");
        age = (TextField) scene.lookup("#age");
        login = (TextField) scene.lookup("#login");
        email = (TextField) scene.lookup("#email");
        password = (PasswordField) scene.lookup("#password");
        repeatPassword = (PasswordField) scene.lookup("#repeatPassword");
        feedback = (Label) scene.lookup("#feedback");

        sleep(5000);
    }

    @BeforeEach
    @Order(1)
    public void setup() {
        name.setText("");
        surname.setText("");
        age.setText("");
        login.setText("");
        email.setText("");
        password.setText("");
        repeatPassword.setText("");
        feedback.setText("");
    }

    @Test
    @Order(2)
    public void testEmptyFields() {
        clickOn("#registerButton");
        assertEquals("All fields are required", feedback.getText());
    }

    @Test
    @Order(3)
    public void testPasswordMismatch() {
        name.setText("Test");
        surname.setText("Test");
        age.setText("25");
        login.setText("test");
        email.setText("test@example.com");
        password.setText("password123");
        repeatPassword.setText("differentPassword");

        clickOn("#registerButton");
        assertEquals("Passwords do not match", feedback.getText());
    }

    @Test
    @Order(4)
    public void testInvalidEmail() {
        name.setText("test");
        surname.setText("test");
        age.setText("25");
        login.setText("test");
        email.setText("wrong-email");
        password.setText("password123");
        repeatPassword.setText("password123");

        clickOn("#registerButton");
        assertEquals("Your email address is incorrect", feedback.getText());
    }

    @Test
    @Order(5)
    public void testInvalidAge() {
        name.setText("test");
        surname.setText("test");
        age.setText("-5");
        login.setText("test");
        email.setText("test@example.com");
        password.setText("password123");
        repeatPassword.setText("password123");

        clickOn("#registerButton");
        assertEquals("Enter correct age", feedback.getText());
    }

    @Test
    @Order(6)
    public void testSuccessfulRegistration() {
        name.setText("test");
        surname.setText("test");
        age.setText("25");
        login.setText("test");
        email.setText("test@example.com");
        password.setText("password123");
        repeatPassword.setText("password123");

        clickOn("#registerButton");
        assertEquals("", feedback.getText());
    }
}

