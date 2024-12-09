package com.example.habittracker;

import com.example.habittracker.utils.DomainUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.database.User;
import org.database.services.UserService;

import java.io.IOException;

public class LoginController {
    @FXML
    TextField login;
    @FXML
    PasswordField password;
    @FXML
    Label feedback;
    enum ErrorCode{NO_ERROR, ERROR}

    private Stage stage;
    private Scene scene;
    public Parent parent;
    private UserService userService;

    @FXML
    protected void onSignInButtonClick() {
        processLoginForm();
        System.out.println(login.getText() + " " + password.getText() );
    }

    @FXML
    public void switchToCreateAccountScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("create-account-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToNewScene(String sceneName) {
        DomainUser user = new DomainUser(login.getText());

        try {
            Parent root = FXMLLoader.load(getClass().getResource(sceneName));
            stage = (Stage)login.getScene().getWindow();
            stage.setUserData(user);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void processLoginForm() {
        ErrorCode result = validateLoginForm();
        Platform.runLater(() -> {
            if (result == ErrorCode.ERROR) {
                feedback.setText("Username or password is incorrect");
            }
            if (result == ErrorCode.NO_ERROR) {
                feedback.setText("");
                switchToNewScene("profile-view.fxml");
            }
        });
    }

    private ErrorCode validateLoginForm() {
        boolean databaseResult = checkInDatabase();
        if (!databaseResult) {
            return ErrorCode.ERROR;
        }
        return ErrorCode.NO_ERROR;
    }

    private boolean checkInDatabase() {
        User user = null;
        try {
            user = userService.getUser(login.getText());
        } catch (Exception e) {
            userService.stopConnection();
            return false;
        }
        if (user != null) {
            if (!userService.checkPassword(user, password.getText())) {
                userService.stopConnection();
                return false;
            }
        }
        userService.stopConnection();
        return true;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}