package com.example.habittracker;

import com.example.habittracker.utils.DomainUser;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.database.User;
import org.database.services.UserService;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileViewControllerTest extends ApplicationTest {

    private ProfileViewController controller;
    private UserService mockUserService;
    private Label nameLabel, emailLabel, surnameLabel, usernameLabel, ageLabel;
    private TextField newName, newEmail, newSurname;
    private Button editName, editEmail, editSurname, okName, okEmail, okSurname;
    private ImageView profilePictureImageView;

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize JavaFX controls
        nameLabel = new Label();
        emailLabel = new Label();
        surnameLabel = new Label();
        usernameLabel = new Label();
        ageLabel = new Label();
        newName = new TextField();
        newEmail = new TextField();
        newSurname = new TextField();
        editName = new Button("Edit Name");
        editEmail = new Button("Edit Email");
        editSurname = new Button("Edit Surname");
        okName = new Button("OK Name");
        okEmail = new Button("OK Email");
        okSurname = new Button("OK Surname");
        profilePictureImageView = new ImageView();

        // Initialize the controller and assign fields
        controller = new ProfileViewController();
        controller.nameLabel = nameLabel;
        controller.emailLabel = emailLabel;
        controller.surnameLabel = surnameLabel;
        controller.usernameLabel = usernameLabel;
        controller.ageLabel = ageLabel;
        controller.newName = newName;
        controller.newEmail = newEmail;
        controller.newSurname = newSurname;
        controller.editName = editName;
        controller.editEmail = editEmail;
        controller.editSurame = editSurname;
        controller.okName = okName;
        controller.okEmail = okEmail;
        controller.okSurname = okSurname;
        controller.profilePictureImageView = profilePictureImageView;

        // Mock the UserService
        mockUserService = mock(UserService.class);
        controller.userService = mockUserService;

        // Create a new scene for testing
        Scene scene = new Scene(new VBox(nameLabel, emailLabel, surnameLabel, usernameLabel, ageLabel, newName, newEmail, newSurname, editName, editEmail, editSurname, okName, okEmail, okSurname, profilePictureImageView), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testInitialize() {
        // Set up the mock behavior
        User mockUser = new User("testUser", "testName", "testSurname", 25, "testEmail", "testImageID");
        when(mockUserService.getUser("testUser")).thenReturn(mockUser);

        // Simulate receiving data
        Platform.runLater(() -> {
            controller.user = new DomainUser("testUser");
            controller.initialize();
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the labels are set correctly
        assertEquals("testUser", usernameLabel.getText());
        assertEquals("testName", nameLabel.getText());
        assertEquals("testSurname", surnameLabel.getText());
        assertEquals("testEmail", emailLabel.getText());
        assertEquals("25", ageLabel.getText());
    }

    @Test
    public void testEditName() {
        // Set up the mock behavior
        User mockUser = new User("testUser", "testName", "testSurname", 25, "testEmail", "testImageID");
        when(mockUserService.getUser("testUser")).thenReturn(mockUser);

        // Simulate receiving data and setting the user
        Platform.runLater(() -> {
            controller.user = new DomainUser("testUser");
            controller.initialize();
            controller.onEditNameClicked();
            newName.setText("newTestName");
            controller.onSaveNameClicked();
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the name label is updated
        assertEquals("newTestName", nameLabel.getText());
        verify(mockUserService).updateName(any(User.class), eq("newTestName"));
    }

    // Add similar tests for editing email and surname
}