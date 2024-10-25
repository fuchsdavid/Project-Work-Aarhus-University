package com.example.habittracker;

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

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Label feedback;
    enum ErrorCode{NO_ERROR, ERROR}

    private Stage stage;
    private Scene scene;
    public Parent parent;

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
    public void switchToDashboardAccountScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void processLoginForm(){
        ErrorCode result = validateLoginForm();
        if(result == ErrorCode.ERROR){
            feedback.setText("Username or password is incorrect");
        }
        if(result == ErrorCode.NO_ERROR){
            feedback.setText("");
        }

    }

    private ErrorCode validateLoginForm(){
        int userID = checkInDatabase();
        if(userID == -1){
            return ErrorCode.ERROR;
        }
        return ErrorCode.NO_ERROR;
    }

    private int checkInDatabase(){
        /*TODO
        check if the account is registered in a database
        and if password is correct
         */
        return 0;
    }

}