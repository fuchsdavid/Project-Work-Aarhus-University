package com.example.habittracker;

import com.example.habittracker.utils.DomainUser;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileViewControllerTest extends ApplicationTest {

    private ProfileViewController controller;
    private Label nameLabel, emailLabel, surnameLabel, usernameLabel, ageLabel;
    private TextField newName, newEmail, newSurname;
    private Button editName, editEmail, editSurname, okName, okEmail, okSurname;
    private ImageView profilePictureImageView;

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile-view.fxml"));
        Parent root = loader.load();

        // Get the controller
        controller = loader.getController();

        // Initialize JavaFX controls
        nameLabel = (Label) root.lookup("#nameLabel");
        emailLabel = (Label) root.lookup("#emailLabel");
        surnameLabel = (Label) root.lookup("#surnameLabel");
        usernameLabel = (Label) root.lookup("#usernameLabel");
        ageLabel = (Label) root.lookup("#ageLabel");
        newName = (TextField) root.lookup("#newName");
        newEmail = (TextField) root.lookup("#newEmail");
        newSurname = (TextField) root.lookup("#newSurname");
        editName = (Button) root.lookup("#editName");
        editEmail = (Button) root.lookup("#editEmail");
        editSurname = (Button) root.lookup("#editSurame");
        okName = (Button) root.lookup("#okName");
        okEmail = (Button) root.lookup("#okEmail");
        okSurname = (Button) root.lookup("#okSurname");
        profilePictureImageView = (ImageView) root.lookup("#profilePictureImageView");

        // Create a new scene for testing
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    @Order(1)
    public void testInitialize() {
        // Simulate receiving data
        Platform.runLater(() -> {
            controller.initialize();
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the labels are set correctly
        assertEquals("test", usernameLabel.getText());
        assertEquals("testName", nameLabel.getText());
        assertEquals("testSurname", surnameLabel.getText());
        assertEquals("test@example.com", emailLabel.getText());
        assertEquals("25", ageLabel.getText());
    }

    @Test
    @Order(2)
    public void testEditName() {
        // Simulate receiving data and setting the user
        Platform.runLater(() -> {
            controller.initialize();
            controller.onEditNameClicked();
            newName.setText("newTestName");
            controller.onSaveNameClicked();
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the name label is updated
        assertEquals("newTestName", nameLabel.getText());
    }

    @Test
    @Order(3)
    public void testEditSurname() {
        // Simulate receiving data and setting the user
        Platform.runLater(() -> {
            controller.initialize();
            controller.onEditSurnameClicked();
            newSurname.setText("newTestSurname");
            controller.onSaveSurnameClicked();
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the surname label is updated
        assertEquals("newTestSurname", surnameLabel.getText());
    }


    @Test
    @Order(4)
    public void testEditEmailWithNewEmail() {
        // Simulate receiving data and setting the user
        Platform.runLater(() -> {
            controller.initialize();
            controller.onEditEmailClicked();
            newEmail.setText("newTestEmail@example.com");
            controller.onSaveEmailClicked();
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the email label is updated
        assertEquals("newTestEmail@example.com", emailLabel.getText());
    }

}